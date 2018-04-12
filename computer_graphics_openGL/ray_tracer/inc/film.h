#pragma once

#include <FreeImage.h>
#include <iostream>
#include <string>

#include "color.h"
#include "sampler.h"

class Film{
public:
    int w,h;
    Film(int w_, int h_) : w(w_), h(h_) {}
    void commit(Sample sample, Color color) {
        int x = (int) (sample.x - 0.5);
        int y = (int) (sample.y - 0.5);
        int i = y * w + x;
        int j = i * 3;
        color.clamp();
        pixels[j] = (unsigned char) (color.b * 255);
        pixels[j+1] = (unsigned char) (color.g * 255);
        pixels[j+2] = (unsigned char) (color.r * 255);
    }
    void writeImage(char *fname) {
        FreeImage_Initialise();
        FIBITMAP *img = FreeImage_ConvertFromRawBits(pixels, w, h, w * 3, 
            24, 0xFF0000, 0x00FF00, 0x0000FF, true);
        FreeImage_Save(FIF_PNG, img, fname, 0);
        FreeImage_DeInitialise();
    }
    unsigned char getPixel(int idx) {return pixels[idx];}

private:
    unsigned char pixels[3*1000*1000] = {};
};