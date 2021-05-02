import java.util.concurrent.*;

public class InterruptTest {
    public static final long MAX_TIMEOUT = 1000;
    
    public static void main (String[] args) {
	int nThreads = Runtime.getRuntime().availableProcessors();
	ExecutorService pool = Executors.newFixedThreadPool(nThreads);

	Thread[] tasks = new Thread[nThreads];

	for (int i = 0; i < nThreads; ++i) {
	    tasks[i] = new Thread(new BusyTask(i, 2 * MAX_TIMEOUT));
	    pool.execute(tasks[i]);
	}

	try {
	    Thread.sleep(MAX_TIMEOUT);
	} catch (InterruptedException e) {
	    // ignore interruption
	}
	  
	pool.shutdownNow();
	
	System.out.println("Shut down pool!");
    }
}
