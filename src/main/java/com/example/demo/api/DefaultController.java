package com.example.demo.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DefaultController {

    @GetMapping(path = { "", "/" })
    public String home() {
        return "forward:/index.html";
    }

    @GetMapping("/login")
    public String login() {
        return "forward:/client_login.html";
    }

    @GetMapping("/signup")
    public String signup() {
        return "forward:/client_signup.html";
    }

    @GetMapping("/help")
    public String help() {
        return "forward:/help.html";
    }

    @GetMapping("/cart")
    public String cart() {
        return "forward:/cart.html";
    }
}