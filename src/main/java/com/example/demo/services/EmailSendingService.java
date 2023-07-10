package com.example.demo.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailSendingService {
    private final JavaMailSender mailSender;
    @Value("classpath:static/mailTemplates/verificationEmail.html")
    private Resource verificationMail;

    @Autowired
    public EmailSendingService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String email, String verificationLink) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("castex.no.reply@gmail.com");
        helper.setTo(email);
        helper.setSubject("Verify Email");
        String content = new String(verificationMail.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        content = content.replace("thisIsTheLinkToConfirm", verificationLink);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
