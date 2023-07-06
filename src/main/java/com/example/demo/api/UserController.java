package com.example.demo.api;

import com.example.demo.authentication.AuthenticationRequest;
import com.example.demo.authentication.AuthenticationResponse;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import javax.naming.AuthenticationException;
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

    @PostMapping("/logIn")
    public ResponseEntity<?> logIn(@RequestBody AuthenticationRequest request) {
        try {
            return userService.logIn(request);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials!");
        }
    }

    @PutMapping("/{email}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable("email") String email,
                                    @RequestBody UserEntity userEntity) {
        return userService.update(email, userEntity);
    }

    @DeleteMapping("/{email}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable("email") String email) {
        return userService.delete(email);
    }
}
