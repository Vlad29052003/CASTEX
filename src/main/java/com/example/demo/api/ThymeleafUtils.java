package com.example.demo.api;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class ThymeleafUtils {

    public String convertToHtmlImage(byte[] imageBytes) {
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        return "<img th:src=\"@{|data:image/png;base64," + base64Image + "|}\" alt=\"Image\">";
    }
}
