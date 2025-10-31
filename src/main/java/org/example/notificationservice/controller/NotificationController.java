package org.example.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.notificationservice.exception.UnknownTypeException;
import org.example.notificationservice.service.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final EmailService emailService;

    @PostMapping("/send")
    public void sendEmail(
            @RequestParam String email,
            @RequestParam String subject,
            @RequestParam String text

    ) {
        emailService.sendEmail(email, subject, text);
    }
}
