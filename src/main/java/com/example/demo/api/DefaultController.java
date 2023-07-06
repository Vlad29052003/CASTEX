package com.example.demo.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping(path = { "", "/" })
    public String home() {
        return "forward:/index.html";
    }

    @GetMapping(path = { "/login" })
    public String login() {
        return "forward:/client_login.html";
    }

    @GetMapping(path = { "/signup" })
    public String signup() {
        return "forward:/client_signup.html";
    }

    @GetMapping(path = { "/help" })
    public String help() {
        return "forward:/help.html";
    }

    @GetMapping(path = { "/cart" })
    public String cart() {
        return "forward:/cart.html";
    }
}