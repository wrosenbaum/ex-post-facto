public class Fibonacci {
    private static long MIN = 44;
    private static long MAX = 45;
    
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
	System.out.println("Computing Fibonacci numbers iteratively...");
	for (long i = MIN; i <= MAX; i++) {
	    System.out.println("fibonacci(" + i + ") = " + iFib(i));
	}

	System.out.println("\nComputing Fibonacci numbers recursively...");
	for (long i = MIN; i <= MAX; i++) {
	    System.out.println("fibonacci(" + i + ") = " + rFib(i));
	}
    }
}
