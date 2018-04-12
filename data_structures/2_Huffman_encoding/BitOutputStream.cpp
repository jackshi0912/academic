/**
 * Name: Huize Shi
 * Userid: cs100vbo
 * August 12, 2016
 * PA2: BitOutputStream.cpp
 */

#include <fstream>
#include "PA2.hpp"
#include "BitOutputStream.hpp"

using namespace std;
/** The constructor for the BitOutputStream */
BitOutputStream::BitOutputStream(ofstream& out) :out(out) {
  data = bitPointer = 0;
}

/** The opening method for the ofstream to open file */
bool BitOutputStream::open(string fileName) {
  out.open(fileName, ios::binary);
  return out.is_open(); // Return open status
}

/** Put a bit into the bufffer */
void BitOutputStream::put(bool bit) {
  if (bit) { // Check if the bit is 1
    data = data | (FIRST_BIT >> bitPointer); // Put the bit in place
  }
  bitPointer++; // Move on to next bit
  if (bitPointer == BYTE_SIZE) { // Check if it's time to move to next byte
    out.put(data); // Put byte into buffer
    bitPointer = 0;
    data = 0;
  }
}

/** Close method for the stream */
void BitOutputStream::close() {
  out.put(data);
  out.close();
}
