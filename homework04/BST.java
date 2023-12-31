import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.contains(null)) {
            throw new IllegalArgumentException("You cannot add null data to a BST");
        } else {
            for (T dat : data) {
                add(dat);
            }
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot add null data to a BST");
        } else {
            if (root == null)  {
                root = new BSTNode<>(data);
                size++;
            } else {
                addHelper(data, root);
            }
        }
    }

    /**
     * Helper method to add data to the BST
     *
     * @param data the data to add
     * @param curr the current node being added to
     */
    private void addHelper(T data, BSTNode<T> curr) {
        if (data.compareTo(curr.getData()) < 0) {
            if (curr.getLeft() != null) {
                addHelper(data, curr.getLeft());
            } else {
                curr.setLeft(new BSTNode<>(data));
                size++;
            }
        } else if (data.compareTo(curr.getData()) > 0) {
            if (curr.getRight() != null) {
                addHelper(data, curr.getRight());
            } else {
                curr.setRight(new BSTNode<>(data));
                size++;
            }
        }
    }
    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot remove null data from a BST");
        } else {
            BSTNode<T> dummy = new BSTNode<>(null);
            root = removeHelper(root, data, dummy);
            return dummy.getData();
        }
    }

    /**
     * Helper method to remove data from BST
     *
     * @param curr current node to traverse to removed node
     * @param data data that will be removed from the tree
     * @param dummy the node to be removed
     * @return the parent of the node that is being removed (dummy)
     */
    private BSTNode<T> removeHelper(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data to remove was not found in the BST");
        } else {
            if (data.compareTo(curr.getData()) < 0) {
                curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
            } else if (data.compareTo(curr.getData()) > 0) {
                curr.setRight(removeHelper(curr.getRight(), data, dummy));
            } else {
                dummy.setData(curr.getData());
                size--;
                if (curr.getLeft() == null && curr.getRight() == null) {
                    return null;
                } else if (curr.getLeft() != null && curr.getRight() == null) {
                    return curr.getLeft();
                } else if (curr.getRight() != null && curr.getLeft() == null) {
                    return curr.getRight();
                } else {
                    BSTNode<T> dummy2 = new BSTNode<>(null);
                    curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                    curr.setData(dummy2.getData());
                }
            }
        }
        return curr;
    }

    /**
     * Helper method to remove a node and replacing with a successor node
     *
     * @param curr the current node being iterated through to find the successor
     * @param dummy the child of the node being removed
     * @return the successor node of the removed node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
            return curr;
        }
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data does not exist in the BST");
        } else {
            return getHelper(root, data);
        }
    }

    /**
     * Helper method for get()
     * @param curr current node traversing to find node containing data
     * @param data data user is looking for
     * @return data in the node where the data was found
     */
    private T getHelper(BSTNode<T> curr, T data) {
        if (curr == null) {
            throw new NoSuchElementException("Data does note exist in the tree");
        } else {
            if (data.compareTo(curr.getData()) < 0) {
                return getHelper(curr.getLeft(), data);
            } else if (data.compareTo(curr.getData()) > 0) {
                return getHelper(curr.getRight(), data);
            } else {
                return curr.getData();
            }
        }
    }

    /**
     * Returns whether data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        try {
            get(data);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> prelist = new ArrayList<>();
        preorderHelper(root, prelist);
        return prelist;
    }

    /**
     * Helper method for preorder()
     *
     * @param curr current node being traversed through
     * @param list list that data is being added to
     */
    private void preorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            list.add(curr.getData());
            preorderHelper(curr.getLeft(), list);
            preorderHelper(curr.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> inlist = new ArrayList<>();
        inorderHelper(root, inlist);
        return inlist;
    }
    /**
     * Helper method for inorder()
     * @param curr current node being traversed through
     * @param list list that data is being added to
     */
    private void inorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            inorderHelper(curr.getLeft(), list);
            list.add(curr.getData());
            inorderHelper(curr.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> inlist = new ArrayList<>();
        postorderHelper(root, inlist);
        return inlist;
    }
    /**
     * Helper method for postorder()
     *
     * @param curr current node being traversed through
     * @param list list that data is being added to
     */
    private void postorderHelper(BSTNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            postorderHelper(curr.getLeft(), list);
            postorderHelper(curr.getRight(), list);
            list.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        List<T> list = new ArrayList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.poll();
            if (curr != null) {
                list.add(curr.getData());
                q.add(curr.getLeft());
                q.add(curr.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return heightHelper(root);
    }

    /**
     * Helper method for height()
     *
     * @param curr  node that traverses to find left-most and right-most node
     * @return height of the root of the tree
     */
    private int heightHelper(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            return (Math.max(heightHelper(curr.getLeft()), heightHelper(curr.getRight())) + 1);
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *         20
     *       /    \
     *     10      25
     *           /    \
     *         23      37
     *        /  \    /   \
     *      22   24  35    40
     *                 \
     *                  36
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k), with n being the number of data in the BST
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k < 0 or k > size
     */
    public List<T> kLargest(int k) {
        if (k < 0 || k > size) {
            throw new IllegalArgumentException("There is no" + k + "th largest numbers in this BST");
        }
        List<T> list = new ArrayList<>();
        kLargestHelper(root, list, k);
        return list;
    }

    /**
     * Helper method for kLargest()
     *
     * @param curr current node to traverse to the largest entries
     * @param list where the largest entries are being added to
     * @param k kth largest entries
     */
    private void kLargestHelper(BSTNode<T> curr, List<T> list, int k) {
        if (curr == null) {
            return;
        } else {
            if (list.size() < k) {
                kLargestHelper(curr.getRight(), list, k);
                if (list.size() < k) {
                    list.add(0, curr.getData());
                    kLargestHelper(curr.getLeft(), list, k);
                }
            }
        }
    }



    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
