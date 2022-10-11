package com.example.demo.service.interf;

public interface IConvert<T, D> {
    D convertEntityToDto(T t);

    T convertDtoToEntity(D d);
}
