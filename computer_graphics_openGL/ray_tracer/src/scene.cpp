#pragma once
#include "scene.h"
#include "hw4.h"

bool Scene::intersect(Ray &ray, float *thit, Intersection *intersect){
    bool hit = false;
    float thit_temp = INFINITY;
    PointNormal ptr_norm_1 = PointNormal(Point(vec4(0,0,0,1)), Normal(vec3(0,0,0)));
    Shape *shape_temp = new Sphere();
    // Look to calculate thit
    for (int i = 0; i < num_objects; i++) {
        // Check hit
        if (shapes[i]->intersect(ray, thit, intersect->ptr_norm)){
            hit = true;
            // Check for updates
            if (*thit <= thit_temp) {
                shape_temp = shapes[i];
                thit_temp = *thit;
                ptr_norm_1 = *(intersect->ptr_norm);
            }
        }
    }
    *thit = thit_temp;
    intersect->shape = shape_temp;
    *(intersect->ptr_norm) = ptr_norm_1;
    return hit;
}

bool Scene::intersectP(Ray &ray, Shape *shape){
    float min_t_val = 0.0001;
    float t = 0;
    PointNormal *ptr_norm = new PointNormal(Point(), Normal());
    for (int i = 0; i < num_objects; i++) {
        t = 0;
        *ptr_norm = PointNormal(Point(), Normal());
        if (shapes[i] != shape) {
            if (shapes[i]->intersect(ray, &t, ptr_norm)) {
                if ((t) >= min_t_val && (t) < 1) return true;
            }
        }
    }
    return false;
}

Ray Scene::make_reflect_ray(PointNormal ptr_norm, Ray ray){
    return Ray(ptr_norm.point.p, 
        glm::normalize(ray.dir + 2 * vec4(ptr_norm.normal.p,0)), ray.t, ray.t_min, ray.t_max);
}

// Same model as before
Color Scene::computePointLight(vec3 direction, vec3 color, vec3 normal, 
        vec3 halfvec, Shape* shape){
    float n_dir = glm::dot(normal, direction);
    float m;
    if (n_dir > 0) {
        m = n_dir;
    } else {
        m = 0;
    }
    vec3 diffuse = vec3(shape->diffuse[0], shape->diffuse[1], shape->diffuse[2]);
    vec3 specular = vec3(shape->specular[0], shape->specular[1], shape->specular[2]);
    Color lambert = Color(diffuse.x * color.x * m, 
        diffuse.y * color.y * m, diffuse.z * color.z * m);
    
    float nDotH = glm::dot(normal, halfvec);
    float n = nDotH > 0? nDotH : 0;
    float pn = powf(n, shape->index_refrac);
    
    Color phong = Color(specular.x * color.x * pn, specular.y * color.y * pn, specular.z * color.z * pn);
    return Color(lambert.r + phong.r, lambert.g + phong.g, lambert.b + phong.b);
}

void Scene::rayTrace(Ray &ray, int depth, Color *color) {
    // If end trace, reset color and end
    if (depth > maxdepth) {
        *color = Color(0,0,0);
        return;
    }
    float thit = INFINITY;
    Intersection *in = new Intersection();
    if (intersect(ray, &thit, in)) {
        *color = compute_color(in);
        Ray reflectedRay = make_reflect_ray(*(in->ptr_norm), ray);
        Color *temp_color = new Color(0,0,0);
        rayTrace(reflectedRay, depth+1, temp_color);
        (*color).r += (*temp_color).r;
        (*color).g += (*temp_color).g;
        (*color).b += (*temp_color).b;
    }else{
        *color = Color(0, 0, 0);
    }
}

Color Scene::compute_color(Intersection *intersect) {
    Color color = Color(0, 0, 0);
    vec3 eyepos = vec3(0,0,0);
    vec4 cam_pos_ = intersect->shape->transform * intersect->ptr_norm->point.p;
    vec3 cam_pos = vec3(cam_pos_.x / cam_pos_.w, cam_pos_.y / cam_pos_.w, cam_pos_.z / cam_pos_.w);
    vec3 eyedirn = glm::normalize(eyepos - cam_pos);
    vec4 normal_ = glm::transpose(glm::inverse(intersect->shape->transform)) * 
        vec4(intersect->ptr_norm->normal.p,0);
    vec3 normal = glm::normalize(vec3(normal_.x, normal_.y, normal_.z));
    color.r += intersect->shape->ambient[0];
    color.g += intersect->shape->ambient[1];
    color.b += intersect->shape->ambient[2];
    color.r += intersect->shape->emission[0];
    color.g += intersect->shape->emission[1];
    color.b += intersect->shape->emission[2];

    for (int i = 0; i < num_lights; i++) {
        if (dynamic_cast<PointLight* > (lights[i]) != 0) {
            // Lighting model
            PointLight* light = dynamic_cast<PointLight*>(lights[i]);
            vec3 lightposn = light->position;
            vec3 direction = glm::normalize(lightposn - cam_pos);
            vec3 color = vec3(light->color.r, light->color.g, light->color.b);
            vec3 halfvec = glm::normalize(eyedirn + direction);
            float distance = glm::distance(lightposn, cam_pos);
            float att_const = 1.0f / (attenuation[0] + distance * attenuation[1] 
                + distance * distance * attenuation[2]);
            Ray ray_shadow = Ray(cam_pos, lightposn - cam_pos, 0, 0, 100);
            bool shadow = intersectP(ray_shadow, intersect->shape);
            if (!intersectP(ray_shadow, intersect->shape)) {
                Color point_color = computePointLight(direction, color, normal, halfvec, intersect->shape);
                color.r += att_const * point_color.r;
                color.g += att_const * point_color.g;
                color.b += att_const * point_color.b;
            }
        }
        
        if (dynamic_cast<DirectionalLight*>(lights[i]) != 0) {
            DirectionalLight* light = dynamic_cast<DirectionalLight*>(lights[i]);
            vec3 lightposn = light->position, direction = glm::normalize(lightposn), 
                color = vec3(light->color.r, light->color.g, light->color.b), halfvec = glm::normalize(eyedirn + direction);
            float distance = glm::distance(lightposn, cam_pos), 
                att_const = 1.0f / (attenuation[0] + distance * attenuation[1] + distance * distance * attenuation[2]);
            Ray ray_shadow = Ray(cam_pos, lightposn-cam_pos, 0, 0, 100);
            if (!intersectP(ray_shadow, intersect->shape)) {
                Color directionalColor = computePointLight(direction, color, normal, halfvec, intersect->shape);
                color.r += att_const * directionalColor.r;
                color.g += att_const * directionalColor.g;
                color.b += att_const * directionalColor.b;
            }
        }
        
    }
    
    return color;
}