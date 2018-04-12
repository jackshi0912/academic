#include "shape.h"
#include "hw4.h"

bool Sphere::intersect(Ray &ray){
    float t = 0;
    return Sphere::intersect(ray, &t, new PointNormal(Point(),Normal()));
}

bool Sphere::intersect(Ray &ray, float *thit, PointNormal *ptr_norm){
    mat4 inverse_transform = glm::inverse(transform);
    Ray transformed_ray = Ray(inverse_transform * ray.pos, 
        inverse_transform*ray.dir, ray.t, ray.t_min, ray.t_max);
    float a,b,c,discriminant,r1,r2;
    get_quadratic_formula(transformed_ray, a, b, c);
    discriminant = b*b - 4*a*c;
    if (discriminant >= 0){
        if (discriminant == 0) {
            *thit = -b / (2*a);
        }
        else{
            r1 = (-b + sqrtf(discriminant)) / (2*a);
            r2 = (-b - sqrtf(discriminant)) / (2*a);
            if (r1 > 0 && r2 > 0){
                *thit = fmin(r1, r2);
            }else if (r1 > 0 && r2 < 0){
                *thit = r1;
            }else if (r1 <0 && r2 >0){
                *thit = r2;
            }else{ return false; }
        }
        vec4 point = transformed_ray.pos + transformed_ray.dir * (*thit);
        vec4 normal = glm::normalize(point - vec4(center,1));
        *ptr_norm = PointNormal(Point(point), Normal(vec3(normal.x, normal.y, normal.z)));
        return true;
    }
    else{
        return false;
    }
}

void Sphere::get_quadratic_formula(Ray &ray, float &a, float &b, float &c){
    vec3 p0 = vec3(ray.pos.x, ray.pos.y, ray.pos.z);
    vec3 p1 = vec3(ray.dir.x, ray.dir.y, ray.dir.z);
    a = glm::dot(p1, p1);
    b = 2 * (glm::dot(p1, p0 - center));
    c = glm::dot(p0-center, p0-center) - radius * radius;
}

void Triangle::setNormal(){
    normal = glm::cross(vertA - vertC, vertB - vertC);
}

// Copied from textbook
void Triangle::bc_coord(vec4 p, float &alpha, float &beta, float &gamma){
    vec4 v0 = vec4(vertB,1) - vec4(vertA,1);
    vec4 v1 = vec4(vertC,1) - vec4(vertA,1);
    vec4 v2 = p - vec4(vertA,1);
    float d00 = glm::dot(v0, v0);
    float d01 = glm::dot(v0, v1);
    float d11 = glm::dot(v1, v1);
    float d20 = glm::dot(v2, v0);
    float d21 = glm::dot(v2, v1);
    float denom = d00 * d11 - d01 * d01;
    beta = (d11 * d20 - d01 * d21) / denom;
    gamma = (d00 * d21 - d01 * d20) / denom;
    alpha = 1.0f - beta - gamma;
}


bool Triangle::intersect(Ray &ray){
    float t = 0;
    return intersect(ray, &t, new PointNormal(Point(), Normal()));
}

bool Triangle::intersect(Ray &ray, float *thit, PointNormal *ptr_norm){
    float alpha, beta, gamma;
    mat4 inverse_transform = glm::inverse(transform);
    Ray transformed_ray = Ray(inverse_transform * ray.pos, 
        inverse_transform*ray.dir, ray.t, ray.t_min, ray.t_max);
    setNormal();
    float t = (glm::dot(vec4(vertC,1), vec4(normal,1)) 
        - glm::dot(transformed_ray.pos, vec4(normal,1))) 
        / (glm::dot(transformed_ray.dir, vec4(normal,1)));
    vec4 rayP = transformed_ray.pos + t * transformed_ray.dir;
    bc_coord(rayP, alpha, beta, gamma);
    
    if (beta >= 0 && beta <= 1 && gamma >= 0 && gamma <= 1 && (beta + gamma) <= 1) {
        *thit = t;
        *ptr_norm = PointNormal(Point(rayP), Normal(normal));
        if (t > 0) {
            return true;
        } else {return false;}
    }
    return false;

}
