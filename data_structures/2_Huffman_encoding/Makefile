# A simple makefile for CSE 100 PA2

CC=g++
CXXFLAGS=-std=c++11 -g -gdwarf-2
LDFLAGS=-g

all: compress uncompress

compress: BitInputStream.o BitOutputStream.o HCNode.o HCTree.o

uncompress: BitInputStream.o BitOutputStream.o HCNode.o HCTree.o

HCTree.o: BitInputStream.hpp BitInputStream.cpp BitOutputStream.hpp BitOutputStream.cpp HCNode.hpp HCNode.cpp HCTree.hpp HCTree.cpp

HCNode.o: HCNode.hpp HCNode.cpp

BitOutputStream.o: BitOutputStream.hpp BitOutputStream.cpp

BitInputStream.o: BitInputStream.hpp BitInputStream.cpp

clean:
	rm -f compress uncompress *.o core*
