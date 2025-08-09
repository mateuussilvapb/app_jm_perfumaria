package io.github.mateuussilvapb.app_jm_perfumaria;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

// DTOs for request/response
record TokenRequest(String username,
                    String password,
                    String clientID,
                    String grantType) {}

record TokenResponse(String access_token) {}

public class AuthClient {
    private final WebClient webClient;

    public AuthClient(String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .defaultHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public String obterAccessToken(String username,
                                   String password,
                                   String clientID) {
        TokenRequest body = new TokenRequest(username, password, clientID, "password");

        webClient.post()
            .uri("/token")
            .bodyValue(body)
            .retrieve()
            .bodyToMono(TokenResponse.class)
            .block();

        return null;
    }
}