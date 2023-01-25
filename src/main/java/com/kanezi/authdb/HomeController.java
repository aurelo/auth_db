package com.kanezi.authdb;

import com.kanezi.authdb.fomanticUI.Toast;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class HomeController {

    @GetMapping("/")
    public String helloWorld(Model model) {
        model.addAttribute("message", "Hello world!");
        model.addAttribute("date_time", LocalDateTime.now());
        return "index";
    }


    @GetMapping("/toast")
    public String showToast() {
        return "toast";
    }

    @PostMapping("/toast")
    public String toast(RedirectAttributes attributes) {
        throw new IllegalArgumentException("exception showcase");

        //attributes.addFlashAttribute("toast", Toast.success("Hello", "Hi from toast!"));
//        return "redirect:/toast";
    }
}
