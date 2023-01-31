package com.test.openvidu.config;

import io.openvidu.java.client.OpenVidu;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ViduConfig {

    @Bean
    public OpenVidu openVidu() {
        OpenVidu openVidu = new OpenVidu("https://openvidussafy.duckdns.org", "ssafy705");
        return openVidu;
    }
}
