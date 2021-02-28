public class TowerOfHanoi {

    // generate instructions for moving m disks from peg 'from'
    // to peg 'to', where 'other' is the third peg (not 'from' or 'to')
    public static void move (int m, int from, int to, int other) {
	// fill out code!
    }

    public static void printInstructions (int n) {
	move(n, 1, 3, 2);
    }
    
    public static void main (String[] args) {
	int numDisks = 4;
	System.out.println("Printing instructions for Tower of Hanoi puzzle with " + numDisks + " disks.");

	printInstructions(numDisks);
    }
}
