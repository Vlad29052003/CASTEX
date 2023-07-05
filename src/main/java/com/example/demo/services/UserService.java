package com.example.demo.services;

import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
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

    public ResponseEntity<List<UserEntity>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<?> signUp(UserEntity userEntity) {
        boolean emailIsUsed = userRepository.findAll().stream().map(u -> u.email)
                .anyMatch(u -> u.equals(userEntity.email));
        if(emailIsUsed) return ResponseEntity.badRequest().body("This email is already in use!");
        userRepository.save(userEntity);
        return ResponseEntity.ok().body("User successfully created!");
    }

    public ResponseEntity<?> logIn(String email, int hashedPassword) {
        Optional<UserEntity> user = userRepository.findAll().stream()
                .filter(u -> u.email.equals(email)).findFirst();
        if(user.isEmpty()) return ResponseEntity.badRequest()
                .body("This email is not associated with any accounts!");
        UserEntity u = user.get();
        if(hashedPassword != u.hashedPassword) return ResponseEntity
                .badRequest().body("Incorrect password!");
        return ResponseEntity.ok(u);
    }

    @Transactional
    public ResponseEntity<?> update(long id, UserEntity userEntity) {
        if(!userRepository.existsById(id) || userEntity.id != id)
            return ResponseEntity.badRequest().body("Invalid id!");
        userRepository.saveAndFlush(userEntity);
        return ResponseEntity.ok("Successfully updated!");
    }

    @Transactional
    public ResponseEntity<?> delete(long id) {
        if(!userRepository.existsById(id)) return ResponseEntity.badRequest().body("Invalid id!");
        userRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted!");
    }
}
