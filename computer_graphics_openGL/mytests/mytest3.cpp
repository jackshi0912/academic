/***************************************************************************/
/* This is a simple demo program written for CSE 167 by Ravi Ramamoorthi   */
/* This program corresponds to the first OpenGL lecture.                   */
/*                                                                         */
/* Successive lectures/iterations make this program more complex.          */
/* This is the first simple program to draw a ground plane allowig zooming */  
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
#include <fstream>
#include <sstream>
#include <iostream>

int mouseoldx, mouseoldy ; // For mouse motion
GLdouble eyeloc = 2.0 ; // Where to look from; initially 0 -2, 2
GLfloat teapotloc = -0.5 ; // ** NEW ** where the teapot is located
GLint animate = 0 ; // ** NEW ** whether to animate or not
GLuint vertexshader, fragmentshader, shaderprogram ; // shaders
GLuint projectionPos, modelviewPos, colorPos; // Locations of uniform variables
glm::mat4 projection, modelview; // The mvp matrices themselves
glm::mat4 identity(1.0f); // An identity matrix used for making transformation matrices

GLubyte woodtexture[256][256][3] ; // ** NEW ** texture (from grsites.com)
GLuint texNames[1] ; // ** NEW ** texture buffer
GLuint istex ;  // ** NEW ** blend parameter for texturing
GLuint islight ; // ** NEW ** for lighting
GLint texturing = 1 ; // ** NEW ** to turn on/off texturing
GLint lighting = 1 ; // ** NEW ** to turn on/off lighting

/* Variables to set uniform params for lighting fragment shader */
GLuint light0dirn ; 
GLuint light0color ; 
GLuint light1posn ; 
GLuint light1color ; 
GLuint ambient ; 
GLuint diffuse ; 
GLuint specular ; 
GLuint shininess ; 


const int DEMO = 5 ; // ** NEW ** To turn on and off features

#include "shaders.h"
#include "geometry3.h"

/* New helper transformation function to transform vector by modelview */ 
void transformvec (const GLfloat input[4], GLfloat output[4]) {
	glm::vec4 inputvec(input[0], input[1], input[2], input[3]);
	glm::vec4 outputvec = modelview * inputvec;
	output[0] = outputvec[0];
	output[1] = outputvec[1];
	output[2] = outputvec[2];
	output[3] = outputvec[3];
}

// Treat this as a destructor function. Delete any dynamically allocated memory here
void deleteBuffers() {
	glDeleteVertexArrays(numobjects + ncolors, VAOs);
	glDeleteVertexArrays(1, &teapotVAO);
	glDeleteBuffers(numperobj*numobjects + ncolors, buffers);
	glDeleteBuffers(3, teapotbuffers);
}

void display(void)
{
  // Clear all pixels in the buffer

  glClear (GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT) ; 

  // draw white polygon (square) of unit length centered at the origin
  // Note that vertices must generally go counterclockwise
  // Change from the first program, in that I just made it white.

  // Does the order of drawing matter?  What happens if I draw the ground 
  // after the pillars?  I will show this in class 

  glUniform1i(islight,0) ; // Turn off lighting (except on teapot, later)
  glUniform1i(istex,texturing) ;

  // Draw the floor
  // Start with no modifications made to the model matrix
  glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
  glUniform3f(colorPos, 1.0f, 1.0f, 1.0f); // The floor is white
  drawtexture(FLOOR,texNames[0]) ; // Texturing floor 
  glUniform1i(istex,0) ; // Other items aren't textured 

  // Now draw several cubes with different transforms, colors

  // We now maintain a stack for the modelview matrices. Changes made to the stack after pushing
  // are discarded once it is popped.
  pushMatrix(modelview);
  // 1st pillar 
  // glm functions build a new matrix. They don't actually modify the passed in matrix.
  // Consequently, we need to assign this result to modelview.
  modelview = modelview * glm::translate(identity, glm::vec3(-0.4, -0.4, 0.0));
  glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
  glUniform3fv(colorPos, 1, _cubecol[0]);
  drawcolor(CUBE, 0);
  popMatrix(modelview);

  // 2nd pillar 
  pushMatrix(modelview);
  modelview = modelview * glm::translate(identity, glm::vec3(0.4, -0.4, 0.0));
  glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
  glUniform3fv(colorPos, 1, _cubecol[1]);
  drawcolor(CUBE, 1);
  popMatrix(modelview);

  // 3rd pillar 
  pushMatrix(modelview);
  modelview = modelview * glm::translate(identity, glm::vec3(0.4, 0.4, 0.0));
  glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
  glUniform3fv(colorPos, 1, _cubecol[2]);
  drawcolor(CUBE, 2);
  popMatrix(modelview);

  // 4th pillar 
  pushMatrix(modelview);
  modelview = modelview * glm::translate(identity, glm::vec3(-0.4, 0.4, 0.0));
  glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]);
  glUniform3fv(colorPos, 1, _cubecol[3]);
  drawcolor(CUBE, 3);
  popMatrix(modelview);

  // Draw the glut teapot 

  /* New for Demo 3; add lighting effects */ 
  {
    const GLfloat one[] = {1, 1, 1, 1};
    const GLfloat medium[] = {0.5f, 0.5f, 0.5f, 1};
    const GLfloat small[] = {0.2f, 0.2f, 0.2f, 1};
    const GLfloat high[] = {100} ;
    const GLfloat zero[] = {0.0, 0.0, 0.0, 1.0} ; 
    const GLfloat light_specular[] = {1, 0.5, 0, 1};
    const GLfloat light_specular1[] = {0, 0.5, 1, 1};
    const GLfloat light_direction[] = {0.5, 0, 0, 0}; // Dir light 0 in w 
    const GLfloat light_position1[] = {0, -0.5, 0, 1};

    GLfloat light0[4], light1[4] ; 

    // Set Light and Material properties for the teapot
    // Lights are transformed by current modelview matrix. 
    // The shader can't do this globally. 
    // So we need to do so manually.  
    transformvec(light_direction, light0) ; 
    transformvec(light_position1, light1) ; 

    glUniform3fv(light0dirn, 1, light0) ; 
    glUniform4fv(light0color, 1, light_specular) ; 
    glUniform4fv(light1posn, 1, light1) ; 
    glUniform4fv(light1color, 1, light_specular1) ; 
    // glUniform4fv(light1color, 1, zero) ; 

    glUniform4fv(ambient,1,small) ; 
    glUniform4fv(diffuse,1,medium) ; 
    glUniform4fv(specular,1,one) ; 
    glUniform1fv(shininess,1,high) ; 

    // Enable and Disable everything around the teapot 
    // Generally, we would also need to define normals etc. 
    // But in old OpenGL, GLUT already does this for us. In modern OpenGL, the 
	// 3D model file for the teapot also defines normals already.
    if (DEMO > 4) 
      glUniform1i(islight,lighting) ; // turn on lighting only for teapot. 

  }
	//  ** NEW ** Put a teapot in the middle that animates 
	glUniform3f(colorPos, 0.0f, 1.0f, 1.0f);
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


  // Does order of drawing matter?
  // What happens if I draw the ground after the pillars? 
  // I will show this in class.

  // glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &(modelview)[0][0]); 
  // drawobject(FLOOR) ; 

  // don't wait!  
  // start processing buffered OpenGL routines 


  glutSwapBuffers() ; 
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
	  eyeloc = 2.0;
	  modelview = glm::lookAt(glm::vec3(0, -eyeloc, eyeloc), glm::vec3(0, 0, 0), glm::vec3(0, 1, 1));
	  // Send the updated matrix over to the shader
	  glUniformMatrix4fv(modelviewPos, 1, GL_FALSE, &modelview[0][0]);
	  glutPostRedisplay();
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

  glutPostRedisplay() ;
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
      if (animate) glutIdleFunc(animation) ;
      else glutIdleFunc(NULL) ;
      break ;
    case 't': // ** NEW ** to turn on/off texturing ; 
      texturing = !texturing ;
      glutPostRedisplay() ; 
      break ;
    case 's': // ** NEW ** to turn on/off shading (always smooth) ; 
      lighting = !lighting ;
      glutPostRedisplay() ; 
      break ;
    default:
      break ;
  }
}

