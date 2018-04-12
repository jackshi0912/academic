/** Filename: BSTIterator.hpp
 * Author: Huize Shi
 * Userid: cs100vbo
 * Description: This class defines a Iterator for the BST
 * Date: August 5, 2016
 */

#ifndef BSTITERATOR_HPP
#define BSTITERATOR_HPP
#include "BSTNode.hpp"
#include <list>
#include <iterator>

/*
 * For more on iterators, refer to Paul Kube's
 * 3rd Lecture, starting on Slide 14.
 */

template<typename Data>
class BSTIterator : public std::iterator<std::input_iterator_tag,Data> {

  private:

    BSTNode<Data>* curr;

  public:

    /** Constructor.  Use the argument to initialize the current BSTNode
     *  in this BSTIterator.
     */
    BSTIterator(BSTNode<Data>* curr) : curr(curr) {
    }

    /** Dereference operator. */
    Data operator*() const {
      return curr->data; // Release data 
    }

    /** Pre-increment operator. */
    BSTIterator<Data>& operator++() {
      curr = curr->successor();
      return *this;
    }

    /** Post-increment operator. */
    BSTIterator<Data> operator++(int) {
      BSTIterator before = BSTIterator(curr);
      ++(*this);
      return before;
    }

    /** Equality test operator. */
    bool operator==(BSTIterator<Data> const & other) const {
      return (this->curr == other.curr);
    }

    /** Inequality test operator. */
    bool operator!=(BSTIterator<Data> const & other) const {
      return !(this->curr == other.curr);
    }

};

#endif //BSTITERATOR_HPP
