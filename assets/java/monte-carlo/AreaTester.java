public class AreaTester {
    public static final int N_POINTS = 1_000_000;
    
    public static void main (String[] args) {
	Blob b = new Blob();

	// a circle centered at the origin with radius 1 to the blob
	b.add(new Circle(0.0, 0.0, 1.0));
	

	double area = b.estimateArea(N_POINTS);

	System.out.printf("blob bounded by %.2f <= x <= %.2f, %.2f <= y <= %.2f\n", b.getXMin(), b.getXMax(), b.getYMin(), b.getYMax());
	System.out.println("area = " + area);
    }
}
