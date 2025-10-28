package org.example.notificationservice;

import org.example.notificationservice.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JavaMailSender mailSender;

    @Test
    void shouldSendCreateEmail() throws Exception {

        String email = "user@test.com";


        mockMvc.perform(post("/api/v1/notifications/send")
                        .param("email", email)
                        .param("type", "create"))
                .andExpect(status().isOk());


        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        SimpleMailMessage sent = captor.getValue();
        assertThat(sent.getTo()).contains(email);
        assertThat(sent.getSubject()).isEqualTo("Создание аккаунта");
        assertThat(sent.getText()).contains("Ваш аккаунт на сайте был успешно создан.");
    }

    @Test
    void shouldSendDeleteEmail() throws Exception {
        String email = "delete@test.com";

        mockMvc.perform(post("/api/v1/notifications/send")
                        .param("email", email)
                        .param("type", "delete"))
                .andExpect(status().isOk());

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(captor.capture());

        SimpleMailMessage sent = captor.getValue();
        assertThat(sent.getSubject()).isEqualTo("Удаление аккаунта");
        assertThat(sent.getText()).contains("Ваш аккаунт был удалён.");
    }

    @Test
    void shouldReturnBadRequestForUnknownType() throws Exception {
        mockMvc.perform(post("/api/v1/notifications/send")
                        .param("email", "x@test.com")
                        .param("type", "banana"))
                .andExpect(status().isBadRequest());
    }
}
