package com.hsk.mvc.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class MainController {

    @GetMapping
    public String MainControll() {
        return "layout";
    }
    
}
