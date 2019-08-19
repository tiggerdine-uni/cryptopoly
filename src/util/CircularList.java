package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CircularList<T> implements Iterator<T> {

    private List<T> list;
    private int index;

    public CircularList() {
        list = new ArrayList<>();
        index = -1;
    }

    @Override
    public boolean hasNext() {
        return !list.isEmpty();
    }

    @Override
    public T next() {
        tick();
        return list.get(index);
    }

    public boolean add(T e) {
        if (index != -1) {
            return false;
        }
        return list.add(e);
    }

    public boolean shuffle() {
        if (index != -1) {
            return false;
        }
        Collections.shuffle(list);
        return true;
    }

    private void tick() {
        if (index < list.size() - 1) {
            index++;
        } else {
            index = 0;
        }
    }

    public int getIndex() {
        return index;
    }

    public int size() {
        return list.size();
    }

    public T get(int i) {
        return list.get(i);
    }

    public List<T> get() {
        return list;
    }
}
