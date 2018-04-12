#pragma once
#include "transform.h"
#include "hw4.h"

class Ray{
public:
    float t = 0.0, t_min = 0.0, t_max = 0.0;
    vec4 pos = vec4(0,0,0,1), dir = vec4(0,0,0,0);
    Ray(vec4 pos_, vec4 dir_, float t_, float t_min_, float t_max_) 
        : pos(pos_), dir(dir_), t(t_), t_min(t_min_), t_max(t_max_) {}
    Ray(vec3 pos_, vec3 dir_, float t_, float t_min_, float t_max_) 
        : pos(vec4(pos_, 1)), dir(vec4(dir_, 1)), t(t_), t_min(t_min_), t_max(t_max_) {}
};