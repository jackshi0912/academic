#pragma once
#include <stdio.h>

class Sample{
public:
    float x,y;
    Sample(float x_, float y_) : x(x_), y(y_) {}
};

class Sampler{
    int current_pixel;
    int width, height, pixels;
public:
    Sampler(int w, int h) : width(w), height(h), current_pixel(0), pixels(w*h) {}
    bool getSample(Sample* sample) {
        if (current_pixel >= pixels){
            return false;
        }else{
            *sample = Sample(current_pixel % width + 0.5, current_pixel / width + 0.5);
            current_pixel += 1;
            if (current_pixel % 100 == 0){
                printf("%.2f%%\r", (float)current_pixel/pixels*100);
            }
            return true;
        }
    }
};

