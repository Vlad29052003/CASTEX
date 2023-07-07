package com.example.demo.configurations;

import org.springframework.security.crypto.password.PasswordEncoder;

public class SkipPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString(); // Return the password as is
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword); // Compare the passwords directly
    }
}
