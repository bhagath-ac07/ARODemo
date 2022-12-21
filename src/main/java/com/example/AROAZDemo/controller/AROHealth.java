package com.example.AROAZDemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AROHealth {

    @GetMapping("/health")
    public String showHealth() {
        return "OK";
    }
}
