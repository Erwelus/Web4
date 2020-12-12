package com.example.web4.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/application/")
    public String getMain(){
        return "forward:/index.html";
    }
}
