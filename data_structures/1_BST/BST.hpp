/*
 * Filename: BST.hpp
 * Author: Huize Shi
 * Userid: cs100vdo
 * Description: This function creates a binary search tree.
 * Date: August 5, 2016
 */
#ifndef BST_HPP
#define BST_HPP
#include "BSTNode.hpp"
#include "BSTIterator.hpp"
#include <iostream>

template<typename Data>
class BST {

  protected:

    /** Pointer to the root of this BST, or 0 if the BST is empty */
    BSTNode<Data>* root;

    /** Number of Data items stored in this BST. */
    unsigned int isize;

    /** Height of this BST. */
    unsigned int iheight;

  public:

    /** define iterator as an aliased typename for BSTIterator<Data>. */
    typedef BSTIterator<Data> iterator;

    /** Default constructor.
      Initialize an empty BST.
      */
    BST() : root(0), isize(0), iheight(0) {  }

    /** Default destructor.
      Delete every node in this BST.
      */
    virtual ~BST() {
      deleteAll(root);
      root = nullptr;
      isize = 0;
      return;
    }

    /** Given a reference to a Data item, insert a copy of it in this BST.
     *  Return  true if the item was added to this BST
     *  as a result of this call to insert,
     *  false if an item equal to this one was already in this BST.
     *  Note: This function should use only the '<' operator when comparing
     *  Data items. (should not use ==, >, <=, >=)  For the reasoning
     *  behind this, see the assignment writeup.
     */
    virtual bool insert(const Data& item) {
      // For helpful pseudocode, refer to Paul Kube's
      // 3rd Lecture, 7th slide.

      BSTNode<Data> *newNode = new BSTNode<Data>(item);

      if ( !root ) { // Check if the root is null
        root = newNode;
        ++isize;
        return true;
      }

      BSTNode<Data> *curNode = root;

      while ( true ) {
        if ( newNode->data < curNode->data ) { // If item goes left
          if ( !curNode->left ) { // If the spot is avaliable
            curNode->left = newNode;
            newNode->parent = curNode;
            ++isize;
            return true;
          }
          curNode = curNode->left; // Traverse left
        } else if ( curNode->data < newNode->data ) { // If item goes right
          if ( !curNode->right ) { // If the spot is avaliable
            curNode->right = newNode;
            newNode->parent = curNode;
            ++isize;
            return true;
          }
          curNode = curNode->right; // Traverse left
        } else { // If the item exists
          return false;
        }
      }
    }


    /** Find a Data item in the BST.
     *  Return an iterator pointing to the item, or pointing past
     *  the last node in the BST if not found.
     *  Note: This function should use only the '<' operator when comparing
     *  Data items. (should not use ==, >, <=, >=).  For the reasoning
     *  behind this, see the assignment writeup.
     */
    iterator find(const Data& item) const {
      // For helpful pseudocode, refer to Paul Kube's
      // 3rd Lecture, 6th Slide.

      BSTNode<Data> *curNode = root;

      while ( true ) {
        if ( item < curNode->data ) { // If item goes left
          if ( !curNode->left ) // Not found
            return this->end();
          curNode = curNode->left; // Traverse left
        } else if ( curNode->data < item ) { // If item goes right
          if ( !curNode->right ) // Not found
            return this->end();
          curNode = curNode->right; // Traverse right
        } else { // If the item exists
          return typename BST<Data>::iterator(curNode);
        }
      }
    }


    /** Return the number of items currently in the BST.
    */
    unsigned int size() const {
      return isize;
    }

    /** Return the height of the BST.
    */
    unsigned int height() const {
      return recursiveHeight(root)-1;
    }

    /** Helper method to calculate the height of the BST recursively */
    unsigned int recursiveHeight( BSTNode<Data> *curNode ) const {

      if ( !curNode ) { // Base case: check if the tree is empty
        return 0;
      }

      // Calculate the height of each sub-tree
      int lHight = recursiveHeight( curNode->left );
      int rHight = recursiveHeight( curNode->right );

      // Compare the two values and choose the bigger one and add 1
      return (lHight > rHight)?(lHight+1):(rHight+1);

    }


    /** Return true if the BST is empty, else false.
    */
    bool empty() const {
      return (!isize)?true:false;
    }

    /** Return an iterator pointing to the first item in the BST (not the root).
    */
    iterator begin() const {
      BSTNode<Data> *curNode = root;
      while ( curNode->left ) { // Going left until there is no lesser Node
        curNode = curNode->left;
      }
      return typename BST<Data>::iterator(curNode);
    }

    /** Return an iterator pointing past the last item in the BST.
    */
    iterator end() const {
      return typename BST<Data>::iterator(0);
    }

    /** Perform an inorder traversal of this BST.
    */
    void inorder() const {
      inorder(root);
    }


  private:

    /** Recursive inorder traversal 'helper' function */

    /** Inorder traverse BST, print out the data of each node in ascending 
     * order. Implementing inorder and deleteAll base on the pseudo code is 
     * an easy way to get started.
      */
    void inorder(BSTNode<Data>* n) const {
      if (!n) { // Base case
        return;
      }
      inorder( n->left ); // Traverse left
      std::cout << *n << std::endl; // Print value
      inorder( n->right ); // Traverse Right
    }

    /** Find the first element of the BST
    */
    static BSTNode<Data>* first(BSTNode<Data>* root) {
      if(root == 0) // Base case
        return 0;
      while(root->left != 0) // Going left until there is no lesser Node
        root = root->left;
      return root;
    }

    /** postorder traversal, deleting nodes
    */
    static void deleteAll(BSTNode<Data>* n) {
      /* Pseudo Code:
         if current node is null: return;
         recursively delete left sub-tree
         recursively delete right sub-tree
         delete current node
         */

      if(n == nullptr) return;
      deleteAll(n->left);
      deleteAll(n->right);
      delete n;
      n = 0;

    }

};


#endif //BST_HPP
