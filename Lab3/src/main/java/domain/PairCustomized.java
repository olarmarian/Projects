package domain;

public class PairCustomized<K,V> {
    private K key;
    private V value;

    public PairCustomized(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return  value.toString();
    }

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
