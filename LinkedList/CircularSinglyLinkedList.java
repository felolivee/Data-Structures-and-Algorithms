import java.util.NoSuchElementException;

/**
 * Your implementation of a CircularSinglyLinkedList without a tail pointer.
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
public class CircularSinglyLinkedList<T> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private CircularSinglyLinkedListNode<T> head;
    private int size;

    /*
     * Do not add a constructor.
     */

    /**
     * Adds the data to the specified index.
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new data
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        CircularSinglyLinkedListNode newN;
        CircularSinglyLinkedListNode temp;
        if (index < 0) {
            throw new IndexOutOfBoundsException("You cannot add data to a negative index");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("You cannot add data to an index greater than the size of"
                    + " the Circular Linked List");
        } else if (data == null) {
            throw new IllegalArgumentException("You cannot add null data to the Circular Linked List");
        } else if (size == 0) {
            newN = new CircularSinglyLinkedListNode(data);
            head = newN;
            head.setNext(head);
            size++;
        } else if (index == 0) {
            newN = new CircularSinglyLinkedListNode(head.getData(), head.getNext());
            head.setData(data);
            head.setNext(newN);
            size++;
        } else if (index == size) {
            newN = new CircularSinglyLinkedListNode(head.getData(), head.getNext());
            head.setData(data);
            head.setNext(newN);
            head = newN;
            size++;
        } else {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            newN = new CircularSinglyLinkedListNode(temp.getData());
            newN.setNext(temp.getNext());
            temp.setData(data);
            temp.setNext((newN));
            size++;
        }
    }

    /**
     * Adds the data to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        CircularSinglyLinkedListNode newN;
        if (data == null) {
            throw new IllegalArgumentException("You cannot add null data to the Circular Linked List");
        } else if (size == 0) {
            newN = new CircularSinglyLinkedListNode(data);
            head = newN;
            head.setNext(head);
            size++;
        } else {
            newN = new CircularSinglyLinkedListNode(head.getData(), head.getNext());
            head.setData(data);
            head.setNext(newN);
            size++;
        }
    }

    /**
     * Adds the data to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        CircularSinglyLinkedListNode newN;
        if (data == null) {
            throw new IllegalArgumentException("You cannot add null data to the Circular Linked List");
        } else if (size == 0) {
            newN = new CircularSinglyLinkedListNode(data);
            head = newN;
            head.setNext(head);
            size++;
        } else {
            newN = new CircularSinglyLinkedListNode(head.getData(), head.getNext());
            head.setData(data);
            head.setNext(newN);
            head = newN;
            size++;
        }
    }

    /**
     * Removes and returns the data at the specified index.
     *
     * Must be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T ret;
        CircularSinglyLinkedListNode temp;
        if (index < 0) {
            throw new IndexOutOfBoundsException("You cannot add data to a negative index");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("You cannot add data to an index greater"
                    + " than the size of the Circular Linked List");
        } else if (size == 1) {
            ret = head.getData();
            head = null;
            size--;
            return ret;
        } else if (index == 0) {
            ret = head.getData();
            head.setData((head.getNext().getData()));
            head.setNext(head.getNext().getNext());
            size--;
            return ret;
        } else {
            temp = head;
            for (int i = 0; i < index - 1; i++) {
                temp = temp.getNext();
            }
            ret = (T) temp.getNext().getData();
            temp.setNext(temp.getNext().getNext());
            size--;
            return ret;
        }
    }

    /**
     * Removes and returns the first data of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        T ret;
        if (size == 0) {
            throw new NoSuchElementException("You cannot remove something from an empty list");
        } else if (size == 1) {
            ret = head.getData();
            head = null;
            size--;
            return ret;
        } else {
            ret = head.getData();
            head.setData((head.getNext().getData()));
            head.setNext(head.getNext().getNext());
            size--;
            return ret;
        }
    }

    /**
     * Removes and returns the last data of the list.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        T ret;
        CircularSinglyLinkedListNode temp;
        if (size == 0) {
            throw new NoSuchElementException("You cannot remove something from an empty list");
        } else if (size == 1) {
            ret = head.getData();
            head = null;
            size--;
            return ret;
        } else if (size == 2) {
            ret = head.getNext().getData();
            head.setNext(head);
            size--;
            return ret;
        } else {
            temp = head;
            for (int i = 0; i < size - 2; i++) {
                temp = temp.getNext();
            }
            ret = (T) temp.getNext().getData();
            temp.setNext(temp.getNext().getNext());
            size--;
            return ret;
        }
    }

    /**
     * Returns the data at the specified index.
     *
     * Should be O(1) for index 0 and O(n) for all other cases.
     *
     * @param index the index of the data to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        CircularSinglyLinkedListNode temp;
        if (index < 0) {
            throw new IndexOutOfBoundsException("You cannot index a negative number");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("You cannot index a number rgeater than the size");
        } else if (index == 0) {
            return head.getData();
        } else {
            temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            return (T) temp.getData();
        }
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
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("No null data exists in the Circular Linked List");
        }
        CircularSinglyLinkedListNode temp = head;
        CircularSinglyLinkedListNode prev = null;
        for (int i = 1; i < size; i++) {
            if (data.equals(temp.getNext().getData())) {
                prev = temp;
            }
            temp = temp.getNext();
        }
        if (prev == null && head != null && data.equals(head.getData())) {
            prev = temp;
        }
        if (prev != null) {
            T ret = (T) prev.getNext().getData();
            if (size == 1) {
                head = null;
            } else {
                if (head.equals(prev.getNext())) {
                    head = prev.getNext().getNext();
                }
                prev.setNext(prev.getNext().getNext());
            }
            size--;
            return ret;
        } else {
            throw new NoSuchElementException("Data does not exist in Circular Linked List");
        }
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all of the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        CircularSinglyLinkedListNode temp = head;
        for (int i = 0; i < size; i++) {
            arr[i] = (T) temp.getData();
            temp = temp.getNext();
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public CircularSinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
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
        // DO NOT MODIFY!
        return size;
    }
}
