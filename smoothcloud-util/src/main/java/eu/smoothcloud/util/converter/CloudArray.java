package eu.smoothcloud.util.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CloudArray<T> {
    private final T[] array;

    public CloudArray(T[] array) {
        this.array = array;
    }

    public List<T> convertToList() {
        return new ArrayList<>(Arrays.asList(array));
    }
}
