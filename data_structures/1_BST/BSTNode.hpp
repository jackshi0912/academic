/* Filename: BSTNode.hpp
 * Author: Huize Shi
 * Userid: cs100vbo
 * Description: The class defining the Node for the binary search tree
 * Date: August 5, 2016
 */

#ifndef BSTNODE_HPP
#define BSTNODE_HPP
#include <iostream>
#include <iomanip>

template<typename Data>
class BSTNode {

public:

  /** Constructor.  Initialize a BSTNode with the given Data item,
   *  no parent, and no children.
   */
  BSTNode(const Data & d) : data(d) {
    left = right = parent = 0;
  }

  // Fields of the BSTNode
  BSTNode<Data>* left;
  BSTNode<Data>* right;
  BSTNode<Data>* parent;
  Data const data;   // the const Data in this node.

  /** Return the successor of this BSTNode in a BST, or 0 if none.
   ** PRECONDITION: this BSTNode is a node in a BST.
   ** POSTCONDITION:  the BST is unchanged.
   ** RETURNS: the BSTNode that is the successor of this BSTNode,
   ** or 0 if there is none.
   */
  BSTNode<Data>* successor() {

    if ( !this->right ) { // Check if there is a greater node
      BSTNode<Data> *temp = this; // The current trailing pointer
      BSTNode<Data> *tempParent = this->parent; // Parent node pointer
      while (tempParent && temp == tempParent->right ) {
        temp = tempParent;
        tempParent = tempParent->parent;
      }
      return tempParent;
    }

    BSTNode<Data> *temp = this->right; // Make a temp for right sub tree

    while ( temp->left ) { // Loop until there is no lesser node
      temp = temp->left; // Move the temp pointer to left
    }

    return temp; // Return the resulting node
  }

}; 

/** Overload operator<< to print a BSTNode's fields to an ostream. */
template <typename Data>
std::ostream & operator<<(std::ostream& stm, const BSTNode<Data> & n) {
  stm << '[';
  stm << std::setw(10) << &n;                 // address of the BSTNode
  stm << "; p:" << std::setw(10) << n.parent; // address of its parent
  stm << "; l:" << std::setw(10) << n.left;   // address of its left child
  stm << "; r:" << std::setw(10) << n.right;  // address of its right child
  stm << "; d:" << n.data;                    // its data field
  stm << ']';
  return stm;
}

#endif // BSTNODE_HPP
