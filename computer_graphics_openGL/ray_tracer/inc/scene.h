#pragma once

#include "color.h"
#include "camera.h"
#include "shape.h"
#include "hw4.h"
#include "light.h"

class Scene{
public:
    float attenuation[3] = {1,0,0};
    int w,h;
    int num_objects = 0, num_lights = 0, max_vertices = 0, current_vertex = 0, maxdepth = 0;
    vec3 vertices[999999];
    Camera* camera = new Camera(vec3(),vec3(),vec3(),0);
    Shape** shapes;
    Light** lights;
    
    Color compute_color(Intersection *in);
    bool intersect(Ray &ray, float *thit, Intersection *in);
    bool intersectP(Ray &ray, Shape *shape);
    void rayTrace(Ray &ray, int depth, Color *color);
    Color computePointLight(vec3 direction, vec3 lightColor, vec3 normal, 
        vec3 halfvec, Shape* shape);
    static Ray make_reflect_ray(PointNormal local, Ray ray);

    Scene(int w_, int h_) : w(w_), h(h_) {
        shapes = new Shape*[max_objects];
        lights = new Light*[max_lights];
    };
    void set_attenuation(float _a[3]){
        attenuation[0] = _a[0];
        attenuation[1] = _a[1];
        attenuation[2] = _a[2];
    }
};