package com.example.demo.services;

import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<UserEntity>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<?> signUp(UserEntity userEntity) {
        boolean emailIsUsed = userRepository.findAll().stream().map(UserEntity::getEmail)
                .anyMatch(u -> u.equals(userEntity.getEmail()));
        if(emailIsUsed) return ResponseEntity.badRequest().body("This email is already in use!");
        userRepository.save(userEntity);
        return ResponseEntity.ok("User successfully created!");
    }

    public ResponseEntity<?> logIn(String email, String hashedPassword) {
        Optional<UserEntity> user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email)).findFirst();
        if(user.isEmpty()) return ResponseEntity.badRequest()
                .body("This email is not associated with any accounts!");
        UserEntity u = user.get();
        if(!hashedPassword.equals(u.getPassword())) return ResponseEntity
                .badRequest().body("Incorrect password!");
        return ResponseEntity.ok(u);
    }

    @Transactional
    public ResponseEntity<?> update(String email, UserEntity userEntity) {
        if(!userRepository.existsById(email))
            return ResponseEntity.badRequest().body("Invalid id!");
        if(userRepository.existsById(userEntity.getEmail()))
            return ResponseEntity.badRequest().body("The email is already in use!");
        delete(email);
        userRepository.saveAndFlush(userEntity);
        return ResponseEntity.ok("Successfully updated!");
    }

    @Transactional
    public ResponseEntity<?> delete(String email) {
        if(!userRepository.existsById(email)) return ResponseEntity.badRequest().body("Invalid id!");
        userRepository.deleteById(email);
        return ResponseEntity.ok("Successfully deleted!");
    }

    public UserEntity getByEmail (String email) {
        return userRepository.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }
}
