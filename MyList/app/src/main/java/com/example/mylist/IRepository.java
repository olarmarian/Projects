package com.example.mylist;

import java.util.List;

public interface IRepository<ID,T> {
    void add(T elem);
    void update(ID id ,T elem);
    void remove(T elem);
    T findOne(ID id);
    List<T> findAll();
}
