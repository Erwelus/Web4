package com.example.web4.controllers;

import com.example.web4.dto.ResultDto;
import com.example.web4.model.Result;
import com.example.web4.repository.ResultRepository;
import com.example.web4.service.ResultService;
import com.example.web4.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MainController {
    private final UserService userService;
    private final ResultService resultService;
    private final ResultRepository resultRepository;

    public MainController(UserService userService, ResultService resultService, ResultRepository resultRepository) {
        this.userService = userService;
        this.resultService = resultService;
        this.resultRepository = resultRepository;
    }


    @PostMapping("/")
    public void addResult(@RequestBody ResultDto request){
        Result result = resultService.prepareResult(request.getX(), request.getY(), request.getR(), userService.getCurrentUser());
        resultRepository.save(result);
    }

}
