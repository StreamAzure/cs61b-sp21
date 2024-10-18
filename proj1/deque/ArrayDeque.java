package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T> {
    /**
     * 由于总是在首尾添加和删除元素，不用考虑中间元素删除的情况
     */

    private int nextFirst;
    private int nextLast;
    private T[] items = (T[]) new Object[8];
    private int size;

    private static final double EXPANSION_RATE = 1.5;

    private static final int MIN_ITEMS_LENGTH = 8;

    public ArrayDeque() {
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int i = (nextFirst + 1) % items.length;
        int j = 0;
        while (i != nextLast) {
            a[j] = items[i];
            i = (i + 1) % items.length;
            j++;
        }
        items = a;
        nextFirst = capacity - 1;
        nextLast = j;
    }

    public void addFirst(T x) {
        if (size == items.length - 2) {
            resize((int) (size * EXPANSION_RATE));
        }
        items[nextFirst] = x;
        nextFirst = getPreIdx(nextFirst);
        size += 1;
    }

    public void addLast(T x) {
        if (size == items.length - 2) {
            resize((int) (size * EXPANSION_RATE));
        }
        items[nextLast] = x;
        nextLast = getNextIdx(nextLast);
        size += 1;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T deleteItem = items[getNextIdx(nextFirst)];
        items[getNextIdx(nextFirst)] = null;
        nextFirst = getNextIdx(nextFirst);
        size -= 1;

        if (size > MIN_ITEMS_LENGTH && items.length / 3 > size) {
            resize((int) (items.length / 3));
        }
        return deleteItem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T deleteItem = items[getPreIdx(nextLast)];
        items[getPreIdx(nextLast)] = null;
        nextLast = getPreIdx(nextLast);
        size -= 1;
        if (size > MIN_ITEMS_LENGTH && items.length / 3 > size) {
            resize((int) (items.length / 3));
        }
        return deleteItem;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (T t : this) {
            System.out.print(t + " ");
        }
        System.out.println();
    }

    public T get(int index) {
        return items[(nextFirst + index + 1) % items.length];
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

    /**
     * 获得数组下标为 index 的元素的下一个元素的数组下标
     */
    private int getNextIdx(int index) {
        return (index + 1) % items.length;
    }

    /**
     * 获得数组下标为 index 的元素的上一个元素的数组下标
     */
    private int getPreIdx(int index) {
        return ((index - 1) + items.length) % items.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int nowIdx;
        ArrayDequeIterator() {
            nowIdx = nextFirst;
        }
        @Override
        public boolean hasNext() {
            return getNextIdx(nowIdx) != nextLast;
        }

        @Override
        public T next() {
            int nextIdx = getNextIdx(nowIdx);
            nowIdx = nextIdx;
            return items[nextIdx];
        }
    }

}
