package deque;

import java.util.Iterator;

public interface Deque<T> {

    void addFirst(T item);

    void addLast(T item);
    boolean isEmpty();

    int size();

    void printDeque();

    T getRecursive(int index);

    Iterator<T> iterator();

    boolean equals(Object o);

}
