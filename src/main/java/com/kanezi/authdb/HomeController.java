package com.kanezi.authdb;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Controller
public class HomeController {

    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello world!");
        model.addAttribute("date_time", LocalDateTime.now());
        return "index";
    }
}
