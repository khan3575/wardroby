package com.khan.wardroby.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CustomErrorController {
    @PostMapping("access-denied")
    public String accessDenied()
    {
        return "accessDenied";
    }
}
