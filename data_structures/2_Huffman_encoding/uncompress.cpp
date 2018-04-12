/**
 * Name: Huize (Jack) Shi
 * Userid: cs100vpo
 * Date: August
 * PA2: Uncompress.cpp
 */

#include <iostream>
#include <fstream>
#include <vector>
#include <iostream>
#include "HCTree.hpp"
#include "PA2.hpp"

void getTree(ifstream& in, vector<HCNode*>& treeData);

using namespace std;

/** Driver fucnction for uncompressing */
int main(int argc, char *argv[]) {

  // DEBUG: cerr << endl << "Uncompress: " << endl;

  if (argc != EXP_ARG) { // Checking user input
    cerr << NUM_ARG_STR;
    return -1;
  }

  // Opening input and output file
  ifstream in;
  ofstream out;
  BitInputStream bitIn(in);

  out.open(argv[OUT_FILE_IDX], ios::binary);
  
  if (!bitIn.open(argv[IN_FILE_IDX]) || !out.is_open()) { 
    cerr << FILE_OPEN_ERR_STR; 
    return -1;
  }

  // Check if file is empty
  if (in.peek() == EOF) {
    in.close();
    out.close();
    return 0;
  }

  // Creating vectors of leaves
  vector<HCNode*> treeData;

  // Obtain the data to reconstruct the tree
  getTree(in, treeData);


  // Building a huffman tree
  HCTree tree;
  tree.build(treeData);

  // Run decode
  tree.decode(bitIn, out);

  // Debug:
  // cerr << "After decode" << endl;

  // Close stream
  in.close();
  out.close();
  
  return 0;
}

/** Read the file header for tree information */
void getTree(ifstream& in, vector<HCNode*>& treeData) {
  //DEBUG: cerr << endl << "Getting leaves: " << endl;
  char c;
  unsigned char symbol;
  unsigned int freq;
  unsigned int headerSize;
  in.read((char*)&headerSize, sizeof(int));
  //DEBUG: cerr << endl;
  #pragma omp parallel for
  for (unsigned int i = 0; i < headerSize; i++) {
    freq = 0; // Clear garbage data
    in.read((char*)&symbol, sizeof(char));
    // DEBUG: cerr << "Node Symbol out: " << symbol;
    in.read((char*)&freq, MIN_NUM_OF_BYTES);
    //DEBUG: cerr << "      FreqOut: " << freq << endl;
    treeData.push_back(new HCNode(freq, symbol));
  }
}
