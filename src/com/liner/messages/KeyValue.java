package com.liner.messages;


public class KeyValue<T>{
    public String key;
    public T value;

    public KeyValue(String key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "KeyValue{" +
                "key='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    public boolean isEqual(KeyPair keyPair) {
        return keyPair.key.equals(key);
    }
}