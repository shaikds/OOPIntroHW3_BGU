package assignment3;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MySet<T extends Serializable> extends MyArrayList<T> {

    public MySet() {
        super();
    }

    public MySet(Iterable<? extends T> collection) {
        super();
        for (T collectionElement : collection) {
            add(collectionElement);
        }

    }
    public MySet(T[] arr) {
        super();
        for (T t : arr) {
            add(t);
        }    }

    public void add(T element) {
        if (!contains(element)) super.add(element); //if element no exists -> add it.
    }

    public boolean contains(T element) {
        for (T arrElement : this) { // Duplicates check
            if (arrElement == null) return false; //Null check
            if (element==arrElement || arrElement.equals(element)) {
                return true; // No need to add this element
            }
        }
        return false;
    }

    public int size() {
        return super.size();
    }

    public void remove(T element) {
        if (element != null) {
            if (contains(element)) super.remove(element);
        }

    }

    public boolean equals(MySet<T> other) {
        if (this.size() != other.size()) {
            return false;
        }
        for (T element : this) {
            if (!other.contains(element)) {
                return false;
            }
        }
        return true;
    }

    public MySet<T> union(MySet<T> other) {
        MySet<T> union = new MySet<>();

        // Add elements from array1
        for (T element : this) {
            if (!union.contains(element)) {
                union.add(element);
            }
        }

        // Add elements from array2
        for (T element : other) {
            if (!union.contains(element)) {
                union.add(element);
            }
        }

        // Convert unionList back to an array
        return union;
    }

    public MySet<T> intersection(MySet<T> other) {
        MySet<T> intersection = new MySet<>();
        for (T element : this) {
            if (other.contains(element)) {
                if (!intersection.contains(element))
                    intersection.add(element); // add only elements that is not already in the intersection .
            }
        }
        return intersection;
    }

    public MySet<T> difference(MySet<T> other) {
        MySet<T> difference = new MySet<>();
        // Add elements from the first arr.
        for (T element : this) {
            if (!difference.contains(element)) {
                difference.add(element);
            }
        }
        // Remove all the elements that are in difference && other.
        for (T element : other) {
            if (difference.contains(element)) difference.remove(element);
        }

        return difference;
    }

    @Override
    public Iterator<T> iterator() {
        return super.iterator();
    }
}




