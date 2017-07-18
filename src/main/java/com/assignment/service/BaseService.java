package com.assignment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by manish on 18/07/17.
 */
public interface BaseService<T, S> {

    T save(T obj);
    T update(T obj, S id);
    void delete(S id);
    T findOne(S id);
    Page<T> listAllByPage(Pageable pageable);
}
