package com.example.demo.services;

import com.example.demo.repositories.UserRepository;
import entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<?> signUp(User user) {
        boolean usernameIsTaken = userRepository.findAll()
                .stream().map(u -> u.username).anyMatch(u -> u.equals(user.username));
        if(usernameIsTaken) return ResponseEntity.badRequest()
                .body("This username already exists!");
        boolean emailIsUsed = userRepository.findAll().stream().map(u -> u.email)
                .anyMatch(u -> u.equals(user.email));
        if(emailIsUsed) return ResponseEntity.badRequest().body("This email is already in use!");
        userRepository.save(user);
        return ResponseEntity.ok().body("User successfully created!");
    }

    public ResponseEntity<?> logIn(String email, int hashedPassword) {
        Optional<User> user = userRepository.findAll().stream()
                .filter(u -> u.email.equals(email)).findFirst();
        if(user.isEmpty()) return ResponseEntity.badRequest()
                .body("This email is not associated with any accounts!");
        User u = user.get();
        if(hashedPassword != u.hashedPassword) return ResponseEntity
                .badRequest().body("Incorrect password!");
        return ResponseEntity.ok(u);
    }

    @Transactional
    public ResponseEntity<?> update(long id, User user) {
        if(!userRepository.existsById(id) || user.id != id)
            return ResponseEntity.badRequest().body("Invalid id!");
        userRepository.saveAndFlush(user);
        return ResponseEntity.ok("Successfully updated!");
    }

    @Transactional
    public ResponseEntity<?> delete(long id) {
        if(!userRepository.existsById(id)) return ResponseEntity.badRequest().body("Invalid id!");
        userRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
