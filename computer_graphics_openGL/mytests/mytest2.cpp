/***************************************************************************/
/* This is a simple demo program written for CSE 167 by Ravi Ramamoorthi   */
/* This program corresponds to the first OpenGL lecture.                   */
/*                                                                         */
/* Successive lectures/iterations make this program more complex.          */
/* This is the first simple program to draw a ground plane allowing zooming*/  
/* The intent is to show how to draw a simple scene.                       */
/* Modified in August 2016 by Hoang Tran to exclusively use modern OpenGL  */
/***************************************************************************/


// I start with the modified first opengl program to do shaders
// I am going to add cubes and teapots to my scene.

#include <stdio.h>
#include <assert.h>
#include <stdlib.h>
#include <GL/glew.h>
#include <GL/glut.h>
// Usage of degrees is deprecated. Use radians for glm functions.
#define GLM_FORCE_RADIANS
#include <glm/glm.hpp>
#include <glm/gtc/matrix_transform.hpp>
#include "shaders.h"
#include "geometry.h"
#include <fstream>
#include <sstream>
#include <iostream>

int mouseoldx, mouseoldy ; // For mouse motion
GLfloat eyeloc = 2.0 ; // Where to look from; initially 0 -2, 2
GLfloat teapotloc = -0.5 ; // ** NEW ** where the teapot is located
GLint animate = 0 ; // ** NEW ** whether to animate or not
GLuint vertexshader, fragmentshader, shaderprogram ; // shaders
GLuint projectionPos, modelviewPos; // Locations of uniform variables
glm::mat4 projection, modelview; // The mvp matrices themselves
glm::mat4 identity(1.0f); // An identity matrix used for making transformation matrices

const int DEMO = 4  ; // ** NEW ** To turn on and off features

// Treat this as a destructor function. Delete any dynamically allocated memory here
void deleteBuffers() {
glDeleteVertexArrays(numobjects + ncolors, VAOs);
glDeleteVertexArrays(1, &teapotVAO);
glDeleteBuffers(numperobj*numobjects + ncolors, buffers);
glDeleteBuffers(3, teapotbuffers);
}

void display(void)
{
  // clear all pixels  
  // If (DEMO >= 2) also clear the depth buffer 

  if (DEMO >= 2) glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) ; 
  else glClear (GL_COLOR_BUFFER_BIT);

  // draw white polygon (square) of unit length centered at the origin
  // Note that vertices must generally go counterclockwise
  // Change from the first program, in that I just made it white.

  // Does the order of drawing matter?  What happens if I draw the ground 
  // after the pillars?  I will show this in class 

  // Start with no modifications made to the model matrix
  glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
  drawobject(FLOOR) ;

  // Now draw several cubes with different transforms, colors
  // I continue to use the deprecated push-pop and matrix mode 
  // Since it is convenient (or you have to write your own stack).  

  if (DEMO > 0) {
    // We now maintain a stack for the modelview matrices. Changes made to the stack after pushing
	// are discarded once it is popped.
	pushMatrix(modelview);
	// 1st pillar 
	// glm functions build a new matrix. They don't actually modify the passed in matrix.
	// Consequently, we need to assign this result to modelview.
	modelview = modelview * glm::translate(identity, glm::vec3(-0.4, -0.4, 0.0));
	glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
    drawcolor(CUBE, 0) ;
	popMatrix(modelview);

    // 2nd pillar 
	pushMatrix(modelview);
	modelview = modelview * glm::translate(identity, glm::vec3(0.4, -0.4, 0.0));
	glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
	drawcolor(CUBE, 1) ;
	popMatrix(modelview);

    // 3rd pillar 
	pushMatrix(modelview);
	modelview = modelview * glm::translate(identity, glm::vec3(0.4, 0.4, 0.0));
	glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
	drawcolor(CUBE, 2) ;
	popMatrix(modelview);

    // 4th pillar 
	pushMatrix(modelview);
	modelview = modelview * glm::translate(identity, glm::vec3(-0.4, 0.4, 0.0));
	glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
	drawcolor(CUBE, 3) ;
	popMatrix(modelview);
  }

  // Draw a teapot. GLUT's teapot uses the old OpenGL, which is not usable in the modern OpenGL
  // context, therefore a 3D model of the teapot is loaded (in the init() func) and drawn instead.
  if (DEMO >= 3) {
	//  ** NEW ** Put a teapot in the middle that animates
	pushMatrix(modelview);
	modelview = modelview * glm::translate(identity, glm::vec3(teapotloc, 0.0, 0.0));

    //  The following two transforms set up and center the teapot 
    //  Remember that transforms right-multiply the modelview matrix (top of the stack)
	modelview = modelview * glm::translate(identity, glm::vec3(0.0, 0.0, 0.1));
	modelview = modelview * glm::rotate(identity, glm::pi<float>() / 2.0f, glm::vec3(1.0, 0.0, 0.0));
	float size = 0.235f; // Teapot size
	modelview = modelview * glm::scale(identity, glm::vec3(size, size, size));
	glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
    drawteapot() ;
	popMatrix(modelview);
  }

  // Does order of drawing matter? 
  // What happens if I draw the ground after the pillars? 
  // I will show this in class.

  // glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]); 
  // drawobject(FLOOR) ; 

  // don't wait!  
  // start processing buffered OpenGL routines 

  if (DEMO >= 2) glutSwapBuffers() ; 
  glFlush ();
}

// ** NEW ** in this assignment, is an animation of a teapot 
// Hitting p will pause this animation; see keyboard callback

void animation(void) {
  teapotloc = teapotloc + 0.005 ;
  if (teapotloc > 0.5) teapotloc = -0.5 ;
  glutPostRedisplay() ;  
}


