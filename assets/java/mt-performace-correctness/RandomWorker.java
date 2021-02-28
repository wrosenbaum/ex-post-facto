import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomWorker implements Runnable {
    private long numValues;
    private Random r;

    public RandomWorker(Random r, long numValues) {
	this.numValues = numValues;
	this.r = r;
    }

    public void run () {
	for (long i = 0; i < numValues; i++) {
	    r.nextInt();
	}
    }
}
