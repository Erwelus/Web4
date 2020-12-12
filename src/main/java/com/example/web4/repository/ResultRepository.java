package com.example.web4.repository;

import com.example.web4.model.Result;
import com.example.web4.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ResultRepository extends CrudRepository<Result, Long> {

    List<Result> findByOwner(User owner);
}
