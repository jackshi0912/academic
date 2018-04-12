CC = g++ -std=c++11
CFLAGS = -Wall -O3 -I/opt/local/include -I./glm -I./inc -w
LDFLAGS = -L/opt/local/lib -lm -lfreeimage -I./glm
SOURCES = $(wildcard src/*.cpp)
OBJECTS = $(addprefix bin/, $(notdir $(SOURCES:.cpp=.o)))


all : raytracer

raytracer : main.o scene.o transform.o readfile.o shape.o
	${CC} ${CFLAGS} $(OBJECTS) ${LDFLAGS} -o raytracer

main.o:
	${CC} ${CFLAGS} -c ./src/main.cpp -o ./bin/$@

shape.o:
	${CC} ${CFLAGS} -c ./src/shape.cpp -o ./bin/$@

scene.o:
	${CC} ${CFLAGS} -c ./src/scene.cpp -o ./bin/$@

transform.o:
	${CC} ${CFLAGS} -c ./src/transform.cpp -o ./bin/$@

readfile.o :
	${CC} ${CFLAGS} -c ./src/readfile.cpp -o ./bin/$@

clean :
	rm -rf *o *~ raytracer