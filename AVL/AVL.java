import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
 *
 * @author Felipe Oliveira
 * @version 1.0
 * @userid foliveira8
 * @GTID 903682967
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot add null data to a BST");
        } else {
            for (T dat : data) {
                add(dat);
            }
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot add null data to a BST");
        } else {
            root = addHelper(data, root);
        }
    }
    /**
     * Helper method to add data to the AVL
     *
     * @param data the data to add
     * @param curr the current node being added to
     * @return AVL pointer node
     */
    private AVLNode<T> addHelper(T data, AVLNode<T> curr) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(addHelper(data, curr.getLeft()));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(addHelper(data, curr.getRight()));
        }
        heightHelper(curr);
        balanceFactor(curr);
        curr = balance(curr);
        return curr;
    }

    /**
     * helper method that determines the height of a node
     * @param curr current node
     */
    private void heightHelper(AVLNode<T> curr) {
        int leftH;
        int rightH;
        if (curr.getLeft() == null) {
            leftH = -1;
        } else {
            leftH = curr.getLeft().getHeight();
        }
        if (curr.getRight() == null) {
            rightH = -1;
        } else {
            rightH = curr.getRight().getHeight();
        }
        curr.setHeight(Math.max(leftH, rightH) + 1);
    }

    /**
     * finds the balance factor of a node
     * @param curr current node
     * @return balance factor (int)
     */
    private int balanceFactor(AVLNode<T> curr) {
        int leftH;
        int rightH;
        if (curr.getLeft() == null) {
            leftH = -1;
        } else {
            leftH = curr.getLeft().getHeight();
        }
        if (curr.getRight() == null) {
            rightH = -1;
        } else {
            rightH = curr.getRight().getHeight();
        }
        curr.setBalanceFactor(leftH - rightH);
        return curr.getBalanceFactor();
    }


    /**
     * Balances the node
     * @param curr unbalanced node
     * @return AVL node
     */
    private AVLNode<T> balance(AVLNode<T> curr) {

        if (curr.getBalanceFactor() > 1) {
            if (curr.getLeft().getBalanceFactor() >= 0) {
                return rightRotation(curr);
            } else {
                return leftRightRotation(curr);
            }
        } else if (curr.getBalanceFactor() < -1) {
            if (curr.getRight().getBalanceFactor() <= 0) {
                return leftRotation(curr);
            } else {
                return rightLeftRotation(curr);
            }
        }
        return curr;
    }

    /**
     * performs a left-right rotation
     * @param curr unbalanced node
     * @return AVL node
     */
    private AVLNode<T> leftRightRotation(AVLNode<T> curr) {
        AVLNode<T> child = curr.getLeft();
        curr.setLeft(leftRotation(child));
        curr = rightRotation(curr);
        return curr;
    }
    /**
     * performs a right-left rotation
     * @param curr unbalanced node
     * @return AVL node
     */
    private AVLNode<T> rightLeftRotation(AVLNode<T> curr) {
        AVLNode<T> child = curr.getRight();
        curr.setRight(rightRotation(child));
        curr = leftRotation(curr);
        return curr;
    }
    /**
     * performs a right rotation
     * @param curr unbalanced node
     * @return AVL node
     */
    private AVLNode<T> rightRotation(AVLNode<T> curr) {
        AVLNode<T> child = curr.getLeft();
        curr.setLeft(child.getRight());
        child.setRight(curr);
        heightHelper(curr);
        balanceFactor(curr);
        heightHelper(child);
        balanceFactor(child);
        return child;
    }
    /**
     * performs a left rotation
     * @param curr unbalanced node
     * @return AVL node
     */
    private AVLNode<T> leftRotation(AVLNode<T> curr) {
        AVLNode<T> child = curr.getRight();
        curr.setRight(child.getLeft());
        child.setLeft(curr);
        heightHelper(curr);
        balanceFactor(curr);
        heightHelper(child);
        balanceFactor(child);
        return child;

    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("You cannot remove null data from a BST");
        } else {
            AVLNode<T> dummy = new AVLNode<>(null);
            root = removeHelper(root, data, dummy);
            return dummy.getData();
        }
    }

    /**
     * Helper method to remove data from BST
     *
     * @param curr current node to traverse to removed node
     * @param data data that will be removed from the tree
     * @param dummy the node carrying removed data
     * @return the parent of the node that is being removed (dummy)
     */
    private AVLNode<T> removeHelper(AVLNode<T> curr, T data, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("Data to remove was not found in the BST");
        } else {
            if (data.compareTo(curr.getData()) < 0) {
                curr.setLeft(removeHelper(curr.getLeft(), data, dummy));
                heightHelper(curr);
                balanceFactor(curr);
                curr = balance(curr);
            } else if (data.compareTo(curr.getData()) > 0) {
                curr.setRight(removeHelper(curr.getRight(), data, dummy));
                heightHelper(curr);
                balanceFactor(curr);
                curr = balance(curr);
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
                    AVLNode<T> dummy2 = new AVLNode<>(null);
                    curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                    curr.setData(dummy2.getData());
                    heightHelper(curr);
                    balanceFactor(curr);
                    curr = balance(curr);
                    return curr;
                }
            }
        }

        return curr;
    }
    /**
     * Helper method to remove a node and replacing with a predecessor node
     * @param curr the current node being iterated through to find the successor
     * @param dummy the child of the node being removed
     * @return the successor node of the removed node
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getRight() == null) {
            dummy.setData(curr.getData());
            return curr.getLeft();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), dummy));
            heightHelper(curr);
            balanceFactor(curr);
            curr = balance(curr);
            return curr;
        }
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
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
    private T getHelper(AVLNode<T> curr, T data) {
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
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Null data does not exist in the BST");
        }
        try {
            get(data);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size > 0) {
            return root.getHeight();
        } else {
            return -1;
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * This must be done recursively.
     *
     * Your list should not have duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> list = new ArrayList<>();
        deepestBranchesHelper(root, list);
        return list;
    }

    /**
     * helper method of deepestBranches function
     * @param curr current node
     * @param list list that data is being added upon
     */
    private void deepestBranchesHelper(AVLNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            if (curr.getBalanceFactor() > 0) {
                list.add(curr.getData());
                deepestBranchesHelper(curr.getLeft(), list);
            } else if (curr.getBalanceFactor() < 0) {
                list.add(curr.getData());
                deepestBranchesHelper(curr.getRight(), list);
            } else if (curr.getBalanceFactor() == 0) {
                list.add(curr.getData());
                deepestBranchesHelper(curr.getLeft(), list);
                deepestBranchesHelper(curr.getRight(), list);
            }
        }
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * This must be done recursively.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * @return a sorted list of data that is > data1 and < data2
     * @throws IllegalArgumentException if data1 or data2 are null
     * or if data1 > data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null || data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("Cannot find elements between these invalid thresholds");
        } else {
            List<T> list = new ArrayList<>();
            sortedInBetweenHelper(data1, data2, root, list);
            return list;
        }
    }

    /**
     * helper method for sortedInBetween function
     * @param data1 lower threshold
     * @param data2 higher threshold
     * @param curr current node
     * @param list last that data is being added upon
     */
    private void sortedInBetweenHelper(T data1, T data2, AVLNode<T> curr, List<T> list) {
        if (curr == null) {
            return;
        } else {
            if (curr.getData().compareTo(data1) > 0) {
                sortedInBetweenHelper(data1, data2, curr.getLeft(), list);
                if (curr.getData().compareTo(data2) < 0) {
                    list.add(curr.getData());
                    sortedInBetweenHelper(data1, data2, curr.getRight(), list);
                }
            } else {
                sortedInBetweenHelper(data1, data2, curr.getRight(), list);
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
    public AVLNode<T> getRoot() {
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
