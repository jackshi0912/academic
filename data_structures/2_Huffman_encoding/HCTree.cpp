/**
 * Name: Huize Shi
 * Userid: cs100vpo
 * Date: August 12, 2016
 * PA2: HCTree.cpp
 */

#include <queue>
#include <vector>
#include <iterator>
#include <stack>
#include "HCTree.hpp"
#include "PA2.hpp"
#include "BitInputStream.hpp"
#include "BitOutputStream.hpp"

using namespace std;

/** Initializing tree method */
void HCTree::build(const vector<int>& freq) {

  priority_queue<HCNode*, vector<HCNode*>, HCNodePtrComp> pq;
  HCNode* tempNode;
  
  #pragma omp parallel for
  for ( unsigned int i = 0; i < freq.size(); i++ ) {
    if (!freq[i]) { // If occurrence is 0, ignore
      continue;
    }
    // None 0 freq byte char detechted
    tempNode = new HCNode(freq[i], i); // Make tempNode
    // Debug:
    //cerr << "Node created: " << i << ": " << freq[i] << endl;
    leaves[i] = tempNode; // Add leaf
    pq.push(tempNode); // push the tempNode
  }
  
  pq.push(endNode);

  // Building the tree by taking off item from priority queue
  HCNode *c0;
  HCNode *c1;
  HCNode *tempParent;

  while (pq.size() > 1) {
    // Obtain least frequent nodes
    c0 = pq.top();
    pq.pop();
    c1 = pq.top();
    pq.pop();

    // Making a temp parent
    tempParent =  new HCNode( c1->count + c0->count, 
        (c0->symbol > c1->symbol)?(c0->symbol):c1->symbol, c0, c1, nullptr);

    // Linking the nodes
    tempParent->c0 = c0;
    tempParent->c1 = c1;
    c0->p = tempParent;
    c1->p = tempParent;

    pq.push(tempParent); // Push the parent onto the priority queue
  }

  root = tempParent; // return the master root
}

/** Over loaded build method. Uses a vector of leaves */
void HCTree::build(const vector<HCNode*>& continuousLeaves) {

  priority_queue<HCNode*, vector<HCNode*>, HCNodePtrComp> pq;

  for ( unsigned int i = 0; i < continuousLeaves.size(); i++ ) {
    // Debug:
    //cerr << "Node created: " << i << ": " << freq[i] << endl;
    leaves[(int)continuousLeaves[i]->symbol] = continuousLeaves[i];
    pq.push(continuousLeaves[i]);
  }
  
  pq.push(endNode);

  // Building the tree by taking off item from priority queue
  HCNode *c0;
  HCNode *c1;
  HCNode *tempParent;
  while (pq.size() > 1) {
    // Obtain least frequent nodes
    c0 = pq.top();
    pq.pop();
    c1 = pq.top();
    pq.pop();

    // Making a temp parent
    tempParent =  new HCNode( c1->count + c0->count, 
        (c0->symbol > c1->symbol)?(c0->symbol):c1->symbol, c0, c1, nullptr);

    // Linking the nodes
    tempParent->c0 = c0;
    tempParent->c1 = c1;
    c0->p = tempParent;
    c1->p = tempParent;

    pq.push(tempParent); // Push the parent onto the priority queue
  }

  root = tempParent; // return the master root
}

/** Fill the queue with leaf nodes */
void HCTree::getLeaves(queue<HCNode*>& hctree) {
  #pragma omp parallel for
  for (int i = 0; i < ASCII_SIZE; i++) {
    if (leaves[i]) {
      hctree.push(leaves[i]);
    }
  }
}

/** Encoding method with ASCII Output */
void HCTree::encode(byte symbol, ofstream& out) const {
  stack<char> code; // create a stack
  HCNode *curNode = leaves[(int)(symbol)]; // Obtain the leaf node

  while (curNode->p) { // Loop up the tree
    if (curNode == curNode->p->c0){ // Check if children 0
      code.push('0');
    } else if (curNode == curNode->p->c1){ // Check if children 1
      code.push('1');
    }
    curNode = curNode->p;
  }

  while(!code.empty()) {
    //cerr << code.top();
    out << code.top();
    code.pop();
  }
}

/** Decoding method with ASCII input */
void HCTree::decode(ifstream& in, ofstream& out) const {

  HCNode *curNode;
  char c;
  bool end = false;
  while (!in.eof()) { // Going through the file
    curNode = root; // Establishing root pointer
    while (curNode->c1 || curNode->c0) { // going down the tree
      if (!in.get(c)) { // Check for end condition
        end = true;
        break;
      }
      if (c == '0') {
        curNode = curNode->c0; // Travel left
      } else if (c =='1'){ // Travel right
        curNode = curNode->c1;
      }
    }
    if (!end) {
      //cerr << (char)curNode->symbol;
      out << (char)curNode->symbol;
    }
  }
}

/** Bit output decode */
void HCTree::encode(ifstream& in, BitOutputStream& out) const {
  HCNode *curNode;
  char c;
  stack<int> code;

  while (1) {
    in.get(c); // Getting a byte
    if (in.eof()) {
      curNode = endNode;
    } else {
      // Finding the leaf
      curNode = leaves[(int)(static_cast<unsigned char>(c))];
    }
    // DEBUG: cerr << "Encoding symbol: " << curNode->symbol << ": ";
    
    while (curNode->p) { // Going up the tree
      if (curNode == curNode->p->c0) { // Check if the node is c0
        // DEBUG: cerr << "push 0 onto stack" << endl;
        code.push(0);
      } else if (curNode == curNode->p->c1) { // Check if the node is c1
        // DEBUG: cerr << "push 1 onto stack" << endl;
        code.push(1);
      }
      curNode = curNode->p; // Moving up the tree
    }

    // DEBUG: cerr << "Popping off stack: " << endl;

    while(!code.empty()) { // Invert the order of bit pattern
      out.put(code.top());
      // DEBUG: cerr << code.top();
      code.pop();
    }
    // DEBUG: cerr << endl;
    if (in.eof()) {
      break;
    }
  }
}

/** Bit input decode */
void HCTree::decode(BitInputStream& in, ofstream& out) const {
  HCNode *curNode;
  bool dir;
  while (1) {
    if (in.eof()) {
      break;
    }
    curNode = root;
    //DEBUG: cerr << "Decode symbol: ";
    while (curNode->c0 || curNode->c1){
      dir = in.get();
      if (in.eof()) {
        break;
      } 
      if(dir) {
        // DEBUG: cerr << 1;
        curNode = curNode->c1;
      } else {
        curNode = curNode->c0;
        // DEBUG: cerr << 0;
      }
    }
    if(curNode == endNode) {
      // DEBUG: cerr << endl << "EndNode detected" << endl;
      break;
    }
    // DEBUG: cerr << endl;
    out << curNode->symbol;
  }
}

HCTree::~HCTree() {
  memDelete(root);
}

// Post traverse delete the tree
void HCTree::memDelete(HCNode *root) {
  if (!root) {
    return;
  }
  HCTree::memDelete(root->c0);
  HCTree::memDelete(root->c1);
  delete root;
}
