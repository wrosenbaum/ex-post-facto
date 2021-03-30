public class FractionTester {
    public static void main (String[] args) {
	System.out.println("Making some fractions...");

	Fraction a = new Fraction();
	Fraction b = new Fraction();
	Fraction c = new Fraction();

	try {
	    a = new Fraction(1, 2);
	    b = new Fraction(1, 3);
	    c = new Fraction(1, 6);

	    System.out.println("a = " + a.toString() +
			       ", b = " + b.toString() +
			       ", c = " + c.toString());
	}
	catch (DivisionByZeroException e){
	}
	
	System.out.println("Doing some basic arithmetic...");

	System.out.println("a + b = " + a.plus(b).toString());

	try {
	    System.out.println("c / (a + b) = " + c.dividedBy(a.plus(b)));
	}
	catch (DivisionByZeroException e) {   
	    System.out.println("Oops. You tried to divide " + c.toString() + " by " + a.plus(b).toString() +  ". " + e);
	}

	try {
	    System.out.println("1 / (a - b - c) = " + a.minus(b).minus(c).reciprocal());
	}
	catch (DivisionByZeroException e) {
	    System.out.println("Oops. You tried to take the reciprocal of " + a.minus(b).minus(c).toString() +  ". " + e);
	}

	System.out.println("a - b = " + a.minus(b).toString());
	System.out.println("a + b - c = " + a.plus(b).minus(c).toString());

		    
    }
}
