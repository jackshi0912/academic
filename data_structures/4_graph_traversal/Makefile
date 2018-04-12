# A simple makefile for CSE 100 P4

CC=g++
CXXFLAGS=-std=c++11 -g -Wall -gdwarf-2
LDFLAGS=-g

all: pathfinder actorconnections

actorconnections: ActorGraph.o ActorNode.o

pathfinder: ActorGraph.o ActorNode.o

ActorGraph.o: ActorGraph.hpp

ActorNode.o: ActorNode.hpp

clean:
	rm -f pathfinder *.o core* *~

