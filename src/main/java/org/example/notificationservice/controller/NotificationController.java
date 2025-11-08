package org.example.notificationservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.notificationservice.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
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
