package com.service;

import java.sql.SQLException;
import java.util.List;

public interface CrudOperations<T> {
    List<T> findAll();
    List<T> saveAll(List<T> toSave);
    T save(T toSave) throws SQLException;
    T delete(T toDelete);
}

