import java.util.Arrays;

public class BadCounter {
    public static int NUM_THREADS = 4;
    public static int TIMES = 1_000_000;
    public static long TIMES_PER_THREAD = TIMES / NUM_THREADS;

    public static void main (String[] args) {
	Counter counter = new Counter();
	Thread[] threads = new Thread[NUM_THREADS];
	
	for (int i = 0; i < NUM_THREADS; i++) {
	    threads[i] = new Thread(new ThreadCount(counter, TIMES_PER_THREAD));
	}

	for (Thread t : threads)
	    t.start();

	for (Thread t : threads) {
	    try {
		t.join();
	    }
	    catch (InterruptedException e) {
	    }
	}

	System.out.println("Expected final count: " + NUM_THREADS * TIMES_PER_THREAD);
	System.out.println("Actual final count: " + counter.getCount());
    }
}
