package com.example.demo.service.interf;

public interface IService<T, K, D> extends IConvert<D, T>{
    T save(T t);

    T findById(K id);

    void deleteById(K id);

    D findByIdEntity(K id);
}
