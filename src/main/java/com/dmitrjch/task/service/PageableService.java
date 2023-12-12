package com.dmitrjch.task.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PageableService<T> {
    Page<T> findAll(Pageable pageable);

}