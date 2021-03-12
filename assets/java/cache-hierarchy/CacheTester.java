import java.util.Random;

/*
 * CacheTester.java: a simple program to test the effect of cache
 * locality on program performance. This program creates arrays of
 * various sizes, and tests the runtime of accessing the arrays (1) in
 * linear order and (2) in random order. The total number of accesses
 * to each array is fixed to NUM_ACCESSES.
 * 
 * You should implement the method getLinearizedArray. After doing so
 * and un-commenting the relevant statements in the main method, your
 * program will also test the performance of first reordering the
 * elements of the original array so that they appear in the "random"
 * order, then accessing the elements in this order.
 */

public class CacheTester {

    public static int LOG_ACCESSES = 30;
    public static long NUM_ACCESSES = (long) Math.pow(2, LOG_ACCESSES);
    public static int[] LOG_SIZE = {18, 19, 20, 21, 22, 23, 24};
    private static Random r = new Random();

    /*
     * Return an array of random floats in the range [0.0, 1.0] of size 'size'.
     */
    private static float[] getRandomArray (int size) {
	float[] arr = new float[size];

	for (int i = 0; i < size; ++i) {
	    arr[i] = r.nextFloat();
	}

	return arr;
    }

    /*
     * returns the array {0, 1, 2, ..., size - 1}
     */
    private static int[] getLinearIndexArray (int size) {
	int[] arr = new int[size];

	for (int i = 0; i < size; ++i) {
	    arr[i] = i;
	}

	return arr;
    }

    /*
     * returns an array of 'size' elements containing the elements
     * 0, 1, 2, ..., size - 1 in a random order
     */
    private static int[] getRandomIndexArray (int size) {
	// create the array {0, 1, ..., size - 1}
	int[] arr = getLinearIndexArray(size);

	// For each i = 1, 2, ..., size - 1, pick a random
	// number j from the range [0, i-1], then swap
	// arr[i] and arr[j].
	for (int i = 1; i < size; ++i) {
	    int j = r.nextInt(i + 1);
	    int val = arr[j];
	    arr[j] = arr[i];
	    arr[i] = val;
	}

	return arr;
    }

    /*
     * Given a data array arr and an array of indices, returns a new array 
     * consisting of the same elements of arr, but appearing in the order 
     * prescribed by indices array. 
     *
     * For example, if arr = {2, 4, 5, 6} and indices = {2, 1, 3, 0}, the 
     * method will return {5, 4, 6, 2} (= {arr[2], arr[1], arr[3], arr[0]}).
     */
    private static float[] getLinearizedArray (float[] arr, int[] indices) {

	/* complete this implementation! */
	
	return arr; // change this to return a new array!
    }



    /*
     * Given a data array arr and array of 'indices', returns the sum
     * of the elements in arr with indices in 'indices'. The sum is computed
     * in the order the indices appear in 'indices'.
     */
    public static float getSum(float[] arr, int[] indices) {
	float sum = 0;

	for (int i : indices) {
	    sum += arr[i];
	}

	return sum;
    }
    
    public static void main (String[] args) {

	System.out.printf("Timing for N = 2^" + LOG_ACCESSES + " array accesses:\n\n" +
			  "  arr size   time linear (ms)   time random (ms)   t linearized (ms)\n" + "-----------------------------------------------------------------------\n");
	
	for (int n : LOG_SIZE) {
	    
	    /* size of the array */
	    int size = (int) Math.pow(2, n);

	    /* number of times to iterate over array*/
	    long iter = NUM_ACCESSES / size;

	    /* array of random floats*/
	    float[] arr = getRandomArray(size);

	    /* indices {0, 1, 2,..., size - 1}*/
	    int[] linearIndices = getLinearIndexArray(size);

	    /* shuffled indices {0, 1, 2,..., size - 1}*/
	    int[] randomIndices = getRandomIndexArray(size); 

	    /* make NUM_ACCESSES accesses to arr in linear order */
	    long linearStart = System.nanoTime();
	    for (int i = 0; i < iter; ++i) {
		float sumLinear = getSum(arr, linearIndices);
	    }
	    long linearStop = System.nanoTime();
	    long linearElapsedMS = (linearStop - linearStart) / 1_000_000;

	    /* make NUM_ACCESSES accesses to arr in random order */
	    long randomStart = System.nanoTime();
	    for (int i = 0; i < iter; ++i) {
		float sumRandom = getSum(arr, randomIndices);
	    }
	    long randomStop = System.nanoTime();
	    long randomElapsedMS = (randomStop - randomStart) / 1_000_000;

	    /* make NUM_ACCESSES accesses to linearized array in linear order */	    
	    long linearizedStart = System.nanoTime();
	    // un-comment to test after implementing getLinearizedArray method
	    // float[] linearizedArr = getLinearizedArray(arr, randomIndices);
	    // for (int i = 0; i < iter; ++i) {
	    // 	float sumLinearized = getSum(linearizedArr, linearIndices);
	    // }
	    long linearizedStop = System.nanoTime();
	    long linearizedElapsedMS = (linearizedStop - linearizedStart) / 1_000_000;


	    System.out.printf(" %9d   %11d        %11d        %12d\n", size, linearElapsedMS, randomElapsedMS, linearizedElapsedMS);
	}
	
    }
}
