// Transform.cpp: implementation of the Transform class.

// Note: when you construct a matrix using mat4() or mat3(), it will be COLUMN-MAJOR
// Keep this in mind in readfile.cpp and display.cpp
// See FAQ for more details or if you're having problems.

#include "Transform.h"

// Helper rotation function.  Please implement this.  
mat3 Transform::rotate(const float degrees, const vec3& axis) 
{
    // Create the symetric rotation matrix
    float rad = degrees / 180 * pi;
    mat3 A(0, axis[2], -axis[1], -axis[2], 0, axis[0], axis[1], -axis[0], 0);
    // Create the axis * transpose axis matrix
    mat3 axis_transpose(axis[0] * axis[0], axis[0] * axis[1], axis[0] * axis[2],
        axis[0] * axis[1], axis[1] * axis[1], axis[1] * axis[2],
        axis[0] * axis[2], axis[1] * axis[2], axis[2] * axis[2]);

    return ((float)cos(rad))*mat3() +
        ((float)(1 - cos(rad)))*axis_transpose + ((float)sin(rad))*A;
}

void Transform::left(float degrees, vec3& eye, vec3& up)
{
    up = rotate(degrees, up) * up;
    eye = rotate(degrees, up) * eye;
}

void Transform::up(float degrees, vec3& eye, vec3& up)
{
    eye = rotate(degrees, glm::normalize(glm::cross(eye, up))) * eye;
    up = rotate(degrees, glm::normalize(glm::cross(eye, up))) * up;
}

mat4 Transform::lookAt(const vec3 &eye, const vec3 &center, const vec3 &up)
{
    vec3 w = glm::normalize(eye-center);
    vec3 u = glm::normalize(glm::cross(up, w));
    vec3 v = glm::cross(w, u);

    mat4 rotation = transpose(mat4(u.x, u.y, u.z, 0,
                                   v.x, v.y, v.z, 0,
                                   w.x, w.y, w.z, 0,
                                   0,   0,   0,   1));

    mat4 translation = transpose(mat4(1, 0, 0, -eye.x,
                                      0, 1, 0, -eye.y,
                                      0, 0, 1, -eye.z,
                                      0, 0, 0, 1));
    return rotation * translation;
}

mat4 Transform::perspective(float fovy, float aspect, float zNear, float zFar)
{
    mat4 ret;
    // New, to implement the perspective transform as well.  
    float d = 1 / tan(glm::radians(fovy)/2);
    ret[0][0] = d / aspect;
    ret[1][1] = d;
    ret[2][2] = -(zFar + zNear) / (zFar - zNear);
    ret[3][2] = -2*zFar *zNear / (zFar - zNear);
    ret[2][3] = -1;
    ret[3][3] = 0;
    return ret;
}

mat4 Transform::scale(const float &sx, const float &sy, const float &sz)
{
    mat4 ret;
    // Implement scaling 
    ret[0][0] = sx;
    ret[1][1] = sy;
    ret[2][2] = sz;

    return ret;
}

mat4 Transform::translate(const float &tx, const float &ty, const float &tz)
{
    mat4 ret;
    // Implement translation 
    ret[3][0] = tx;
    ret[3][1] = ty;
    ret[3][2] = tz;

    return ret;
}

// To normalize the up direction and construct a coordinate frame.  
// As discussed in the lecture.  May be relevant to create a properly 
// orthogonal and normalized up. 
// This function is provided as a helper, in case you want to use it. 
// Using this function (in readfile.cpp or display.cpp) is optional.  

vec3 Transform::upvector(const vec3 &up, const vec3 & zvec)
{
    vec3 x = glm::cross(up, zvec);
    vec3 y = glm::cross(zvec, x);
    vec3 ret = glm::normalize(y);
    return ret;
}


Transform::Transform()
{

}

Transform::~Transform()
{

}
