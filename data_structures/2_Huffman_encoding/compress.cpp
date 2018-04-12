/**
 * Name: Huize Shi
 * Userid: cs100vbo
 * Date: August 12, 2016
 * PA2: compress.cpp
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iostream>
#include <queue>
#include "HCTree.hpp"
#include "BitOutputStream.hpp"
#include "PA2.hpp"

void writeFileHeader (ofstream& out, HCTree& tree);

using namespace std;

/** Driver function for compressing */
int main(int argc, char *argv[]) {
  // DEBUG:
  // cerr << "Compress:" << endl << endl;
  if (argc != EXP_ARG) { // Checking user input
    cerr << NUM_ARG_STR;
    return -1;
  }

  // Declaring I/O objects
  ifstream in;
  ofstream out;
  BitOutputStream bitOut(out);

  in.open(argv[IN_FILE_IDX], ios::binary);

  // Open file and check open status
  if (!bitOut.open(argv[OUT_FILE_IDX]) || !in.is_open()) {
    cerr << FILE_OPEN_ERR_STR;
    return -1;
  }
 
  // Check if file is empty
  if (in.peek() == EOF) {
    out.close();
    in.close();
    return 0;
  }

  // Creating vectors of character frequency
  vector<int> freq = vector<int>(ASCII_SIZE, 0);

  // Get frequency
  unsigned char c;
  while (1) {
    in.read((char*)&c, sizeof(unsigned char));
    if (in.eof()) {
      break;
    }
    // Debug: cerr << "Char is:" << c << ", value is: " 
    //     << (int)(static_cast<unsigned char>(c)) << endl;
    freq[c]++;
  }

  in.clear();
  in.seekg(0, ios::beg);

  //Debug: cerr << "after filling freq vector" << endl;

  // Building a huffman tree
  HCTree tree;
  tree.build(freq);

  writeFileHeader(out, tree); // Write Header

  tree.encode(in, bitOut); // Encode

  // Debug: cerr << "After encode" << endl;

  // Close stream
  in.close();
  bitOut.close();
  
  return 0;
}

/** Helper function to write file header for rebuilding the tree */
void writeFileHeader (ofstream& out, HCTree& tree) {
  queue<HCNode*> temp;
  tree.getLeaves(temp);
  unsigned int headerSize = temp.size();
  out.write((char*)&headerSize, sizeof(int)); // Write the num of Nodes to read
  unsigned int freq;
  // DEBUG: cerr << endl;
  while (!temp.empty()) {
    freq = temp.front()->count; // Clear garbage data
    out.write((char*)&temp.front()->symbol, sizeof(char));
    // DEBUG: cerr << "Header node symbol in: " << temp.front()->symbol;
    out.write((char*)&freq, MIN_NUM_OF_BYTES);
    //DEBUG: cerr << "    FreqIn: " << shifted << endl;
    temp.pop();
  }
}
