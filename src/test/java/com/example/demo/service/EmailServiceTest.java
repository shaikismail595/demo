package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private EmailService emailService;

    @Test
    void testSendSimpleMessage() {
        String to = "recipient@example.com";
        String subject = "Test Email";
        String text = "This is a test email";
        emailService.sendSimpleMessage(to, subject, text);
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setFrom("noreply@baeldung.com");
        expectedMessage.setTo(to);
        expectedMessage.setSubject(subject);
        expectedMessage.setText(text);

        verify(emailSender).send(expectedMessage);
    }
}