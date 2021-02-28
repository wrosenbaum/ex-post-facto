public class RecursiveFibonacciTester {
    public static final long MAX = 45;

    private static long iFib (long n) {
	long prev = 0;
	long cur = 1;
	long next;

	for (long count = 1; count < n; count++) {
	    next = prev + cur;
	    prev = cur;
	    cur = next;
	}
	
    	return cur;
    }

    private static long rFib (long n) {
	if (n <= 2) return 1;

	else {
	    return rFib(n-1) + rFib(n-2);
	}
    }

    public static void main (String[] args) {
	long start, stop, recTime, iterTime;

	System.out.println("Comparing recursive and iterative runtimes...");
	
	start = System.nanoTime();
	rFib(MAX);
	stop = System.nanoTime();

	recTime = stop - start;

	start = System.nanoTime();
	iFib(MAX);
	stop = System.nanoTime();

	iterTime = stop - start;

	System.out.println("Recursive took " + recTime / iterTime + " times as long as iterative.");

    }
}
