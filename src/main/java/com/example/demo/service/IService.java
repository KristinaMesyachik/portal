package com.example.demo.service;

public interface IService<T, K> {
    T save(T t);
    T findById(K id);
    void deleteById(K id);
}
