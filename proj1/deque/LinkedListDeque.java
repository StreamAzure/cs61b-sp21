package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T> {

    private class Node {
        private T item;
        private Node next;
        private Node prev;

        public Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node headSentinel;
    private Node tailSentinel;

    private int size;

    public LinkedListDeque() {
        this.headSentinel = new Node(null, null, null);
        this.tailSentinel = new Node(null, null, null);
        headSentinel.next = tailSentinel;
        tailSentinel.prev = headSentinel;
    }

    public void addFirst(T item) {
        Node node = new Node(item, null, null);
        node.prev = headSentinel;
        node.next = headSentinel.next;
        node.next.prev = node;
        headSentinel.next = node;
        size += 1;
    }

    public void addLast(T item) {
        Node node = new Node(item, null, null);
        node.prev = tailSentinel.prev;
        node.next = tailSentinel;
        node.prev.next = node;
        tailSentinel.prev = node;
        size += 1;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    public T get(int index) {
        Node nowNode = headSentinel.next;
        int cnt = 0;
        while (nowNode != tailSentinel) {
            if (cnt == index) {
                return nowNode.item;
            }
            nowNode = nowNode.next;
            cnt += 1;
        }
        return null;
    }

    public void printDeque() {
        for (T item : this) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node deletedNode = headSentinel.next;
        headSentinel.next = deletedNode.next;
        headSentinel.next.prev = headSentinel;
        size -= 1;
        return deletedNode.item;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        Node deletedNode = tailSentinel.prev;
        tailSentinel.prev = deletedNode.prev;
        tailSentinel.prev.next = tailSentinel;
        size -= 1;
        return deletedNode.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node nowNode;

        public LinkedListDequeIterator() {
            nowNode = headSentinel;
        }
        @Override
        public boolean hasNext() {
            return nowNode.next != tailSentinel;
        }

        @Override
        public T next() {
            Node returnNode = nowNode.next;
            nowNode = returnNode;
            return returnNode.item;
        }
    }
}
