public class Factorial {
    private static int rFactorial (int n) {
	if (n <= 1) {
	    return 1;
	}

	return n * rFactorial(n - 1);
	    
    }

    public static void main (String[] args) {
	System.out.println("Computing the first few factorials...");
	for (int i = 1; i <= 10; i++) {
	    System.out.println(i + "! = " + rFactorial(i));
	}
    }
}
