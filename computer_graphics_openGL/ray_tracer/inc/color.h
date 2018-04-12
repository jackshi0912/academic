#pragma once

class Color{
public:
    float r,g,b;
    Color(float r_, float g_, float b_) : r(r_), g(g_), b(b_) {}
    void clamp(){
        r = (r>1)?1:r;
        g = (g>1)?1:g;
        b = (b>1)? 1:b;
    }
};