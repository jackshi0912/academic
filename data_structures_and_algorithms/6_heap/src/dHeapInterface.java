package hw6;

public interface dHeapInterface<T> {
    /**
     * Adds the specified element to the heap; o cannot be null. Resizes the
     * storage if full
     * 
     * @param o
     *            The element to be added
     */
    public void add(T o);

    /**
     * Return the number of elements stored in the heap
     * 
     * @return The number of elements stored in the heap
     */
    public int size();

    /**
     * Remove and return the smallest element stored in the heap. If the heap is
     * empty, then the method throws a NoSuchElementException.
     * 
     * @return
     */
    public T removeSmallest();
}
