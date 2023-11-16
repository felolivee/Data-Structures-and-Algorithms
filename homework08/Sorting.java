import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator cannot be null");
        } else {
            for (int i = arr.length - 1; i >= 0; i--) {
                int maxInd = i;
                for (int j = i - 1; j >= 0; j--) {
                    if (comparator.compare(arr[j], arr[maxInd]) > 0) {
                        maxInd = j;
                    }
                }
                T iVal = arr[i];
                arr[i] = arr[maxInd];
                arr[maxInd] = iVal;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator cannot be null");
        } else {
            int startInd = 0;
            int endInd = arr.length - 1;
            int swapped = 0;
            while (endInd > startInd) {
                swapped = startInd;
                for (int i = startInd; i < endInd; i++) {
                    if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                        T iVal = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = iVal;
                        swapped = i;
                    }
                }
                endInd = swapped;
                for (int i = endInd; i > startInd; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T jVal = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = jVal;
                        swapped = i;
                    }
                }
                startInd = swapped;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator cannot be null");
        } else {
            if (arr.length == 1) {
                return;
            } else {
                int midInd = arr.length / 2;
                int leftLength = arr.length / 2;
                int rightLength = arr.length - leftLength;
                T[] leftArr = (T[]) new Object[leftLength];
                T[] rightArr = (T[]) new Object[rightLength];
                for (int i = 0; i < leftLength; i++) {
                    leftArr[i] = arr[i];
                }
                for (int i = 0; i < rightLength; i++) {
                    rightArr[i] = arr[i + leftLength];
                }
                mergeSort(leftArr, comparator);
                mergeSort(rightArr, comparator);
                int i = 0;
                int j = 0;
                //neither subarrays are emptied
                while (i < leftArr.length && j < rightArr.length) {
                    if (comparator.compare(leftArr[i], rightArr[j]) <= 0) {
                        arr[i + j] = leftArr[i];
                        i++;
                    } else {
                        arr[i + j] = rightArr[j];
                        j++;
                    }
                }
                //right subarray is emptied
                while (i < leftArr.length) {
                    arr[i + j] = leftArr[i];
                    i++;
                }
                //left subarray is emptied
                while (j < rightArr.length) {
                    arr[i + j] = rightArr[j];
                    j++;
                }
            }
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("The comparator cannot be null");
        } else if (rand == null) {
            throw new IllegalArgumentException("The random object cannot be null");
        } else if (!(1 <= k && k <= arr.length)) {
            throw new IllegalArgumentException("k is not in the range of the provided array");
        } else {
            int start = 0;
            int end = arr.length - 1;
            return quickSelectHelper(k, arr, comparator, rand, start, end);
        }
    }

    /**
     * Helper method of quickSelect
     * @param k kth smallest
     * @param arr array of T data
     * @param comparator comparator object that compares data in arr
     * @param rand the Random object used to select pivots
     * @param start start index of arr
     * @param end end index of arr
     * @return kth smallest data in arr
     * @param <T> data type to sort
     */
    private static <T> T quickSelectHelper(int k, T[] arr, Comparator<T> comparator,
                                      Random rand, int start, int end) {
        if ((end - start) <= 0) {
            return arr[start];
        } else {
            int pivotInd = rand.nextInt(end - start + 1) + start;
            T pivotVal = arr[pivotInd];
            arr[pivotInd] = arr[start];
            arr[start] = pivotVal;

            int i = start + 1;
            int j = end;
            while (i <= j) {
                while ((i <= j) && (comparator.compare(arr[i], pivotVal) <= 0)) {
                    i++;
                }
                while ((i <= j) && (comparator.compare(arr[j], pivotVal) >= 0)) {
                    j--;
                }
                if (i <= j) {
                    T iVal = arr[i];
                    arr[i] = arr[j];
                    arr[j] = iVal;
                    i++;
                    j--;
                }
            }
            T startVal = arr[start];
            arr[start] = arr[j];
            arr[j] = startVal;
            if (j == (k - 1)) {
                return arr[j];
            } else if (j > (k - 1)) {
                return quickSelectHelper(k, arr, comparator, rand, start, j - 1);
            } else {
                return quickSelectHelper(k, arr, comparator, rand, j + 1, end);
            }

        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null.");
        } else {
            List<Integer>[] buckets = new ArrayList[19];
            int maxNum = 0;
            int k = 1;
            for (int i = 0; i < arr.length; i++) {
                if (Math.abs(arr[i]) > maxNum) {
                    maxNum = Math.abs(arr[i]);
                }
            }
            while ((maxNum) >= 10) {
                k++;
                maxNum = maxNum / 10;
            }
            for (int i = 0; i < 19; i++) {
                buckets[i] = new ArrayList<Integer>();
            }
            int ind = 0;
            int dig = 1;
            for (int i = 0; i < k; i++) {
                for (int j = 0; j < arr.length; j++) {
                    ind = (arr[j] / dig) % 10 + 9;
                    buckets[ind].add(arr[j]);
                }
                ind = 0;
                for (List<Integer> bucket : buckets) {
                    while (!bucket.isEmpty()) {
                        arr[ind] = bucket.remove(0);
                        ind++;
                    }
                }
                dig = dig * 10;
            }

        }

    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be null.");
        } else {
            PriorityQueue<Integer> minHeap = new PriorityQueue<>(data);
            int[] sorted = new int[data.size()];
            for (int i = 0; i < data.size(); i++) {
                sorted[i] = minHeap.poll();
            }
            return sorted;
        }
    }
}
