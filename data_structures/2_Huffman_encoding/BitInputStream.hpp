/** 
 * Name: Huize Shi
 * Userid: cs100vbo
 * August 12, 2016
 * PA2: BitInputStream.hpp
 */

#ifndef BITINPUTSTREAM_HPP
#define BITINPUTSTREAM_HPP
#include <fstream>

using namespace std;
/** 
 * This is the header file defining the BitInputStream Class
 * It will allow the user to write to file in a bit level
 */
class BitInputStream {
private:
  ifstream& in; // Input stream
  int bitPointer; // The bit being read from the byte
  unsigned char currentByte; // The byte of data that is being read bit wise
  bool continuousInput; // Check if the input is the first time

public:
  BitInputStream(ifstream& in); // Constructor

  bool open(string fileName); // Opening method for the ofstream

  bool get(); // Getting a bit represented as a boolean

  bool eof(); // Checking for end of file

  void close();
};

#endif
