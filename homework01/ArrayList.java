/**
 * Your implementation of an ArrayList.
 *
 * @author FELIPE OLIVEIRA
 * @version 1.0
 * @userid foliveira8
 * @GTID 903682967
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[ArrayList.INITIAL_CAPACITY];
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        T[] newArr;
        if (index < 0) {
            throw new IndexOutOfBoundsException("You cannot add data at a negative index");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("You cannot add data at an index bigger than the Arraylist");
        } else if (data == null) {
            throw new IllegalArgumentException("You cannot add a null data structure to an Arraylist");
        } else if (size == backingArray.length) {
            newArr = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                if (i < index) {
                    newArr[i] = backingArray[i];
                } else {
                    newArr[i + 1] = backingArray[i];
                }
            }
            newArr[index] = data;
            backingArray = newArr;
        } else if (index == size) {
            backingArray[size] = data;
        } else {
            for (int i = size; i > index; i--) {
                backingArray[i] = backingArray[i - 1];
            }
            backingArray[index] = data;
        }
        size++;
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        T[] newArr;
        if (data == null) {
            throw new IllegalArgumentException("You cannot add a null data structure to an Arraylist");
        } else if (size == backingArray.length) {
            newArr = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newArr[i + 1] = backingArray[i];
            }
            newArr[0] = data;
            backingArray = newArr;
        } else {
            for (int i = 0; i < size; i++) {
                backingArray[i + 1] = backingArray[i];
            }
            backingArray[0] = data;
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        T[] newArr;
        if (data == null) {
            throw new IllegalArgumentException("You cannot add a null data structure to an Arraylist");
        } else if (size == backingArray.length) {
            newArr = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                newArr[i] = backingArray[i];
            }
            newArr[size] = data;
            backingArray = newArr;
        } else {
            backingArray[size] = data;
        }
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T[] newArr;
        T temp;
        if (index < 0) {
            throw new IndexOutOfBoundsException("You cannot add data at a negative index");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("You cannot add data at an index bigger than the Arraylist");
        } else if (index == size - 1) {
            temp = backingArray[index];
            backingArray[size - 1] = null;
            size--;
            return temp;
        } else {
            temp = backingArray[index];
            backingArray[index] = null;
            for (int i = index; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }
            backingArray[size - 1] = null;
        }
        size--;
        return temp;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        T[] newArr;
        T temp;
        if (size == 0) {
            throw new java.util.NoSuchElementException("You cannot remove an item from an empty list");
        } else {
            temp = backingArray[0];
            newArr = (T[]) new Object[backingArray.length];
            for (int i = 0; i < size - 1; i++) {
                newArr[i] = backingArray[i + 1];
            }
            backingArray = newArr;
        }
        size--;
        return temp;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        T temp;
        if (size == 0) {
            throw new java.util.NoSuchElementException("You cannot remove an item from an empty list");
        } else {
            temp = backingArray[size - 1];
            backingArray[size - 1] = null;
        }
        size--;
        return temp;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("You cannot retrieve data at a negative index");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("You cannot retrieve data at an index bigger than the Arraylist");
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[ArrayList.INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
