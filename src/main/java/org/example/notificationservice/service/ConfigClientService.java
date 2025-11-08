package org.example.notificationservice.service;

import org.example.notificationservice.dto.ServiceConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ConfigClientService {

    private final WebClient webClient = WebClient.create("http://config-service:8085");

    public ServiceConfig fetchConfig(String serviceName) {
        return webClient.get()
                .uri("/config/" + serviceName)
                .retrieve()
                .bodyToMono(ServiceConfig.class)
                .block();
    }
}
