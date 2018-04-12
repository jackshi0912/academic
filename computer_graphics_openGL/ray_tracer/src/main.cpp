
#include <iostream>
#include <iomanip>
#include <stdio.h>
#include <string>
#include <cstring>

#include "film.h"
#include "readfile.h"
#include "hw4.h"
#include "ray.h"

using namespace std;

int main(int argc, const char * argv[]) {
    
    Scene *scene = new Scene(10,10);
    readfile(argv[1], scene);
    
    Sample* sample;
    sample = new Sample(0.0, 0.0);
    Sampler sampler = Sampler(scene->w,scene->h);
    Film film = Film(scene->w,scene->h);
    
    Color black = Color(0, 0, 0);
    Color *color = new Color(0,0,0);
    Ray *ray = new Ray(vec3(0,0,0), vec3(0,0,0), 0, 0, 100);

    string filename(argv[1]);
    string rawname = filename.substr(0, filename.find_last_of("."));
    rawname += ".png";
    char img_name[1024];
    strcpy(img_name, rawname.c_str());
    
    while (sampler.getSample(sample)) {
        film.commit(*sample, black); 
        scene->camera->generateRay(*sample, ray, film);
        scene->rayTrace(*ray, 0,color);
        film.commit(*sample, *color);
    }

    film.writeImage(img_name);
    return 0;
}
