public class Fraction {
    
    private int num;
    private int den;

    public static int gcd(int n, int m) {
	if (m == 0) return n;
	return gcd(m, n % m);
    }

    public Fraction () {
	num = 0;
	den = 1;
    }


    public Fraction (int num, int den) throws DivisionByZeroException {
	if (den == 0) {
	    throw new DivisionByZeroException("Tried to create Fraction with numerator " + num + " and denominator " + den);
	}
	
	this.num = num;
	this.den = den;
	reduce();
    }

    public void reduce () {
	int gcd = gcd(num, den);

	if (gcd != 0) {
	    num /= gcd;
	    den /= gcd;
	}
    }

    public int getNum () {return num;}
    public int getDen () {return den;}

    public Fraction plus (Fraction f) {
	try {
	    return new Fraction(num * f.den + den * f.num, den * f.den);
	}
	catch (DivisionByZeroException e) {
	    return null;
	}
    }

    public Fraction minus (Fraction f){
	return this.plus(f.times(-1));
    }

    public Fraction reciprocal () throws DivisionByZeroException {
	return new Fraction(den, num);
    }

    public Fraction times (Fraction f) {
	try {
	    return new Fraction(num * f.num, den * f.den);
	}
	catch (DivisionByZeroException e) {
	    return null;
	}

    }

    public Fraction times (int n) {
	try {
	    return new Fraction(num * n, den);
	}
	catch (DivisionByZeroException e) {
	    return null;
	}

    }

    public Fraction dividedBy (Fraction f) throws DivisionByZeroException {
	return this.times(f.reciprocal());
    }

    public boolean equals (Fraction f) {
	return (num * f.den == f.num * den);
    }

    public String toString () {
	return (num + " / " + den);
    }
}
