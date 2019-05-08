import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {

    public static void main(final String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Permutation k");
            return;
        }

        int k = Integer.parseInt(args[0]);
        if (k < 0) {
            System.out.println("Invalid value for k!");
            return;
        }

        RandomizedQueue<String> input = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            input.enqueue(StdIn.readString());
        }
        if (k > input.size()) {
            System.out.println("Invalid value for k!");
            return;
        }

        Iterator<String> inputIterator = input.iterator();
        for (int i = 0; i < k; i++) {
            System.out.println(inputIterator.next());
        }
        return;
    }
}
