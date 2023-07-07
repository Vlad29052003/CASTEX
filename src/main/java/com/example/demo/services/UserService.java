package com.example.demo.services;

import com.example.demo.authentication.AuthenticationRequest;
import com.example.demo.authentication.AuthenticationResponse;
import com.example.demo.entities.UserEntity;
import com.example.demo.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(UserRepository userRepository,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<List<UserEntity>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<?> signUp(UserEntity userEntity) {
        boolean emailIsUsed = userRepository.findAll().stream().map(UserEntity::getEmail)
                .anyMatch(u -> u.equals(userEntity.getEmail()));
        if (emailIsUsed) return ResponseEntity.badRequest().body("This email is already in use!");
        userRepository.save(userEntity);
        return ResponseEntity.ok("User successfully created!");
    }

    public ResponseEntity<AuthenticationResponse> logIn(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        UserEntity user = userRepository.findById(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Invalid email!"));
        String token = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @Transactional
    public ResponseEntity<?> update(String email, UserEntity userEntity) {
        if (!userRepository.existsById(email))
            return ResponseEntity.badRequest().body("Invalid id!");
        if (userRepository.existsById(userEntity.getEmail()))
            return ResponseEntity.badRequest().body("The email is already in use!");
        delete(email);
        userRepository.saveAndFlush(userEntity);
        return ResponseEntity.ok("Successfully updated!");
    }

    @Transactional
    public ResponseEntity<?> delete(String email) {
        if (!userRepository.existsById(email)) return ResponseEntity.badRequest().body("Invalid id!");
        userRepository.deleteById(email);
        return ResponseEntity.ok("Successfully deleted!");
    }

    public UserEntity getByEmail(String email) {
        return userRepository.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }


    public ResponseEntity<?> logOut(String token) {
        return null;
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        jwtService.saveToken(jwtToken, user);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = jwtService.findAllValidTokenByUser(user.getEmail());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        jwtService.saveAll(validUserTokens);
    }
}
