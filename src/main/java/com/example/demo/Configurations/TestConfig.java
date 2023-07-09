package com.example.demo.Configurations;

import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class TestConfig {
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            UserEntity u1 = new UserEntity(
                    "u1@email.com",
                    "pass1",
                    "fn1",
                    "ln1",
                    "address 1",
                    "zip1",
                    "m");
            UserEntity u2 = new UserEntity(
                    "u2@email.com",
                    "pass2",
                    "fn2",
                    "ln2",
                    "address 2",
                    "zip2",
                    "f");
            userRepository.saveAllAndFlush(List.of(u1, u2));
        };
    }


}