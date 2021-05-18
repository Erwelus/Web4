package com.example.web4.repository;

import com.example.web4.model.Result;
import com.example.web4.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResultRepository extends CrudRepository<Result, Long> {

    List<Result> findByOwner(User owner);
    @Transactional
    List<Result> deleteByOwner(User owner);
}
