import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class MaxFinder {
    public static final int POOL_SIZE = Runtime.getRuntime().availableProcessors();
    public static final int DATA_SIZE = 1_000_000;

    public static double[] getData() {
	double[] data = new double[DATA_SIZE];
	for (int i = 0; i < DATA_SIZE; ++i) {
	    data[i] = Math.random();
	}

	return data;
    }

    public static double findMax(double[] data) {
	double max = Double.MIN_VALUE;

	for (int i = 0; i < DATA_SIZE; ++i) {
	    if (max < data[i]) {
		max = data[i];
	    }
	}

	return max;
    }
    
    public static void main (String[] args) {
	System.out.println("Generating array of " + DATA_SIZE + " doubles...\n");
	
	double[] data = getData();

	System.out.println("Starting sequential computation...");

	long start = System.nanoTime();

	double max = findMax(data);

	long stop = System.nanoTime();

	System.out.println("The maximum value is " + max +
			   "\nThat took " + (stop - start) / 1_000_000 + "ms");

	System.out.println("\nStarting parallel computation with " + POOL_SIZE + " processors...");

	start = System.nanoTime();

	MaxTask mt = new MaxTask(data, 0, DATA_SIZE);
	ForkJoinPool pool = new ForkJoinPool(POOL_SIZE);
	max = pool.invoke(mt);

	stop = System.nanoTime();

	System.out.println("The maximum value is " + max +
			   "\nThat took " + (stop - start) / 1_000_000 + "ms");
    }
}

class MaxTask extends RecursiveTask<Double> {
    public static int PARALLEL_LIMIT = MaxFinder.DATA_SIZE / 1_000;
    
    double[] data;
    int min;
    int max;
    
    public MaxTask (double[] data, int min, int max) {
	this.data = data;
	this.min = min;
	this.max = max;
    }

    @Override
    protected Double compute () {
	if (max - min <= PARALLEL_LIMIT) {
	    return findMax();
	}

	MaxTask left = new MaxTask(data, min, min + (max - min) / 2);
	MaxTask right = new MaxTask(data, min + (max - min) / 2, max);
	
	right.fork();

	double l = left.compute();
	
	double r = right.join();

	return Math.max(l, r); 
    }

    private Double findMax() {
	double maxValue = Double.MIN_VALUE;

	for (int i = min; i < max; ++i) {
	    if (maxValue < data[i]) {
		maxValue = data[i];
	    }
	}

	return maxValue;
    }
}
