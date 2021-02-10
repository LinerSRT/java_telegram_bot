package com.liner.messages;

public class KeyPair<T>{
    public String key;
    public Class<T> tClass;

    public KeyPair(String key, Class<T> tClass) {
        this.key = key;
        this.tClass = tClass;
    }

    @Override
    public String toString() {
        return "KeyPair{" +
                "key='" + key + '\'' +
                ", tClass=" + tClass +
                '}';
    }

    public boolean isEqual(KeyValue keyValue) {
        return key.equals(keyValue.key);
    }
}