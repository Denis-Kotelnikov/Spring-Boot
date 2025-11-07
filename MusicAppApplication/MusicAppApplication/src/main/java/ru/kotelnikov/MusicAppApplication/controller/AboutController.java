package ru.kotelnikov.MusicAppApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    public String showAboutPage() {
        System.out.println("Обрабатывается запрос /about");
        return "about.html";
    }
}
