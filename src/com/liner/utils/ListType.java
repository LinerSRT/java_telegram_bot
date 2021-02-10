package com.liner.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ListType<T> implements ParameterizedType {
    private final Class<?> clazz;

    public ListType(Class<T> wrapper) {
        this.clazz = wrapper;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{clazz};
    }

    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}