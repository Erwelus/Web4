package com.example.web4.service;

import com.example.web4.model.Result;
import com.example.web4.model.User;
import com.example.web4.repository.ResultRepository;

import java.util.List;
import java.util.StringJoiner;

public interface ResultService {
    boolean check(Double x, Double y, Double r);
    Result prepareResult(Double x, Double y, Double r, User user);
    List<Result> getAllForOwner(User owner);
    String prepareToPrimeReact(List<Result> results);
}
