package org.example.notificationservice;

import org.example.notificationservice.listner.UserListener;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"user-delete", "user-create"})
@DirtiesContext
class UserListenerIntegrationTest {

    @Autowired
    private UserListener listener;

    @MockitoBean
    private JavaMailSender mailSender;

    @Test
    void shouldHandleUserCreateEvent() {
        // when
        listener.UserHandle("user@test.com", "CREATE");

        // then
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());
        SimpleMailMessage sent = captor.getValue();
        assertThat(sent.getSubject()).isEqualTo("Создание аккаунта");
    }

    @Test
    void shouldHandleUserDeleteEvent() {
        listener.UserHandle("remove@test.com", "DELETE");

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());
        SimpleMailMessage sent = captor.getValue();
        assertThat(sent.getSubject()).isEqualTo("Удаление аккаунта");
    }
}
