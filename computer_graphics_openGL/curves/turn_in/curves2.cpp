#include "WorkingScene.h"
#include <math.h>

// This file includes the basic functions that your program must fill in.  
// Note that there are several helper functions from Curve.cpp that can be used!
// In particular, take a look at moveActivePoint, connectTheDots, drawLine, etc.

// What happens when you drag the mouse to x and y?  
// In essence, you are dragging control points on the curve.
void WorkingScene::drag(int x, int y) {
    /* YOUR CODE HERE */
    //you must figure out how to transform x and y so they make sense
    //update oldx, and oldy
    //make sure scene gets redrawn
    if (theOnlyCurve) {
        theOnlyCurve->moveActivePoint((float)(x - oldx) / width, (float)(oldy - y) / height);
        oldx = x;
        oldy = y;
        glutPostRedisplay();
    }
}

// Mouse motion.  You need to respond to left clicks (to add points on curve) 
// and right clicks (to delete points on curve) 
void WorkingScene::mouse(int button, int state, int x, int y) {
    // theOnlyCurve is the current type of curve being drawn. It is created in Scene.cpp.
    if (theOnlyCurve && state == GLUT_DOWN) {
        if (button == GLUT_LEFT_BUTTON) {
            /* YOUR CODE HERE */
            theOnlyCurve->addPoint((float)x / width, (float)(height - y) / height);
        }
        if (button == GLUT_RIGHT_BUTTON) {
            /* YOUR CODE HERE */
            theOnlyCurve->updateActivePoint((float)x / width, (float)(height - y) / height);
            theOnlyCurve->deleteActivePoint();
        }
    }

    /* YOUR CODE HERE */
    //update oldx, and oldy
    //make sure scene gets redrawn
    oldx = x;
    oldy = y;
    glutPostRedisplay();
}



#include "Bezier.h"

int Factorial(int i)
{
    if (i <= 1)
        return 1;
    return i * Factorial(i - 1);
}

int Combination(int n, int r) {
    return Factorial(n) / (Factorial(r)*Factorial(n - r));
}


// Bezier drawing function.  This is by deCasteljau or equivalent algorithm. 
// It should support Bezier curves of arbitrary degree/order.
void Bezier::draw(int levelOfDetail) {

    connectTheDots();
    Pvector::iterator p;
    int j, k;
    float step = (float)1 / levelOfDetail;
    if (!points.empty()) {
        float prev_x = (*(points.begin())).x;
        float prev_y = (*(points.begin())).y;
        float next_x = 0;
        float next_y = 0;

        /* YOUR CODE HERE */
        for (int i = 0; i < levelOfDetail + 1; i++) { // Decide the step value
            next_x = 0;
            next_y = 0;
            for (p = points.begin(), j = points.size()-1, k = 0; p != points.end(); p++, j--, k++) { // Construct curve polynomial

                next_x += Combination(points.size()-1, k)*pow(step*i, k)*pow((1 - step*i), j)*(*p).x;
                next_y += Combination(points.size()-1, k)*pow(step*i, k)*pow((1 - step*i), j)*(*p).y;
                // printf("step: %f, j: %d, k: %d, size: %d\n", step, j, k, points.size());
            }
            //printf("done\n");
            drawLine(prev_x, prev_y, next_x, next_y);
            prev_x = next_x;
            prev_y = next_y;
        }

    }

    //glutPostRedisplay();
}



#include "Bspline.h"

// The B-Spline drawing routine.  
// Remember to call drawSegment (auxiliary function) for each set of 4 points.
void Bspline::draw(int levelOfDetail) {
    connectTheDots();
    if (points.size() >= 4) {
        for (Pvector::iterator it = points.begin(); it + 3 != points.end(); it++) {
            drawSegment(it, it + 1, it + 2, it + 3, levelOfDetail);
        }
    }

    //glutPostRedisplay();
}

