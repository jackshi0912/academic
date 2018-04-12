# A simple makefile for CSE 100 P3

CC=g++
CXXFLAGS=-std=c++11 -g -Wall -gdwarf-2
LDFLAGS=-g

all: benchtrie benchdict util.o

benchtrie: util.o DictionaryTrie.o DictionaryBST.o DictionaryHashtable.o

benchdict: util.o DictionaryTrie.o DictionaryBST.o DictionaryHashtable.o

test: test.o util.o DictionaryTrie.o DictionaryBST.o DictionaryHashtable.o

test.o: test.cpp
	g++ -std=c++11 -g -Wall -gdwarf-2 -c test.cpp
	
DictionaryTrie.o: DictionaryTrie.hpp

DictionaryBST.o: DictionaryBST.hpp

DictionaryHashtable.o: DictionaryHashtable.hpp

util.o: util.hpp

clean:
	rm -f benchtrie benchdict test *.o core* *~

