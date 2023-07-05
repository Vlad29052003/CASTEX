package com.example.demo;

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
            UserEntity u1 = new UserEntity("User1",
                    "u1@email.com",
                    1111,
                    "fn1",
                    "ln1",
                    Date.valueOf(LocalDate.of(2003, 5, 29)),
                    "address 1",
                    "zip1",
                    "m");
            UserEntity u2 = new UserEntity("User2",
                    "u2@email.com",
                    1111,
                    "fn2",
                    "ln2",
                    Date.valueOf(LocalDate.of(2003, 5, 29)),
                    "address 2",
                    "zip2",
                    "f");
            userRepository.saveAllAndFlush(List.of(u1, u2));
        };
    }


}
