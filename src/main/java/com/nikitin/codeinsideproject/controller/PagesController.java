package com.nikitin.codeinsideproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/homepage")
    public String getHomePage() {
        return "homepage";
    }

}
