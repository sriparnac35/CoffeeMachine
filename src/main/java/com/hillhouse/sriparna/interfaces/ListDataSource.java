package com.hillhouse.sriparna.interfaces;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import java.util.List;

public interface ListDataSource<T> {
    int size();
    void add(T data);
    T getByIndex(int index);
    List<T> getAll();
}
