package org.example.notificationservice.listner;

import lombok.RequiredArgsConstructor;
import org.example.notificationservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserListener {
    private final EmailService emailService;

    @KafkaListener(topics = "user-delete", groupId = "notification-service")
    public void handleUserDelete(String email) {
        emailService.sendEmail(email, "Удаление аккаунта", "Здравствуйте! Ваш аккаунт был удалён.");
    }

    @KafkaListener(topics = "user-create", groupId = "notification-service")
    public void handleUserCreate(String email) {
        emailService.sendEmail(email, "Создание аккаунта", "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
    }
}
