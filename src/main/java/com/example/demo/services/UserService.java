package com.example.demo.services;

import com.example.demo.authentication.AuthenticationRequest;
import com.example.demo.authentication.AuthenticationResponse;
import com.example.demo.configurations.RSAUtils;
import com.example.demo.entities.UserEntity;
import com.example.demo.entities.enums.Authority;
import com.example.demo.entities.enums.Gender;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final EmailSendingService emailSendingService;
    private final EmailVerificationService emailVerificationService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, JwtService jwtService, EmailSendingService emailSendingService, EmailVerificationService emailVerificationService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.emailSendingService = emailSendingService;
        this.emailVerificationService = emailVerificationService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        userRepository.saveAndFlush(new UserEntity("@.", passwordEncoder.encode("Parola123@"), "fn", "ln","address", "zip", Gender.M, Authority.USER,true));
    }

    public ResponseEntity<List<UserEntity>> getAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    public ResponseEntity<?> signUp(String domain, UserEntity userEntity) throws Exception {
        boolean emailIsUsed = userRepository.findAll().stream().map(UserEntity::getEmail).anyMatch(u -> u.equals(userEntity.getEmail()));
        if (emailIsUsed) return ResponseEntity.badRequest().body("This email is already in use!");
        byte[] decoded = Base64.getDecoder().decode(userEntity.getPassword());
        String decryptedPassword = RSAUtils.decrypt(decoded).trim().replace("\"", "");
        userEntity.setPassword(passwordEncoder.encode(decryptedPassword));
        userRepository.save(userEntity);
        String uuid = emailVerificationService.generateId(userEntity.getEmail());
        String verificationLink = domain + "/verifyEmail/" + uuid;
        emailSendingService.sendVerificationEmail(userEntity.getEmail(), verificationLink);
        return ResponseEntity.ok("User successfully created!");
    }

    public ResponseEntity<AuthenticationResponse> logIn(AuthenticationRequest request) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(request.getPassword());
        String decryptedPassword = RSAUtils.decrypt(decoded).trim().replace("\"", "");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), decryptedPassword));

        UserEntity user = userRepository.findById(request.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Invalid email!"));
        String token = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @Transactional
    public ResponseEntity<?> update(String email, UserEntity userEntity) {
        if (!userRepository.existsById(email)) return ResponseEntity.badRequest().body("Invalid id!");
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
        return userRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findById(username).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
    }

    //todo log out
    public ResponseEntity<?> logOut(String token) {
        return null;
    }

    private void saveUserToken(UserEntity user, String jwtToken) {
        jwtService.saveToken(jwtToken, user);
    }

    private void revokeAllUserTokens(UserEntity user) {
        var validUserTokens = jwtService.findAllValidTokenByUser(user.getEmail());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(token -> {
            token.setRevoked(true);
        });
        jwtService.saveAll(validUserTokens);
    }

    public boolean verifyEmail(String id) {
        String email = emailVerificationService.verifyEmail(id);
        if(email != null) {
            UserEntity userEntity = userRepository.findById(email).get();
            userEntity.setVerifiedEmail(true);
            userRepository.saveAndFlush(userEntity);
            return true;
        }
        return false;
    }
}