/* Reshapes the window appropriately */
void reshape(int w, int h)
{
	glViewport(0, 0, (GLsizei)w, (GLsizei)h);

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
  modelview = glm::lookAt(glm::vec3(0, -eyeloc, eyeloc), glm::vec3(0, 0, 0), glm::vec3(0, 1, 1));


  // Initialize the shaders
  vertexshader = initshaders(GL_VERTEX_SHADER, "shaders/light.vert") ;
  fragmentshader = initshaders(GL_FRAGMENT_SHADER, "shaders/light.frag") ;
  shaderprogram = initprogram(vertexshader, fragmentshader) ; 

  // * NEW * Set up the shader parameter mappings properly for lighting.
  islight = glGetUniformLocation(shaderprogram,"islight") ;        
  light0dirn = glGetUniformLocation(shaderprogram,"light0dirn") ;       
  light0color = glGetUniformLocation(shaderprogram,"light0color") ;       
  light1posn = glGetUniformLocation(shaderprogram,"light1posn") ;       
  light1color = glGetUniformLocation(shaderprogram,"light1color") ;       
  ambient = glGetUniformLocation(shaderprogram,"ambient") ;       
  diffuse = glGetUniformLocation(shaderprogram,"diffuse") ;       
  specular = glGetUniformLocation(shaderprogram,"specular") ;       
  shininess = glGetUniformLocation(shaderprogram,"shininess") ;

  // Get the positions of other uniform variables
  projectionPos = glGetUniformLocation(shaderprogram, "projection");
  modelviewPos = glGetUniformLocation(shaderprogram, "modelview");
  colorPos = glGetUniformLocation(shaderprogram, "color");

  // Now create the buffer objects to be used in the scene later
  glGenVertexArrays(numobjects + ncolors, VAOs);
  glGenVertexArrays(1, &teapotVAO);
  glGenBuffers(numperobj * numobjects + ncolors + 1, buffers); // 1 extra buffer for the texcoords
  glGenBuffers(3, teapotbuffers);

  // Initialize texture
  inittexture("wood.ppm", shaderprogram) ; 

  // Initialize objects
  initobject(FLOOR, (GLfloat *) floorverts, sizeof(floorverts), (GLfloat *) floorcol, sizeof (floorcol), (GLubyte *) floorinds, sizeof (floorinds), GL_TRIANGLES) ; 
  initcubes(CUBE, (GLfloat *)cubeverts, sizeof(cubeverts), (GLubyte *)cubeinds, sizeof(cubeinds), GL_TRIANGLES);
  loadteapot();

  // Enable the depth test
  glEnable(GL_DEPTH_TEST) ;
  glDepthFunc (GL_LESS) ; // The default option
}

int main(int argc, char** argv)
{
  glutInit(&argc, argv);

  // Requests the type of buffers (Single, RGB).
  // Think about what buffers you would need...

  // Request the depth if needed, later switch to double buffer 
  // Due to its unique headers, OSX systems require another flag
  glutInitDisplayMode (GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH);

  glutInitWindowSize (500, 500); 
  glutInitWindowPosition (100, 100);
  glutCreateWindow ("Simple Demo with Shaders");

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
