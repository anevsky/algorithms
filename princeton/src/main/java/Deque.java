import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alex Nevsky
 *
 * Dequeue.
 * A double-ended queue or deque (pronounced “deck”) is a generalization of a stack and a queue
 * that supports adding and removing items from either the front or the back of the data structure.
 *
 * Date: 27/05/2021
 */
public class Deque<Item> implements Iterable<Item> {

  private Node<Item> first;
  private Node<Item> last;
  private int n;

  private static class Node<Item> {
    private Item item;
    private Node<Item> next;
    private Node<Item> prev;
  }

  // construct an empty deque
  public Deque() {
    first = null;
    last = null;
    n = 0;
  }

  // is the deque empty?
  public boolean isEmpty() {
    return n == 0;
  }

  // return the number of items on the deque
  public int size() {
    return n;
  }

  // add the item to the front
  public void addFirst(Item item) {
    if (item == null) throw new IllegalArgumentException();
    Deque.Node<Item> oldfirst = first;
    first = new Deque.Node<>();
    first.item = item;
    first.prev = oldfirst;
    if (isEmpty()) last = first;
    else           oldfirst.next = first;
    n++;
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null) throw new IllegalArgumentException();
    Deque.Node<Item> oldlast = last;
    last = new Deque.Node<>();
    last.item = item;
    last.next = oldlast;
    if (isEmpty()) first = last;
    else           oldlast.prev = last;
    n++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) throw new NoSuchElementException("Deque underflow");
    Item item = first.item;
    first = first.prev;
    n--;
    if (isEmpty()) last = null;   // to avoid loitering
    return item;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (isEmpty()) throw new NoSuchElementException("Deque underflow");
    Deque.Node<Item> oldlast = last;
    last.next = null;
    if (isEmpty()) first = null;
    n--;
    return oldlast.item;
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator()  {
    return new Deque<Item>.LinkedIterator(first);
  }

  // an iterator, doesn't implement remove() since it's optional
  private class LinkedIterator implements Iterator<Item> {
    private Deque.Node<Item> current;

    public LinkedIterator(Deque.Node<Item> first) {
      current = first;
    }

    public boolean hasNext()  { return current != null;                     }
    public void remove()      { throw new UnsupportedOperationException();  }

    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  // unit testing (required)
  public static void main(String[] args) {
    Deque<Integer> queue = new Deque<>();
    queue.addFirst(1);
    StdOut.println(String.format("isEmpty: %b", queue.isEmpty()));
    queue.addFirst(3);
    queue.addLast(4);
    StdOut.println(String.format("size: %d", queue.size()));
    StdOut.println(queue.removeFirst());
    StdOut.println(queue.removeLast());
    StdOut.println(String.format("hasNext: %b", queue.iterator().hasNext()));
    StdOut.println(queue.removeFirst());
  }

}
