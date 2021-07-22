import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alex Nevsky
 *
 * Date: 27/05/2021
 */
public class RandomizedQueue<Item> implements Iterable<Item> {

  // initial capacity of underlying resizing array
  private static final int INIT_CAPACITY = 8;

  private Item[] q;       // queue elements
  private int n;          // number of elements on queue
  private int first;      // index of first element of queue
  private int last;       // index of next available slot

  // construct an empty randomized queue
  public RandomizedQueue() {
    q = (Item[]) new Object[INIT_CAPACITY];
    n = 0;
    first = 0;
    last = q.length - 1;
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return n == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return n;
  }

  // add the item
  public void enqueue(Item item) {
    // double size of array if necessary and recopy to front of array
    if (n == q.length) resize(2*q.length);   // double size of array if necessary
    q[StdRandom.uniform(0, n + 1)] = item; // add item
    if (last == q.length) last = 0;          // wrap-around
    n++;
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Queue underflow");
    Item item = q[first];
    q[first] = null;                            // to avoid loitering
    n--;
    first++;
    if (first == q.length) first = 0;           // wrap-around
    // shrink size of array if necessary
    if (n > 0 && n == q.length/4) resize(q.length/2);
    return item;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (isEmpty()) throw new NoSuchElementException("Queue underflow");
    if (n == 1) return q[0];
    else        return q[StdRandom.uniform(0, n)];
  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedQueue<Item>.ArrayIterator();
  }

  // an iterator, doesn't implement remove() since it's optional
  private class ArrayIterator implements Iterator<Item> {
    private int i = 0;
    public boolean hasNext()  { return i < n;                               }
    public void remove()      { throw new UnsupportedOperationException();  }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = q[(i + first) % q.length];
      i++;
      return item;
    }
  }

  // resize the underlying array
  private void resize(int capacity) {
    assert capacity >= n;
    Item[] copy = (Item[]) new Object[capacity];
    for (int i = 0; i < n; i++) {
      copy[i] = q[(first + i) % q.length];
    }
    q = copy;
    first = 0;
    last  = q.length - 1;
  }

  // unit testing (required)
  public static void main(String[] args) {
    RandomizedQueue<Integer> queue = new RandomizedQueue<>();
    queue.enqueue(1);
    StdOut.println(String.format("isEmpty: %b", queue.isEmpty()));
    queue.enqueue(3);
    queue.enqueue(4);
    StdOut.println(String.format("size: %d", queue.size()));
    StdOut.println(String.format("sample: %d", queue.sample()));
    StdOut.println(queue.dequeue());
    StdOut.println(queue.dequeue());
    StdOut.println(String.format("hasNext: %b", queue.iterator().hasNext()));
    StdOut.println(queue.dequeue());
  }

}
