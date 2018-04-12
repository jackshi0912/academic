#pragma once

#include "transform.h"
#include "ray.h"
#include "sampler.h"
#include "film.h"
#include "hw4.h"

class Camera{
public:
    vec3 lookFrom = vec3(), lookAt = vec3(), up = vec3();
    vec3 u = vec3(), v = vec3(), w = vec3();
    float fovy;

    Camera(vec3 lookFrom_, vec3 lookAt_, vec3 up_, float fovy_) 
        : lookFrom(lookFrom_), lookAt(lookAt_), up(up_), fovy(fovy_) {setCoord();}
    
    void setCoord() {
        w = glm::normalize(lookFrom - lookAt);
        u = glm::normalize(glm::cross(up, w));
        v = glm::cross(w, u);
    }
    
    void generateRay(Sample &sample, Ray *ray, Film &film) {
        float beta = tanf(glm::radians(fovy) / 2) * (film.h/2 - sample.y) / (film.h/2);
        float tan_fovx = (float) film.w / film.h * tanf(glm::radians(fovy) / 2);
        float alpha = tan_fovx * (sample.x - film.w/2) / (film.w/2);
        vec3 dirn = vec3( alpha * u.x + beta * v.x - w.x,
                          alpha * u.y + beta * v.y - w.y,
                          alpha * u.z + beta * v.z - w.z);
        *ray = Ray(vec4(lookFrom,1), glm::normalize(vec4(dirn,0)), ray->t, ray->t_min, ray->t_max);
    }
    
};