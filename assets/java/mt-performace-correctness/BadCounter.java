public class BadCounter {
    public static int NUM_THREADS = 1;
    public static int TIMES = 100_000_000;
    public static long TIMES_PER_THREAD = TIMES / NUM_THREADS;

    public static void main (String[] args) {
	Counter counter = new Counter();
	Thread[] threads = new Thread[NUM_THREADS];
	
	for (int i = 0; i < NUM_THREADS; i++) {
	    threads[i] = new Thread(new ThreadCount(counter, TIMES_PER_THREAD));
	}

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

	System.out.println("Expected final count: " +
			   NUM_THREADS * TIMES_PER_THREAD +
			   "\nActual final count: " +
			   counter.getCount() +
			   "\nThat took " + (stop - start) / 1_000_000 +
			   "ms.");
    }
}
