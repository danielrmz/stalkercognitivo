/*************************************************************************
 *  Compilation:  javac Queue.java
 *  Execution:    java Queue
 *
 *  A generic queue, implemented using a linked list. Each queue
 *  element is of type Item.
 *  http://www.cs.princeton.edu/introcs/43stack/Queue.java.html
 *************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<Item> implements Iterable<Item> {
    private int N;         // number of elements on queue
    private Node first;    // beginning of queue
    private Node last;     // end of queue

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;
    }

    // create an empty queue
    public Queue() {
        first = null;
        last  = null;
    }

    // is the queue empty?
    public boolean isEmpty() { return first == null; }
    public int length()      { return N;             }
    public int size()        { return N;             }

    // add an item to the queue
    public void enqueue(Item item) {
        Node x = new Node();
        x.item = item;
        if (isEmpty()) { first = x;     last = x; }
        else           { last.next = x; last = x; }
        N++;
    }

    // remove and return the least recently added item
    public Item dequeue() {
        if (isEmpty()) throw new RuntimeException("Queue underflow");
        Item item = first.item;
        first = first.next;
        N--;
        return item;
    }

    // remove and return the least recently added item
    public String toString() {
        String s = "";
        for (Node x = first; x != null; x = x.next)
            s += x.item + " ";
        return s;
    }

    public Iterator<Item> iterator()  { return new QueueIterator();  }

    // an iterator, doesn't implement remove() since it's optional
    private class QueueIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
}