/*
 * SharedArray.java A simple program showing how multiple threads can
 * access and modify a shared array. In the program, each thread has
 * an associated id number and sets the value of one entry of the
 * array. For example, thread 0 sets array[0] to 0, thread 1 sets
 * array[1] to 1, and so on.
 */

import java.util.Arrays;

public class SharedArray implements Runnable {
    public static final int NUM_THREADS = 4;  // number of threads to run

    private int id;                           // thread id
    private int[] array;                      // array


    public SharedArray (int id, int[] array) {
	this.id = id;
	this.array = array;
    }

    public void run () {
	// set the value at my index to my id
	array[id] = id;
    }
    
    
    public static void main (String[] args) {
	// the shared array with one index for each thread
	int[] shared = new int[NUM_THREADS];
	
	// make an array of threads
	Thread[] threads = new Thread[NUM_THREADS];

	// initialize threads with a welcome message
	for (int i = 0; i < NUM_THREADS; i++) {
	    threads[i] = new Thread(new SharedArray(i, shared));
	}

	// start all of the threads
	for (Thread t : threads) {
	    t.start();
	}

	// wait for all threads to complete
	for (Thread t : threads) {
	    try {
		t.join();
	    }
	    catch (InterruptedException ignored) {
		// don't care if t was interrupted
	    }
	}
	
	// print the contents of the array to the terminal 
	System.out.println("shared = " + Arrays.toString(shared));

    }
}
