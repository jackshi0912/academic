/**** Basic setup for defining and drawing objects ****/
// Moved to a header for the second OpenGL program
// Rewritten in August 2016 by hvt001

#ifndef __INCLUDEGEOMETRY
#define __INCLUDEGEOMETRY

#include <vector>

// May need to replace with absolute path on some systems
#define PATH_TO_TEAPOT_OBJ "teapot.obj"

const int numobjects = 2 ; // ** NEW ** number of objects for buffer 
const int numperobj  = 3 ; 
const int ncolors = 4 ; 
GLuint VAOs[numobjects + ncolors], teapotVAO; // A VAO for each object
GLuint buffers[numperobj*numobjects+ncolors], teapotbuffers[3] ; // ** NEW ** List of buffers for geometric data 
GLuint objects[numobjects] ; // ** NEW ** For each object
GLenum PrimType[numobjects] ;
GLsizei NumElems[numobjects] ; 

// For the geometry of the teapot
std::vector <glm::vec3> teapotVertices;
std::vector <glm::vec3> teapotNormals;
std::vector <unsigned int> teapotIndices;

// To be used as a matrix stack for the modelview.
std::vector <glm::mat4> modelviewStack;

// ** NEW ** Floor Geometry is specified with a vertex array
// ** NEW ** Same for other Geometry 

enum {Vertices, Colors, Elements} ; // For arrays for object 
enum {FLOOR, CUBE} ; // For objects, for the floor

const GLfloat floorverts[4][3] = {
  {0.5, 0.5, 0.0}, {-0.5, 0.5, 0.0}, {-0.5, -0.5, 0.0}, {0.5, -0.5, 0.0}
} ; 
const GLfloat floorcol[4][3] = {
  {1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}, {1.0, 1.0, 1.0}
} ; 
const GLubyte floorinds[1][6] = { {0, 1, 2, 0, 2, 3} } ; 
const GLfloat floortex[4][2] = { 
  {1.0, 1.0}, {0.0, 1.0}, {0.0, 0.0}, {1.0, 0.0}
} ;

// CUBES FOR ADDING TO THE SCENE

const GLfloat wd = 0.1 ; 
const GLfloat ht = 0.5 ; 
const GLfloat _cubecol[4][3] = { 
  {1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}, {1.0, 1.0, 0.0} } ; 
const GLfloat cubeverts[8][3] = {
  {-wd, -wd, 0.0}, {-wd, wd, 0.0}, {wd, wd, 0.0}, {wd, -wd, 0.0},
  {-wd, -wd, ht}, {wd, -wd, ht}, {wd, wd, ht}, {-wd, wd, ht}
} ; 
GLfloat cubecol[12][3] ;
const GLubyte cubeinds[12][3] = { 
  {0, 1, 2}, {0, 2, 3}, // BOTTOM 
  {4, 5, 6}, {4, 6, 7}, // TOP 
  {0, 4, 7}, {0, 7, 1}, // LEFT 
  {0, 3, 5}, {0, 5, 4}, // FRONT
  {3, 2, 6}, {3, 6, 5}, // RIGHT 
  {1, 7, 6}, {1, 6, 2} // BACK
} ; 


void initobject(GLuint object, GLfloat * vert, GLint sizevert, GLfloat * col, GLint sizecol, GLubyte * inds, GLint sizeind, GLenum type) ;
void initcubes(GLuint object, GLfloat * vert, GLint sizevert, GLubyte * inds, GLint sizeind, GLenum type);
void drawobject(GLuint object) ;
void drawcolor(GLuint object, GLuint color) ;
void loadteapot();
void drawteapot();
void pushMatrix(glm::mat4);
void popMatrix(glm::mat4&);

// This function takes in a vertex, color, index and type array 
// And does the initialization for an object.  
// The partner function below it draws the object 

void initobject(GLuint object, GLfloat * vert, GLint sizevert, GLfloat * col, GLint sizecol, GLubyte * inds, GLint sizeind, GLenum type) {
   int offset = object * numperobj ; 
   glBindVertexArray(VAOs[object]);
   glBindBuffer(GL_ARRAY_BUFFER, buffers[Vertices + offset]);
   glBufferData(GL_ARRAY_BUFFER, sizevert, vert, GL_STATIC_DRAW);
   // Use layout location 0 for the vertices
   glEnableVertexAttribArray(0);
   glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), 0);

   glBindBuffer(GL_ARRAY_BUFFER, buffers[Colors + offset]);
   glBufferData(GL_ARRAY_BUFFER, sizecol, col, GL_STATIC_DRAW);
   // Use layout location 1 for the colors
   glEnableVertexAttribArray(1);
   glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), 0);
   glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffers[Elements + offset]);
   glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeind, inds, GL_STATIC_DRAW);
   PrimType[object] = type;
   NumElems[object] = sizeind;
   // Prevent further modification of this VAO by unbinding it
   glBindVertexArray(0);
}

