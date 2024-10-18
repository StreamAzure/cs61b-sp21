package deque;

import java.util.Iterator;

public interface Deque<T> {

    void addFirst(T item);

    void addLast(T item);
    default boolean isEmpty() {
        return size() == 0;
    }

    T removeFirst();

    T removeLast();

    T get(int index);

    int size();

    void printDeque();

    Iterator<T> iterator();

    boolean equals(Object o);

}
