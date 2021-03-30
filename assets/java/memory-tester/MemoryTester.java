public class MemoryTester {
    public static void main (String[] args) {
	PairOfNumbers p = new PairOfNumbers(1, 2);

	//System.out.println("p = " + p);

	Number first = p.getFirst();
	first.value = 3;

	System.out.println("p = " + p);
    }
}

class Number {
    public int value;

    public Number (int val) {
	value = val;
    }
}

class PairOfNumbers {
    private Number first;
    private Number second;

    public PairOfNumbers (int firstVal, int secondVal) {
	first = new Number(firstVal);
	second = new Number(secondVal);
    }

    public String toString() {
	return "(" + first.value + ", " + second.value + ")";
    }

    public Number getFirst() { return first; }
    public Number getSecond() { return second; }
}
