import java.util.concurrent.ThreadLocalRandom;

public class BusyTask implements Runnable {
    private long time;
    private int id;

    public BusyTask (int id, long maxTime) {
	this.id = id;
	this.time = ThreadLocalRandom.current().nextLong(maxTime);
    }

    public void run () {
	System.out.println("Starting thread " + id + ".");

	long start = System.currentTimeMillis();
	
	long cur;
	
	do {
	    cur = System.currentTimeMillis();
	} while (cur - start < time);

	System.out.println("Thread " + id + " finished after " + (cur - start) + " ms.");

    }
}
