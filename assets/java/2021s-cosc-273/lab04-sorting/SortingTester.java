public class SortingTester {
    public static final int[] SIZES = {100_000, 1_000_000, 10_000_000, 50_000_000, 100_000_000};
    
    public static void main(String[] args) {
	System.out.printf( "\n     size |  baseline time (ms) | parallel time (ms) | speedup | passed \n"
			   + "----------+---------------------+--------------------+---------+--------\n");
	for (int size : SIZES) {
	    double[] data1 = new double[size];
	    double[] data2 = new double[size];
	
	    for (int i = 0; i < size; ++i) {
		data1[i] = Math.random();
		data2[i] = data1[i];
	    }

	    // test baseline implementation
	    long start = System.nanoTime();

	    Sorting.baselineSort(data1);

	    long stop = System.nanoTime();

	    long baselineTime = stop - start;

	    // test parallel sort
	    start = System.nanoTime();

	    Sorting.parallelSort(data2);

	    stop = System.nanoTime();

	    boolean passed = Sorting.isSorted(data2);

	    long parallelTime = stop - start;

	    double speedup = (double) baselineTime / parallelTime;

	    System.out.printf("%9d |%20d |%19d |%8.2f |%6b \n", size, baselineTime / 1_000_000, parallelTime / 1_000_000, speedup, passed);
	    
	}

	System.out.println("----------+---------------------+--------------------+---------+--------\n");
    }
}
