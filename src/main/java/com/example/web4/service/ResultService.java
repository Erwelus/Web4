package com.example.web4.service;

import com.example.web4.model.Result;
import com.example.web4.model.User;

public interface ResultService {
    boolean check(Double x, Double y, Double r);
    Result prepareResult(Double x, Double y, Double r, User user);
}
