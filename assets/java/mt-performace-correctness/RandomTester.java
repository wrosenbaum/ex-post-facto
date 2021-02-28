import java.util.Random;

public class RandomTester {
    public static int NUM_THREADS = 1;
    public static int TIMES = 10_000_000;
    public static long TIMES_PER_THREAD = TIMES / NUM_THREADS;

    public static void main (String[] args) {
	Random r = new Random();
	Thread[] threads = new Thread[NUM_THREADS];
	
	for (int i = 0; i < NUM_THREADS; i++) {
	    threads[i] = new Thread(new RandomWorker(r, TIMES_PER_THREAD));
	}

	System.out.println("Generating " + TIMES + " random numbers with " + NUM_THREADS + " threads.");
	long start = System.nanoTime();
	
	for (Thread t : threads)
	    t.start();

	for (Thread t : threads) {
	    try {
		t.join();
	    }
	    catch (InterruptedException e) {
	    }
	}

	long stop = System.nanoTime();

	System.out.println("That took " + (stop - start) / 1_000_000 + " ms.");
    }
}
