public class FactorialTest {
    private static final long MAX = 20;
    
    private static long rFactorial (long n) {
	if (n <= 1) {
	    return 1;
	}

	return n * rFactorial(n - 1);	    
    }

    private static long iFactorial (long n) {
	long val = 1;

	for (long i = 1; i <= n; i++) {
	    val *= i;
	}

	return val;
    }


    public static void main (String[] args) {
	long start, stop;
	
	System.out.println("Computing the first few factorials recursively...");

	start = System.nanoTime();
	for (int i = 1; i <= MAX; i++) {
	    System.out.println(i + "! = " + rFactorial(i));
	}
	stop = System.nanoTime();
	System.out.println("That took " + ((stop - start) / 1_000) + " us");

	System.out.println("\nComputing the first few factorials iteratively...");
	start = System.nanoTime();
	for (int i = 1; i <= MAX; i++) {
	    System.out.println(i + "! = " + iFactorial(i));
	}
	stop = System.nanoTime();
	System.out.println("That took " + ((stop - start) / 1_000) + " us");

	
    }
}
