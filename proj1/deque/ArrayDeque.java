package deque;

import afu.org.checkerframework.checker.oigj.qual.O;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T> {
    /**
     * 由于总是在首尾添加和删除元素，不用考虑中间元素删除的情况
     */

    private int nextFirst;
    private int nextLast;
    private T[] items = (T[]) new Object[8];
    private int size;

    private double usage;

    public ArrayDeque() {
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    public void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int i = nextFirst % items.length;
        while (i < nextLast) {
            i %= a.length;
            a[i] = items[i];
            i ++;
        }
        usage = size % items.length;
        items = a;
    }

    public void addFirst(T x) {
        if (size == items.length - 2) {
            resize((int)(size * 1.01));
        }
        items[nextFirst] = x;
        nextFirst = getPreIdx(nextFirst);
        size += 1;
    }

    public void addLast(T x) {
        if (size == items.length - 2) {
            resize((int)(size * 1.01));
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

        if (items.length / size >= 4 && items.length > 16) {
            resize((int)(items.length / 4));
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
        if (size > 16 && items.length / size >= 4) {
            resize((int)(items.length / 4));
        }
        return deleteItem;
    }

    public boolean isEmpty() {
        return size == 0;
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
        return (index - 1) % items.length;
    }

    public double getUsage() {
        return usage;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int nowIdx;
        public ArrayDequeIterator() {
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
