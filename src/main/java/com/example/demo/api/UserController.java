package com.example.demo.api;

import com.example.demo.authentication.AuthenticationRequest;
import com.example.demo.authentication.RegistrationRequest;
import com.example.demo.entities.UserEntity;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
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
    public ResponseEntity<?> signUp(@RequestBody RegistrationRequest request) throws Exception {
        System.out.println(request);
        return userService.signUp(request.getDomainName(), request.getUser());
    }

    @PostMapping("/logIn")
    public ResponseEntity<?> logIn(@RequestBody AuthenticationRequest request) {
        try {
            return userService.logIn(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials!");
        }
    }

    @PostMapping("/logOut")
    public ResponseEntity<?> logOut(@RequestBody String token) {
        try {
            return userService.logOut(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token!");
        }
    }

    @PutMapping("/{token}")
    @Transactional
    public ResponseEntity<?> update(
            @PathVariable("token") String token,
            @RequestBody UserEntity userEntity) {
        return userService.update(token, userEntity);
    }

    @DeleteMapping(path = {"", "/"})
    @Transactional
    public ResponseEntity<?> delete(@RequestBody String token) {
        return userService.delete(token);
    }
}
