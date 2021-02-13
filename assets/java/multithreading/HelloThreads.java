/*
 * HelloThreads.java Our first multithreaded program!
 */

public class HelloThreads implements Runnable {
    public static final int NUM_THREADS = 4;  // number of threads to run

    private String message;                   // message printed by this

    // constructor sets the message to be displayed by this thread
    public HelloThreads (String message) {
	this.message = message;
    }

    // the run method required by the Runnable interface
    // this is the code that gets executed when we start the thread
    public void run () {
	System.out.println(message);
    }
    
    
    public static void main (String[] args) {
	// make an array of threads
	Thread[] threads = new Thread[NUM_THREADS];

	// initialize threads with a welcome message
	for (int i = 0; i < NUM_THREADS; i++) {
	    threads[i] = new Thread(new HelloThreads("Hello from thread " + i));
	}

	System.out.println("Say hello, threads!");

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

	System.out.println("Goodbye, threads!");

    }
}
