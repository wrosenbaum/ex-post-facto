public class RecursiveFactorialTester {
    public static final long MAX = 20;
    
    private static long rFactorial (long n) {
	if (n <= 1) {
	    return 1;
	}

	return n * rFactorial(n - 1);    
    }

    private static long iFactorial (long n) {
	if (n <= 1) {
	    return 1;
	}

	long val = 1;

	for (long i = 2; i <= n; i++) {
	    val *= i;
	}

	return val;
    }

    public static void main (String[] args) {
	long start, stop, recTime, iterTime;

	System.out.println("Comparing recursive and iterative runtimes...");
	
	start = System.nanoTime();
	rFactorial(MAX);
	stop = System.nanoTime();

	recTime = stop - start;

	start = System.nanoTime();
	iFactorial(MAX);
	stop = System.nanoTime();

	iterTime = stop - start;

	System.out.println("Recursive took " + recTime / iterTime + " times as long as iterative.");

    }
}
