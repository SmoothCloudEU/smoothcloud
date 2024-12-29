package eu.smoothcloud.launcher.util;

public class Pair<KEY, VALUE> {
    private KEY key;
    private VALUE value;

    public Pair() {

    }

    public Pair(KEY key, VALUE value) {
        this.key = key;
        this.value = value;
    }

    public KEY getKey() {
        return key;
    }

    public VALUE getValue() {
        return value;
    }

    public void setKey(KEY key) {
        this.key = key;
    }

    public void setValue(VALUE value) {
        this.value = value;
    }
}
