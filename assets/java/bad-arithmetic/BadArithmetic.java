public class BadArithmetic {
    public static void main (String[] args) {

	int a = 10;
	double recip = 1.0 / a;
	double product = 0;
	
	// add 1/a to itself a times
	
	for (int i = 0; i < a; i++) {
	    
	    product += recip;
	    
	}

	// product should be 1.0....
	double one = product;

	System.out.println("a = " + a + "\n" +
			   "a * (1/a) = " + one);


	int iter = 50;

	System.out.println("Multiplying `one` a bunch of times...");

	for (int i = 0; i < iter; i++) {
	    one = one * one;
	}

	System.out.println("one = " + one);
	
    }
}
