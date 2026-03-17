package org.example.exercice15;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("custom")
                .maxConnections(50)
                .pendingAcquireMaxCount(10)
                .build();

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create(connectionProvider)))
                .build();
    }
}

// http://localhost:8080/api/tasks/stream
// http://localhost:8080/actuator/metrics/http.server.requests
