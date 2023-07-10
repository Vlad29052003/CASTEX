package com.example.demo.api;

import com.example.demo.configurations.RSAUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping(path = { "", "/" })
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "client_login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "client_signup";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart";
    }

    @GetMapping("/account-created")
    public String accountCreated() {
        return "account_created";
    }

    @GetMapping("/get-public-key")
    public ResponseEntity<String> getPublicKey() {
        return ResponseEntity.ok(RSAUtils.getPublicKey());
    }
}