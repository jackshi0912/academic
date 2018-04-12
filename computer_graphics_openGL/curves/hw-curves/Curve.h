#if !defined(CURVE_H)
#define CURVE_H

#include <vector>
#include <math.h>
#include "GL/glew.h"
#include "GL/glut.h"
#include "Point.h"

// To account for C++ namespace issues
typedef std::vector<Point> Pvector;

// glLineWidth() doesn't work on OSX systems, so we will alternatively draw
// thick lines using triangles in both the Windows and OSX + Linux versions
// of this homework.

// The basic curve class.  The basic element is a vector of Points, 
// which is the Pvector.  
// The class has methods for drawing adding, deleting, moving, updating, 
// and so forth.  More details are in Curve.cpp 
// The draw method is virtual since it is over-ridden by the specific 
// curve (Bspline, Bezier, Bezier2 etc.).  

// points is the actual vector of points used.  
// The iterator into this (look up C++ iterators) is activePoint 
// The bool isactive indicates if there is currently any active point 
// (In previous versions, we did this by setting activePoint to null, but 
// this is no longer allowed in modern C++)


class Curve  
{
protected:
	Pvector points;
	Pvector::iterator activePoint;
    bool isactive ;
	GLuint colorPos;
	
public:
	Curve();
	virtual ~Curve();
	virtual void draw(int levelOfDetail);
	void addPoint(float x, float y);
	void deleteActivePoint();
	void moveActivePoint(float dx, float dy);
	void updateActivePoint(float x, float y);
	void connectTheDots();
    void drawLine(float x1, float y1, float x2, float y2) ;
	void setShaderReferences(GLuint);
};

#endif

