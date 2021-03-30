public class IQueue {
    AtomicInteger head = new AtomicInteger(0);
    AtomicInteger tail = new AtomicInteger(0);
    int[] items = new int[Integer.MAX_VALUE];

    public void enq (int x) {
	int slot;
	do {
	    slot = tail.get();
	} while (!tail.compareAndSet(slot, slot+1));
	items[slot] = x;
    }

    public int deq () throws EmptyException {
	int value;
	int slot;
	do {
	    slot = head.get();
	    value = items[slot];
	    if (value == null)
		throw new EmptyException();
	} while (!head.compareAndSet(slot, slot+1));
	return value;
    }
}