// Defines a Mouse callback to zoom in and out 
// This is done by modifying gluLookAt         
// The actual motion is in mousedrag           
// mouse simply sets state for mousedrag       
void mouse(int button, int state, int x, int y) 
{
  if (button == GLUT_LEFT_BUTTON) {
    if (state == GLUT_UP) {
      // Do Nothing ;
    }
    else if (state == GLUT_DOWN) {
      mouseoldx = x ; mouseoldy = y ; // so we can move wrt x , y 
    }
  }
  else if (button == GLUT_RIGHT_BUTTON && state == GLUT_DOWN) 
  { 
	// Reset lookAt
    eyeloc = 2.0 ;
	modelview = glm::lookAt(glm::vec3(0, -eyeloc, eyeloc), glm::vec3(0, 0, 0), glm::vec3(0, 1, 1));
	// Send the updated matrix over to the shader
	glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &modelview[0][0]);
    glutPostRedisplay() ;
  }
}

void mousedrag(int x, int y) {
  int yloc = y - mouseoldy  ;    // We will use the y coord to zoom in/out
  eyeloc  += 0.005*yloc ;         // Where do we look from
  if (eyeloc < 0) eyeloc = 0.0 ;
  mouseoldy = y ;

  /* Set the eye location */
  modelview = glm::lookAt(glm::vec3(0, -eyeloc, eyeloc), glm::vec3(0, 0, 0), glm::vec3(0, 1, 1));
  // Send the updated matrix over to the shader
  glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &modelview[0][0]);

  glutPostRedisplay();
}

// Defines what to do when various keys are pressed 
void keyboard (unsigned char key, int x, int y) 
{
  switch (key) {
    case 27:  // Escape to quit
      exit(0) ;
      break ;
    case 'p': // ** NEW ** to pause/restart animation
      animate = !animate ;
      if (DEMO >= 3) {
        if (animate) glutIdleFunc(animation) ;
        else glutIdleFunc(NULL) ;
      }
      break ;
    default:
      break ;
  }
}

/* Reshapes the window appropriately */
void reshape(int w, int h)
{
  glViewport (0, 0, (GLsizei) w, (GLsizei) h);

  // Think about the rationale for this choice for glm::perspective 
  // What would happen if you changed near and far planes? 
  // Note that the field of view takes in a radian angle!
  projection = glm::perspective(30.0f / 180.0f * glm::pi<float>(), (GLfloat)w / (GLfloat)h, 1.0f, 10.0f);
  glUniformMatrix4fv(projectionPos, 1, GL_FALSE, &projection[0][0]);
}


void init (void) 
{
  /* select clearing color 	*/
  glClearColor (0.0, 0.0, 0.0, 0.0);

  /* initialize viewing values  */
  projection = glm::mat4(1.0f); // The identity matrix

  // Think about this.  Why is the up vector not normalized?
  modelview = glm::lookAt(glm::vec3(0, -eyeloc, eyeloc), glm::vec3(0, 0, 0), glm::vec3(0, 1, 1));

  // Now create the buffer objects to be used in the scene later
  // Remember to delete all the VAOs and VBOs that you create when the program terminates!
  glGenVertexArrays(numobjects + ncolors, VAOs);
  glGenVertexArrays(1, &teapotVAO);
  glGenBuffers(numperobj * numobjects + ncolors, buffers) ; 
  glGenBuffers(3, teapotbuffers);

  // Initialize the objects we're going to draw
  initobject(FLOOR, (GLfloat *) floorverts, sizeof(floorverts), (GLfloat *) floorcol, sizeof (floorcol), (GLubyte *) floorinds, sizeof (floorinds), GL_TRIANGLES) ; 
  initcubes(CUBE, (GLfloat *)cubeverts, sizeof(cubeverts), (GLubyte *)cubeinds, sizeof(cubeinds), GL_TRIANGLES);
  loadteapot();

  if (DEMO >= 2) {
    // Enable the depth test
    glEnable(GL_DEPTH_TEST) ;
    glDepthFunc (GL_LESS) ; // The default option
  }

  // Initialize the shader program
  vertexshader = initshaders(GL_VERTEX_SHADER, "shaders/nop.vert") ;
  fragmentshader = initshaders(GL_FRAGMENT_SHADER, "shaders/nop.frag") ;
  shaderprogram = initprogram(vertexshader, fragmentshader) ;
  // Get the positions of the uniform variables
  projectionPos = glGetUniformLocation(shaderprogram, "projection");
  modelviewPos = glGetUniformLocation(shaderprogram, "modelview");
  // Pass the projection and modelview matrices to the shader
  glUniformMatrix4fv(projectionPos, 1, GL_FALSE, &projection[0][0]);
  glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
}

int main(int argc, char** argv)
{
  glutInit(&argc, argv);

  // Requests the type of buffers (Single, RGB).
  // Think about what buffers you would need...

  // Request the depth if needed, later swith to double buffer 
  if (DEMO >= 2)
	  glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);
  else
	  glutInitDisplayMode(GLUT_SINGLE | GLUT_RGB);

  glutInitWindowSize (500, 500); 
  glutInitWindowPosition (100, 100);
  glutCreateWindow ("Simple Demo wih Shaders");

  GLenum err = glewInit();
  if (GLEW_OK != err) {
	  std::cerr << "Error: " << glewGetString(err) << std::endl;
  }

  init (); // Always initialize first

  // Now, we define callbacks and functions for various tasks.
  glutDisplayFunc(display); 
  glutReshapeFunc(reshape) ;
  glutKeyboardFunc(keyboard);
  glutMouseFunc(mouse) ;
  glutMotionFunc(mousedrag) ;

  glutMainLoop(); // Start the main code
  deleteBuffers(); // Program terminate. Destroy dynamically allocated memory.
  return 0;   /* ANSI C requires main to return int. */
}
