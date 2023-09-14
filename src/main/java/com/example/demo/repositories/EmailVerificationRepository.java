package com.example.demo.repositories;


import com.example.demo.entities.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification, UUID> {
}
