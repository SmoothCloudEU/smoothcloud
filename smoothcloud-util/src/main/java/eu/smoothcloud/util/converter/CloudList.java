package eu.smoothcloud.util.converter;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Consumer;

public class CloudList<T> {
    private T[] elements;
    private final Class<T> type;

    @SafeVarargs
    public CloudList(Class<T> type, T... elements) {
        this.type = type;
        this.elements = elements;
    }

    @SuppressWarnings("unchecked")
    public T[] convertToArray() {
        T[] array = (T[]) Array.newInstance(type, elements.length);
        System.arraycopy(elements, 0, array, 0, elements.length);
        return array;
    }

    public void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action, "Action must not be null");
        for (T element : elements) {
            action.accept(element);
        }
    }

    public void add(T element) {
        elements = Arrays.copyOf(elements, elements.length + 1);
        elements[elements.length - 1] = element;
    }

    public boolean remove(T element) {
        List<T> list = new ArrayList<>(Arrays.asList(elements));
        boolean removed = list.remove(element);
        if (removed) {
            elements = list.toArray(Arrays.copyOf(elements, list.size()));
        }
        return removed;
    }

    public int size() {
        return elements.length;
    }

    public T get(int index) {
        if (index < 0 || index >= elements.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + elements.length);
        }
        return elements[index];
    }

    public boolean contains(T element) {
        for (T e : elements) {
            if (Objects.equals(e, element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}
