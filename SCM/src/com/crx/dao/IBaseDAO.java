package com.crx.dao;

import java.util.List;

public interface IBaseDAO<T> {
    public void insert(T t);
    public void update(T t);
    public void delete(int id);
    public T findById(int id);
    public List<T> findAll();
    public List<T> findByName(String name);	
    public List<T> findBySQL(String sql);
}
