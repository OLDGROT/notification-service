package org.example.notificationservice.dto;

import lombok.Data;

@Data
public class ServiceConfig {
    private String serviceName;
    private String discoveryUrl;
    private String dbUrl;
}
