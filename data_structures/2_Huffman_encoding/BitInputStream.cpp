/** Name: Huize Shi
 *  Userid: cs100vbo
 *  Date: August 12, 2016
 *  BitInputStream.cpp
 */
#include <fstream>
#include "BitInputStream.hpp"
#include "PA2.hpp"

using namespace std;

/** Constructor for the BitInputStream */
BitInputStream::BitInputStream(ifstream& in) :in(in) {
  // Initialize all members to null
  continuousInput = bitPointer = currentByte = 0;
}

bool BitInputStream::open(string fileName) {
  in.open(fileName, ios::binary);
  return in.is_open();
}

bool BitInputStream::get() {
  // Check if all bits has been visited in this byte
  if (!continuousInput) { // First time getting char
    continuousInput = true;
    char c;
    in.get(c); // Moving on to new byte
    currentByte = (static_cast<unsigned char>(c));
  }
  if (bitPointer >= BYTE_SIZE) { // Getting new char
    char c;
    in.get(c); // Moving on to new byte
    currentByte = (static_cast<unsigned char>(c));
    bitPointer = 0; // bitPointer set to 0
  }

  // Getting the next bit in the byte
  if (currentByte & (FIRST_BIT >> bitPointer)) {
    bitPointer++;
    return true;
  } else {
    bitPointer++;
    return false;
  }
}

bool BitInputStream::eof() {
  return in.eof();
}

void BitInputStream::close() {
  in.close();
}