void Bspline::drawSegment(Pvector::iterator p1, Pvector::iterator p2, Pvector::iterator p3, Pvector::iterator p4, int levelOfDetail) {

    /* YOUR CODE HERE */

    //printf("Last 4 points: (%f, %f) |  (%f, %f) |  (%f, %f) |  (%f,%f)\n", (*p1).x, (*p1).y, (*p2).x, (*p2).y, (*p3).x, (*p3).y, (*p4).x, (*p4).y );

    float m[4][4] = { {-1.0/6, 3.0/6, -3.0/6, 1.0/6}, 
                      {3.0/6, -6.0/6,  3.0/6, 0}, 
                      {-3.0/6, 0,      3.0/6, 0}, 
                      {1.0/6,  4.0/6,  1.0/6, 0}};
    float step = 1 / (float)levelOfDetail;
    float prev_x, prev_y, next_x, next_y;
    
    //draw segment
    for (int i = 0; i <= levelOfDetail; ++i ) { // Calc seg points
        float u = i*step;
        float u_m[4] = 
          { pow(u,3)*m[0][0] + pow(u, 2)*m[1][0] + u*m[2][0] +m[3][0], 
            pow(u,3)*m[0][1] + pow(u, 2)*m[1][1] + u*m[2][1] +m[3][1], 
            pow(u,3)*m[0][2] + pow(u, 2)*m[1][2] + u*m[2][2] +m[3][2], 
            pow(u,3)*m[0][3] + pow(u, 2)*m[1][3] + u*m[2][3] +m[3][3]};

        next_x = u_m[0] * (*p1).x + u_m[1] * (*p2).x +
            u_m[2] * (*p3).x + u_m[3] * (*p4).x;
        next_y = u_m[0] * (*p1).y + u_m[1] * (*p2).y +
            u_m[2] * (*p3).y + u_m[3] * (*p4).y;

        if (i > 0) { // At least 2 points are calculated
            // printf("prev: %f, %f, next: %f, %f, step: %f\n", prev_x, prev_y, next_x, next_y, step*i );
            drawLine(prev_x, prev_y, next_x, next_y);
        }
        else {
            //printf("start: %f, %f, step: %f\n", next_x, next_y, step*i );
        }

        // Update previous point
        prev_x = next_x;
        prev_y = next_y;
    }
     
    //then create a Point to be drawn where the knot should be

    Point p(next_x, next_y);
    p.draw();
    //glutPostRedisplay();
}

#include "Bezier2.h"

//This function is provided to aid you.
//It should be used in the spirit of recursion, though you may choose not to.
//This function takes an empty vector of points, accum
//It also takes a set of control points, pts, and fills accum with
//the control points that correspond to the next level of detail.
void accumulateNextLevel(Pvector* accum, Pvector pts) {
    if (pts.empty()) return;
    accum->push_back(*(pts.begin()));
    if (pts.size() == 1) return;
    for (Pvector::iterator it = pts.begin(); it != pts.end() - 1; it++) {
        /* YOUR CODE HERE  (only one to three lines)*/
        (*it).x = ((*it).x + (*(it + 1)).x) / 2; 
        (*it).y = ((*it).y + (*(it + 1)).y) / 2; 
    }
    //save the last point
    Point last = *(pts.end() - 1);
    pts.pop_back();
    //recursive call
    accumulateNextLevel(accum, pts);
    accum->push_back(last);
}


// The basic draw function for Bezier2.  Note that as opposed to Bezier, 
// this draws the curve by recursive subdivision.  So, levelofdetail 
// corresponds to how many times to recurse.  
void Bezier2::draw(int levelOfDetail) {
    //This is just a trick to find out if this is the top level call
    //All recursive calls will be given a negative integer, to be flipped here
    if (levelOfDetail > 0) {
        connectTheDots();
    }
    else {
        levelOfDetail = -levelOfDetail;
    }

    //Base case.  No more recursive calls.
    if (levelOfDetail <= 1) {
        if (points.size() >= 2) {
            for (Pvector::iterator it = points.begin(); it != points.end() - 1; it++) {
                /* YOUR CODE HERE */
                drawLine((*it).x, (*it).y, (*(it+1)).x, (*(it+1)).y);
            }
           // glutPostRedisplay();
        }
    }
    else {
        Pvector* accum = new Pvector();
        Bezier2 left, right;

        //add the correct points to 'left' and 'right'.
        //You may or may not use accum as you see fit.
        /* YOUR CODE HERE */
        accumulateNextLevel(accum, points);
        for (int i = 0; i < accum->size(); i++) {
            if ( i <= accum->size() / 2 ) {
                left.points.push_back(accum->at(i));
            }
            if (i >= accum->size() / 2) {
                right.points.push_back(accum->at(i));
            }
        }

        left.draw(1 - levelOfDetail);
        right.draw(1 - levelOfDetail);
        delete accum;
    }
}
