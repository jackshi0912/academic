/**
 * Name: Huize Shi
 * Userid: cs100vbo
 * Date: August 12, 2016
 * PA2: BitOutputStream.hpp
 */

#ifndef BITOUTPUTSTREAM_HPP
#define BITOUTPUTSTREAM_HPP
#include <fstream>

using namespace std;

/** This is the class definition for bit wise output stream */
class BitOutputStream {
private:
  unsigned char data; // The data to be written
  int bitPointer; // The bit location
  ofstream& out;
  
public:
  BitOutputStream(ofstream& out); // Constructor

  bool open(string fileName);
  void put(bool bit); // Put a bit into the buffer
  void close();
};

#endif
