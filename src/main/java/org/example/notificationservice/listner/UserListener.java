package org.example.notificationservice.listner;


import lombok.RequiredArgsConstructor;
import org.example.notificationservice.service.EmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserListener {
    private final EmailService emailService;

    @KafkaListener(topics = "user-events", groupId = "notification-service")
    public void UserHandle(@Header(KafkaHeaders.RECEIVED_KEY) String email, String msg) {
        switch (msg){
            case "CREATE"->
                    emailService.sendEmail(email, "Создание аккаунта", "Здравствуйте! Ваш аккаунт на сайте ваш сайт был успешно создан.");
            case "DELETE" ->
                    emailService.sendEmail(email, "Удаление аккаунта", "Здравствуйте! Ваш аккаунт был удалён.");
        }

    }

}
