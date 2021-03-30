public class BadFraction {
    
    private int num;
    private int den;

    public static int gcd(int n, int m) {
	if (m == 0) return n;
	return gcd(m, n % m);
    }

    public BadFraction () {
	num = 0;
	den = 1;
    }


    public BadFraction (int num, int den) {	
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

    public BadFraction plus (BadFraction f) {

	return new BadFraction(num * f.den + den * f.num, den * f.den);
    }

    public BadFraction minus (BadFraction f){
	return this.plus(f.times(-1));
    }

    public BadFraction reciprocal () {
	return new BadFraction(den, num);
    }

    public BadFraction times (BadFraction f) {
	return new BadFraction(num * f.num, den * f.den);
    }

    public BadFraction times (int n) {
	    return new BadFraction(num * n, den);
    }

    public BadFraction dividedBy (BadFraction f) {
	return this.times(f.reciprocal());
    }

    public boolean equals (BadFraction f) {
	return (num * f.den == f.num * den);
    }

    public String toString () {
	return (num + " / " + den);
    }
}
