// Transform.cpp: implementation of the Transform class.

#include "Transform.h"

//Please implement the following functions:

// Helper rotation function.  
mat3 Transform::rotate(const float degrees, const vec3& axis) {
    // Create the symetric rotation matrix
    float rad = degrees / 180 * pi;
    mat3 A(0, axis[2], -axis[1], -axis[2], 0, axis[0], axis[1], -axis[0], 0);
    // Create the axis * transpose axis matrix
    mat3 axis_transpose(axis[0] * axis[0], axis[0] * axis[1], axis[0] * axis[2],
        axis[0] * axis[1], axis[1] * axis[1], axis[1] * axis[2],
        axis[0] * axis[2], axis[1] * axis[2], axis[2] * axis[2]);
    // You will change this return call
    return ((float)cos(rad))*mat3() + 
        ((float)(1-cos(rad)))*axis_transpose + ((float)sin(rad))*A;
}

// Transforms the camera left around the "crystal ball" interface
void Transform::left(float degrees, vec3& eye, vec3& up) {
    // YOUR CODE FOR HW1 HERE
    up = rotate(degrees, up) * up;
    eye = rotate(degrees, up) * eye;
}

// Transforms the camera up around the "crystal ball" interface
void Transform::up(float degrees, vec3& eye, vec3& up) {
    // YOUR CODE FOR HW1 HERE 
    eye = rotate(degrees, glm::normalize(glm::cross(eye, up))) * eye;
    up = rotate(degrees, glm::normalize(glm::cross(eye,up))) * up;
}

// Your implementation of the glm::lookAt matrix
mat4 Transform::lookAt(vec3 eye, vec3 up) {
    // Setupt coordinate frame
    vec3 w = glm::normalize(eye);
    vec3 u = glm::normalize(glm::cross(up, w));
    vec3 v = glm::cross(w, u);
    
    return mat4(u[0], v[0], w[0], 0.0, u[1], v[1], w[1], 0.0, u[2], v[2], w[2], 0.0,
        glm::dot((-1.0f*u), eye), glm::dot((-1.0f*v), eye), glm::dot((-1.0f*w), eye), 1);
}

Transform::Transform()
{

}

Transform::~Transform()
{

}