package com.springboot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/eco")
public class EcoController {

    @GetMapping
    public String ecoController() {
        return "Hello world";
    }
}
