/**
 * Name: Huize Shi
 * PID: A92122910
 * Date Jan. 19, 2016
 */

package hw2;

import java.nio.channels.IllegalSelectorException;
import java.util.*;

public class DoublyLinkedList12<E> extends AbstractList<E> {

    private int  nelems;
    private Node head;
    private Node tail;

    protected class Node {
        E    data;
        Node next;
        Node prev;

        /** Constructor to create singleton Node */
        public Node(E element) {
            this.setElement(element);
        }

        /**
         * Constructor to create singleton link it between previous and next
         * 
         * @param element
         *            Element to add, can be null
         * @param prevNode
         *            predecessor Node, can be null
         * @param nextNode
         *            successor Node, can be null
         */
        public Node(E element, Node prevNode, Node nextNode) {
            this.setElement(element);
            this.setPrev(prevNode);
            this.setNext(nextNode);
        }

        /** Remove this node from the list. Update previous and next nodes */
        public void remove() {
            this.next.setPrev(this.prev);
            this.prev.setNext(this.next);
        }

        /**
         * Set the previous node in the list
         * 
         * @param p
         *            new previous node
         */
        public void setPrev(Node p) {
            this.prev = p;
        }

        /**
         * Set the next node in the list
         * 
         * @param n
         *            new next node
         */
        public void setNext(Node n) {
            this.next = n;
        }

        /**
         * Set the element
         * 
         * @param e
         *            new element
         */
        public void setElement(E e) {
            this.data = e;
        }

        /** Accessor to get the next Node in the list */
        public Node getNext() {
            return this.next;
        }

        /** Accessor to get the prev Node in the list */
        public Node getPrev() {
            return this.prev;
        }

        /** Accessor to get the Nodes Element */
        public E getElement() {
            return data;
        }
    }

    /** ListIterator implementation */
    protected class MyListIterator implements ListIterator<E> {

        private boolean forward;
        private boolean canRemove;
        private Node    left, right; // Cursor sits between these two nodes
        private int     idx;         // Tracks current position. what next()
                                     // would
        // return

        public MyListIterator() {
            left = null;
            right = head;
            canRemove = false;
            forward = true;
            idx = 0;
        }

        @Override
        public void add(E e) throws NullPointerException {
            if (e == null) {
                throw new NullPointerException();
            }

            Node newNode = new Node(e);
            if (idx == 0) {
                left = newNode;
                left.setNext(right);
                right.setPrev(left);
                head = left;
                left = null;
                right = right.getPrev();
                return;
            }
            if (idx == nelems - 1) {
                left.setNext(newNode);
                newNode.setPrev(left);
                newNode.setNext(right);
                right.setPrev(newNode);
                right = right.getPrev();
                return;
            }
            if (idx == nelems) {
                right = newNode;
                right.setPrev(left);
                left.setNext(right);
                return;
            }
            right = right.getNext();
            newNode.setPrev(left);
            left.setNext(newNode);
            newNode.setNext(right);
            right.setPrev(newNode);
        }

        @Override
        public boolean hasNext() {
            return right != null;
        }

        @Override
        public boolean hasPrevious() {
            return left != null;
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            idx++;
            forward = true;
            canRemove = true;
            this.left = right;
            this.right = this.right.getNext();
            return this.left.getElement();
        }

        @Override
        public int nextIndex() {
            return idx + 1;
        }

        @Override
        public E previous() throws NoSuchElementException {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            canRemove = true;
            this.right = left;
            this.left = this.left.getPrev();
            idx--;
            forward = false;
            return this.right.getElement();
        }

        @Override
        public int previousIndex() {
            return idx - 1;
        }

        @Override
        public void remove() throws IllegalStateException {
            if (!canRemove) {
                throw new IllegalStateException();
            }
            if (forward) {
                if (left.getPrev() == null) {
                    head = left.getNext();
                    left = null;
                    head.setPrev(null);
                    return;
                }
                left = left.getPrev();
                right.setPrev(left);
                left.setNext(right);
                return;
            }
            right = right.getNext();
            left.setNext(right);
            right.setPrev(left);
            return;
        }

        @Override
        public void set(E e) throws NullPointerException, IllegalStateException {
            if (e == null) {
                throw new NullPointerException();
            }
            if (!canRemove) {
                throw new IllegalSelectorException();
            }
            Node newNode = new Node(e);

            if (forward) {
                if (left.getPrev() == null) {
                    left = newNode;
                    left.setNext(right);
                    right.setPrev(left);
                    head = left;
                    return;
                }
                left = left.getPrev();
                right.setPrev(newNode);
                left.setNext(newNode);
                newNode.setPrev(left);
                newNode.setNext(right);
                left = left.getNext();
                return;
            }
            right = right.getNext();
            right.setPrev(newNode);
            newNode.setNext(right);
            newNode.setPrev(left);
            left.setNext(newNode);
            right = newNode.getNext();
            return;
        }
    }

