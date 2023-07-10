package com.example.demo.services;

import com.example.demo.entities.EmailVerification;
import com.example.demo.repositories.EmailVerificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailVerificationService {
    private final EmailVerificationRepository emailRepository;

    @Autowired
    public EmailVerificationService(EmailVerificationRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public String generateId(String email) {
        EmailVerification emailVerification = new EmailVerification(email);
        EmailVerification saved = emailRepository.saveAndFlush(emailVerification);
        return saved.getId().toString();
    }

    public String verifyEmail(String uuid) {
        Optional<EmailVerification> optionalEmailVerification = emailRepository.findAll().stream().filter(e -> e.getId().toString().equals(uuid)).findFirst();
        if(optionalEmailVerification.isEmpty()) return null;
        EmailVerification emailVerification = optionalEmailVerification.get();
        emailRepository.delete(emailVerification);
        return emailVerification.getEmail();
    }
}
