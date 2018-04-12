#pragma once

#include "ray.h"
#include "transform.h"

class Point;
class Normal;
class PointNormal;
class Shape;
class Triangle;
class Sphere;
class Intersection;

class Shape{
public:
    float ambient[3] = {0.2,0.2,0.2};
    float specular[3] = {0,0,0};
    float emission[3] = {0,0,0};
    float diffuse[3] = {0,0,0};
    float index_refrac = 0;
    
    glm::mat4 transform = glm::mat4(1.0);
    
    virtual bool intersect(Ray &ray) = 0;
    virtual bool intersect(Ray &ray, float *thit, PointNormal *local) = 0;
    virtual ~Shape() {};
    
    // Basic field setters
    void set_ambient(float a[3]){
        ambient[0] = a[0];
        ambient[1] = a[1];
        ambient[2] = a[2];
    }
    void set_specular(float s[3]){
        specular[0] = s[0];
        specular[1] = s[1];
        specular[2] = s[2];
    }
    void set_diffuse(float d[3]){
        diffuse[0] = d[0];
        diffuse[1] = d[1];
        diffuse[2] = d[2];
    }
    void set_emission(float e[3]){
        emission[0] = e[0];
        emission[1] = e[1];
        emission[2] = e[2];
    }
};

class Point{
public:
    vec4 p = vec4(0,0,0,1);
    Point(vec4 p_) : p(p_) {}
    Point(){};
};

class Normal{
public:
    vec3 p = vec3(0,0,0);
    Normal(vec3 p_) : p(p_) {}
    Normal(){};
};


class PointNormal{
public:
    Point point = Point();
    Normal normal = Normal();
    PointNormal(Point p_, Normal n_) : point(p_), normal(n_) {}
};


class Sphere:public Shape{
public:
    vec3 center;
    float radius;
    Sphere(vec3 pos, float r) : center(pos), radius(r) {}
    Sphere() : center(vec3(0,0,0)), radius(0) {}
    ~Sphere(){};
    
    bool intersect(Ray &ray);
    bool intersect(Ray &ray, float *thit, PointNormal *local);

private:
    void get_quadratic_formula(Ray &ray, float &a, float &b, float &c);
};

class Triangle:public Shape{
public:
    vec3 vertA = vec3(0,0,0);
    vec3 vertB = vec3(0,0,0);
    vec3 vertC = vec3(0,0,0);
    vec3 normal = vec3(0,0,0);

    Triangle(){};
    Triangle(vec3 vA, vec3 vB, vec3 vC) : vertA(vA), vertB(vB), vertC(vC) {}
    ~Triangle(){};
    
    bool intersect(Ray &ray);
    bool intersect(Ray &ray, float *thit, PointNormal *local);
    
private:
    void bc_coord(vec4 p, float &alpha, float &beta, float &gamma);
    void setNormal();
};


class Intersection{
public:
    PointNormal *ptr_norm = new PointNormal(Point(), Normal());
    Shape * shape = new Sphere();
    Intersection(){};
};