    // Implementation of the DoublyLinkedList12 Class

    /** Only 0-argument constructor is define */
    public DoublyLinkedList12() {
        nelems = 0;
    }

    @Override
    public int size() {
        return nelems;
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index >= nelems) {
            throw new IndexOutOfBoundsException();
        }
        Node curNode = this.getNth(index);
        return curNode.getElement();
    }

    @Override
    /**
     * Add an element to the list
     * 
     * @param index
     *            where in the list to add
     * @param data
     *            data to add
     * @throws IndexOutOfBoundsException
     * @throws NullPointerException
     */
    public void add(int index, E data) throws IndexOutOfBoundsException, NullPointerException {
        if (index > nelems) {
            throw new IndexOutOfBoundsException();
        }
        if (data == null) {
            throw new NullPointerException();
        }
        if (nelems == 0) {
            head = new Node(data);
            tail = head;
            nelems++;
            return;
        }
        if (index == 0) {
            Node newNode = new Node(data);
            newNode.setNext(head);
            head.setPrev(newNode);
            head = newNode;
            nelems++;
            return;
        }
        if (index == nelems) {
            this.add(data);
            return;
        }
        Node curNode = getNth(index);
        Node newNode = new Node(data);
        curNode.getPrev().setNext(newNode);
        newNode.setPrev(curNode.getPrev());
        curNode.setPrev(newNode);
        newNode.setNext(curNode);
        nelems++;
    }

    /**
     * Add an element to the end of the list
     * 
     * @param data
     *            data to add
     * @throws NullPointerException
     */
    public boolean add(E data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        }
        Node newNode = new Node(data);
        if (head == null) {
            head = new Node(data);
            tail = head;
            nelems++;
            return true;
        }
        Node curNode = this.getNth(nelems - 1);
        curNode.setNext(newNode);
        newNode.setPrev(curNode);
        tail = newNode;
        nelems++;
        return true;
    }

    /**
     * Set the element at an index in the list
     * 
     * @param index
     *            where in the list to add
     * @param data
     *            data to add
     * @return element that was previously at this index.
     * @throws IndexOutOfBoundsException
     * @throws NullPointerException
     */
    public E set(int index, E data) throws IndexOutOfBoundsException, NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        }
        if (index > nelems) {
            throw new IndexOutOfBoundsException();
        }
        Node oldNode;
        Node curNode = new Node(data);
        if (index == nelems) {
            this.add(data);
            return null;
        }
        if (index == nelems - 1) {
            oldNode = this.getNth(index);
            oldNode.getPrev().setNext(curNode);
            curNode.setPrev(oldNode.getPrev());
            tail = curNode;
            return oldNode.getElement();
        }
        if (index == 0) {
            oldNode = head;
            curNode.setNext(head.getNext());
            head.getNext().setPrev(curNode);
            head = curNode;
            tail = head;
            return oldNode.getElement();
        }
        oldNode = this.getNth(index);
        this.add(index, data);
        this.remove(index + 1);
        return oldNode.getElement();
    }

    /**
     * Remove the element at an index in the list
     * 
     * @param index
     *            where in the list to add
     * @return element the data found
     * @throws IndexOutOfBoundsException
     */
    public E remove(int index) throws IndexOutOfBoundsException {
        Node oldNode;
        Node curNode;
        if (index >= nelems) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            oldNode = head;
            head = head.getNext();
            head.prev = null;
            return oldNode.getElement();
        }
        if (index == nelems - 1) {
            curNode = this.getNth(index);
            oldNode = curNode;
            curNode.getPrev().setNext(null);
            return oldNode.getElement();
        }
        curNode = this.getNth(index);
        oldNode = curNode;
        curNode.getNext().setPrev(curNode.getPrev());
        curNode.getPrev().setNext(curNode.getNext());
        nelems--;
        return oldNode.getElement();
    }

    /** Clear the linked list */
    public void clear() {
        head = null;
        nelems = 0;
    }

    /**
     * Determine if the list empty
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return nelems == 0;
    }

    /**
     * Get the head of this linked list
     * 
     * @return The head of this linked list
     */
    public E getHead() {
        return head.getElement();
    }

    // Helper method to get the Node at the Nth index
    public Node getNth(int index) {
        Node curNode = head;
        for (int i = 0; i < index; i++) {
            curNode = curNode.getNext();
        }
        return curNode;
    }

    /**
     * Return a list iterator
     * 
     * @return A new MyListIterator
     */
    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    /**
     * Return a list iterator
     * 
     * @return A new MyListIterator
     */
    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }
    
    /**
     * Append the reversed version of this list on the end
     */
    public void reverseAndConcat( DoublyLinkedList12<E> list ) {
        int size = list.size();
        for ( int i = 0; i < size; i++ ) {
            list.add(list.get(size-1-i));
        }
    }
}