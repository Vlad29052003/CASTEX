package com.example.demo.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/default")
public class DefaultController {

    public DefaultController() {

    }
    @GetMapping(path = { "", "/" })
    public String home() {
        return "Hello there!";
    }
}
