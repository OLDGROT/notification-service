package org.example.notificationservice.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.notificationservice.dto.ServiceConfig;
import org.example.notificationservice.service.ConfigClientService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfigInitializer {

    private final ConfigClientService configClientService;

    @PostConstruct
    public void init() {
        ServiceConfig config = configClientService.fetchConfig("notification-service");

    }
}
