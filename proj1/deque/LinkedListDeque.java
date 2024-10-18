package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {

    private class Node {
        private T item;
        private Node next;
        private Node prev;

        Node(T item, Node next, Node prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }
    }

    private Node headSentinel;
    private Node tailSentinel;

    private Node nodeForRecursive;

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

    public T getRecursive(int index) {
        nodeForRecursive = headSentinel.next;
        return getRecursiveHelper(index);
    }

    private T getRecursiveHelper(int index) {
        if (nodeForRecursive == tailSentinel) {
            return null;
        }
        if (index == 0) {
            T result = nodeForRecursive.item;
            return result;
        }
        nodeForRecursive = nodeForRecursive.next;
        return getRecursiveHelper(index - 1);
    }

    public boolean equals(Object o) {
        if (!(o instanceof Deque)) {
            return false;
        }
        if (((Deque<?>) o).size() != size()) {
            return false;
        }
        for (int i = 0; i < ((Deque<?>) o).size(); i++) {
            if (!((Deque<?>) o).get(i).equals(get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node nowNode;

        LinkedListDequeIterator() {
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
