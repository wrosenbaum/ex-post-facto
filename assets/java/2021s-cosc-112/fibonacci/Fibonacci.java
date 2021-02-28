public class Fibonacci {
    private static long iFib (long n) {
	return 0;
    }

    private static long rFib (long n) {
	return 0;
    }
    
    public static void main (String[] args) {
	System.out.println("Computing Fibonacci numbers iteratively...");
	for (int i = 1; i <= 10; i++) {
	    System.out.println("fibonacci(" + i + ") = " + iFib(i));
	}

	System.out.println("\nComputing Fibonacci numbers recursively...");
	for (int i = 1; i <= 10; i++) {
	    System.out.println("fibonacci(" + i + ") = " + rFib(i));
	}
    }
}
