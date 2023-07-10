package com.example.demo.api;

import com.example.demo.configurations.RSAUtils;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DefaultController {
    private final UserService userService;

    @Autowired
    public DefaultController(UserService userService) {
        this.userService = userService;
    }

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

    @GetMapping("/verifyEmail/{id}")
    public String verifyEmail(@PathVariable("id") String id, ModelMap model) {
        userService.verifyEmail(id);
        return "email_verified";
    }
}