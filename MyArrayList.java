package assignment3;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<T extends Serializable> implements Serializable, Iterable<T> {
    public class MyArrayListIterator implements Iterator<T> {
        transient private int position = 0;


        @Override
        public boolean hasNext() {
            if (arr != null) { // only not null allowed to return true if they're not null .
                try {
                    if (arr.get(position) == null) return false;
                } catch (Exception e) {
                    return false;
                }
                return position < arr.size() && arr.get(position) != null;
            }
            return false;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException("No more elements");
            T nextElement = arr.get(position); // next element
            position += 1;
            return nextElement;

        }
    }

    private SimpleArray<T> arr;

    public SimpleArray<T> getUnderlyingSimpleArray() {
        return this.arr;
    }

    public int capacity() {
        return this.arr.length();
    }

    public MyArrayList() {
        this.arr = new SimpleArray<>(1);
    }

    public MyArrayList(T[] arr) {
        if (arr != null) this.arr = new SimpleArray<>(arr);
    }

    public T get(int index) {
        if (arr==null||arr.size() - 1 < index || index < 0||arr.get(index)==null) throw new IndexOutOfBoundsException();
        return arr.get(index);
    }

    public int size() {
        if (arr==null) return 0;
        int result = 0;
        for (T element : arr) {
            if (element != null) {
                result += 1;
            }
        }
        return result;
    }

    public void add(T element) {
        int i = 0; // start from -1 cause were adding 1 first in loop.
        if (arr.get(0) == null) {
            arr.set(0, element);
            return; // Finish here.
        }
        for (T arrElement : arr) {
            if (arrElement == null) {
                arr.set(i, element);
                return; // Finish here.
            }
            i++; // updating index for next iteration.
        }
        //make it bigger multiplying by 2
        SimpleArray<T> tempArr = new SimpleArray<>(arr.length() * 2);

        i = 0;
        for (T t : arr) {
            if (t != null) {
                tempArr.set(i, t);
                i++;
            }
        }
        tempArr.set(i, element);
        this.arr = tempArr;
    }

    public void remove(T element) {
        boolean isDeleted = false;
        for (int i = 0; i < arr.length(); i++) {
            if (element == arr.get(i)) {
                arr.set(i, null);
                isDeleted = true;
                continue;
            }
            if (isDeleted) {    // move all nonull elements one index left.
                if (arr.get(i) == null) return;
                arr.set(i - 1, arr.get(i));
                arr.set(i, null);
            }
        }
        if(!isDeleted)
            // if element notfound it will throw it .
            throw new NoSuchElementException();
    }

    public int index(T element) {
        for (int i = 0; i < arr.length(); i++) {
            if (element == arr.get(i)) return i;
        }
        return -1;  // NOT FOUND !
    }

    public int count(T element) {
        int counter = 0;
        for (T arrElement : arr) {
            if (arrElement == element || element.equals(arrElement)) {
                counter++;
            }
        }
        return counter;
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator();
    }
}
