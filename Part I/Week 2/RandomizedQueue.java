import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int rqSize;
    private int rqCapacity;
    private Item[] items;

    // construct an empty randomized queue
    public RandomizedQueue() {
        rqCapacity = 1;
        items = (Item[]) new Object[rqCapacity];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return rqSize == 0;
    }

    private boolean isFull() {
        return rqSize == rqCapacity;
    }

    // return the number of items on the randomized queue
    public int size() {
        return rqSize;
    }

    private void resize(final int capacity) {
        rqCapacity = capacity;
        Item[] copy = (Item[]) new Object[rqCapacity];
        for (int i = 0; i < rqSize; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    // add the item
    public void enqueue(final Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isFull()) {
            resize(2 * rqCapacity);
        }
        items[rqSize++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(rqSize);
        Item item = items[index];
        items[index] = null;
        if (!isEmpty() && index != rqSize - 1) {
            items[index] = items[rqSize - 1];
            items[rqSize - 1] = null;
        }
        rqSize--;
        if (rqSize > 0 && rqSize == rqCapacity / 4) {
            resize(rqCapacity / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int index = StdRandom.uniform(rqSize);
        Item item = items[index];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Item[] rqItems;
        private final int size = rqSize;
        private int index = 0;

        RandomizedQueueIterator() {
            rqItems = (Item[]) new Object[rqSize];
            for (int i = 0; i < rqSize; i++) {
                rqItems[i] = items[i];
            }
            StdRandom.shuffle(rqItems);
        }

        public boolean hasNext() {
            return index < size;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return rqItems[index++];
        }
    }

    public static void main(final String[] args) {
        // unit testing (optional)
    }
}
