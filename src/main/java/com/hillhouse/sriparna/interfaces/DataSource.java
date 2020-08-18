package com.hillhouse.sriparna.interfaces;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

public interface DataSource<K, V> {
    V getValueByKey(K key);
    void addOrUpdate(K key, V value);
    void add(K key, V value);
    void update(K key, V value);
    boolean exists(K key);
    void delete(K key);
}
