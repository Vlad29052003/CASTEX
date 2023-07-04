package com.example.demo.api;

import com.example.demo.entities.UserEntity;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = {"", "/"})
    public ResponseEntity<List<UserEntity>> getAll() {
        return userService.getAll();
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserEntity userEntity) {
        return userService.signUp(userEntity);
    }

    @PostMapping("/logIn/{email}")
    public ResponseEntity<?> signUp(@PathVariable("email") String email,
                                    @RequestBody int hashedPassword) {
        return userService.logIn(email, hashedPassword);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable("id") long id,
                                    @RequestBody UserEntity userEntity) {
        return userService.update(id, userEntity);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return userService.delete(id);
    }
}
