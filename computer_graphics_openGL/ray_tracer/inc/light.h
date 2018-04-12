#pragma once
#include "color.h"
#include <string>

class Light{
public:
    std::string name;
    virtual ~Light(){};
};

class AmbientLight:public Light{
public:
    std::string name = "ambient";
    Color color = Color(0,0,0);
    
    AmbientLight(Color color_) : color(color_) {}
    ~AmbientLight(){};
};

class PointLight:public Light{
public:
    std::string name = "point";
    Color color = Color(0,0,0);
    vec3 position = vec3(0,0,0);
    
    PointLight(Color color_, vec3 pos_) : color(color_), position(pos_) {}
    ~PointLight(){};
};

class DirectionalLight:public Light{
public:
    std::string name = "directional";
    Color color = Color(0,0,0);
    vec3 position = vec3(0,0,0);
    
    DirectionalLight(Color col_, vec3 pos_) : color(col_), position(pos_) {}
    ~DirectionalLight(){};
};