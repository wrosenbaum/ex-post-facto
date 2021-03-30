public class Fraction {

    private int num;
    private int den;

    
    // initialize with a default value of 0
    public Fraction () {
	this(0, 1);
    } 

    // initialize with a value of numerator / 1
    public Fraction (int num) {
	this(num, 1);
    }

    // initialize with the same value as f
    public Fraction (Fraction f) {
	this(f.getNum(), f.getDen());
    }

    // initialize with a value of numerator / 1
    public Fraction (int num, int den) {
	this.num = num;
	this.den = den;
    }

    

    
    // public getters for numerator and denominator
    public int getNum() {return num;}
    public int getDen() {return den;}

    // get a double approximating the value of the Fraction
    public double toDouble() { return (double) num / den;}
    
    // get a String representing the fraction
    @Override
    public String toString () {return (num + " / " + den);}

    
    // make a new Fraction that is the sum of this and f, and return the result
    public Fraction plus (Fraction f) {
	return plus(f.getNum(), f.getDen());
    }

    // class method for adding two fractions
    public static Fraction add (Fraction f, Fraction g) {
	return f.plus(g);
    }

    // make a new Fraction that is the sum of this and int value, and return the result
    public Fraction plus (int value) {
	return plus(value, 1);
    }

    // make a new Fraction that is the sum of this and (numerator / denominator), 
    // and return the result
    public Fraction plus (int num, int den) {
	int sumNum = this.num * den + num * this.den;
	int sumDen = this.den * den;
	return new Fraction(sumNum, sumDen);
    }

    // add the value of f to this fraction
    public void plusEquals (Fraction f) {
	num = num * f.getDen() + den * f.getNum();
	den = den * f.getDen();
	this.reduce();
    }
    

    // make a new Fraction that is the product of this and f, and
    // return the result
    public Fraction times (Fraction f) {
        int prodNum = num * f.getNum();
        int prodDen = den * f.getDen();

        return new Fraction(prodNum, prodDen);

    }

    // compute the greatest common divisor of two ints
    // this is utter magic
    private int gcd (int n, int m) {
        if (m == 0) return n;
        return gcd(m, n % m);
    }

    // reduce Fraction to lowest terms by dividing numerator and
    // denominator by the greatest common divisor
    private void reduce () {
        
        int div = gcd(num, den);
        
        num /= div;
        den /= div;
    }
}