// This function initializes a bunch of color cubes
void initcubes(GLuint object, GLfloat * vert, GLint sizevert, GLubyte * inds, GLint sizeind, GLenum type) {
	for (int i = 0; i < ncolors; i++) {
		for (int j = 0; j < 8; j++)
			for (int k = 0; k < 3; k++)
				cubecol[j][k] = _cubecol[i][k];
		glBindVertexArray(VAOs[object + i]);
		int offset = object * numperobj;
		int base = numobjects * numperobj;
		glBindBuffer(GL_ARRAY_BUFFER, buffers[Vertices + offset]);
		glBufferData(GL_ARRAY_BUFFER, sizevert, vert, GL_STATIC_DRAW);
		// Use layout location 0 for the vertices
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), 0);

		glBindBuffer(GL_ARRAY_BUFFER, buffers[base + i]);
		glBufferData(GL_ARRAY_BUFFER, sizeof(cubecol), cubecol, GL_STATIC_DRAW);
		// Use layout location 1 for the colors
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, buffers[Elements + offset]);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeind, inds, GL_STATIC_DRAW);
		PrimType[object] = type;
		NumElems[object] = sizeind;
		// Prevent further modification of this VAO by unbinding it
		glBindVertexArray(0);
	}
}

// And a function to draw with them, similar to drawobject but with color
void drawcolor(GLuint object, GLuint color) {
   glBindVertexArray(VAOs[object + color]);
   glDrawElements(PrimType[object], NumElems[object], GL_UNSIGNED_BYTE, 0);
   glBindVertexArray(0);
}


void drawobject(GLuint object) {
   glBindVertexArray(VAOs[object]);
   glDrawElements(PrimType[object], NumElems[object], GL_UNSIGNED_BYTE, 0);
   glBindVertexArray(0);
}

void loadteapot() {
	FILE* fp;
	float x, y, z;
	int fx, fy, fz, ignore;
	int c1, c2;
	float minY = INFINITY, minZ = INFINITY;
	float maxY = -INFINITY, maxZ = -INFINITY;

	fp = fopen(PATH_TO_TEAPOT_OBJ, "rb");

	if (fp == NULL) {
		std::cerr << "Error loading file: " << PATH_TO_TEAPOT_OBJ << std::endl;
		exit(-1);
	}

	while (!feof(fp)) {
		c1 = fgetc(fp);
		while (!(c1 == 'v' || c1 == 'f')) {
			c1 = fgetc(fp);
			if (feof(fp))
				break;
		}
		c2 = fgetc(fp);

		if ((c1 == 'v') && (c2 == ' ')) {
			fscanf(fp, "%f %f %f", &x, &y, &z);
			teapotVertices.push_back(glm::vec3(x, y, z));
			if (y < minY) minY = y;
			if (z < minZ) minZ = z;
			if (y > maxY) maxY = y;
			if (z > maxZ) maxZ = z;
		}
		else if ((c1 == 'v') && (c2 == 'n')) {
			fscanf(fp, "%f %f %f", &x, &y, &z);
			// Ignore the normals in mytest2, as we use a solid color for the teapot.
			teapotNormals.push_back(glm::vec3(0.0, 1.0, 1.0));
		}
		else if ((c1 == 'f'))
		{
			fscanf(fp, "%d//%d %d//%d %d//%d", &fx, &ignore, &fy, &ignore, &fz, &ignore);
			teapotIndices.push_back(fx - 1);
			teapotIndices.push_back(fy - 1);
			teapotIndices.push_back(fz - 1);
		}
	}

	fclose(fp); // Finished parsing
				// Recenter the teapot
	float avgY = (minY + maxY) / 2.0f - 0.02f;
	float avgZ = (minZ + maxZ) / 2.0f;
	for (unsigned int i = 0; i < teapotVertices.size(); ++i) {
		glm::vec3 shiftedVertex = teapotVertices[i] - glm::vec3(0.0f, avgY, avgZ);
		teapotVertices[i] = shiftedVertex;
	}
	// Done loading teapot file, now bind it
	glBindVertexArray(teapotVAO);

	glBindBuffer(GL_ARRAY_BUFFER, teapotbuffers[0]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(glm::vec3) * teapotVertices.size(), &teapotVertices[0], GL_STATIC_DRAW);
	glEnableVertexAttribArray(0); // This allows usage of layout location 0 in the vertex shader
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), 0);
	glBindBuffer(GL_ARRAY_BUFFER, teapotbuffers[1]);
	glBufferData(GL_ARRAY_BUFFER, sizeof(glm::vec3) * teapotNormals.size(), &teapotNormals[0], GL_STATIC_DRAW);
	glEnableVertexAttribArray(1); // This allows usage of layout location 1 in the vertex shader
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 3 * sizeof(GLfloat), 0);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, teapotbuffers[2]);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(unsigned int) * teapotIndices.size(), &teapotIndices[0], GL_STATIC_DRAW);

	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindVertexArray(0);
}

void drawteapot() {
	glBindVertexArray(teapotVAO);
	glDrawElements(GL_TRIANGLES, teapotIndices.size(), GL_UNSIGNED_INT, 0);
	glBindVertexArray(0);
}

// This function pushes the specified matrix onto the modelview stack
void pushMatrix(glm::mat4 mat) {
	modelviewStack.push_back(glm::mat4(mat));
}

// This function pops a matrix from the modelview stack and assigns that to the matrix passed in
void popMatrix(glm::mat4& mat) {
	if (modelviewStack.size()) {
		mat = glm::mat4(modelviewStack.back());
		modelviewStack.pop_back();
	}
	else { // Just to prevent errors when popping from an empty stack.
		mat = glm::mat4(1.0f);
	}
}

#endif
