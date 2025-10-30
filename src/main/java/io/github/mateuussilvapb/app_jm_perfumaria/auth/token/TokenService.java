package io.github.mateuussilvapb.app_jm_perfumaria.auth.token;

import io.github.mateuussilvapb.app_jm_perfumaria.auth.dtos.TokenResponse;
import io.github.mateuussilvapb.app_jm_perfumaria.auth.dtos.UserDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TokenService {

    private final WebClient webClient;

    public TokenService(WebClient webClient) {
        this.webClient = webClient;
    }

    public TokenResponse getToken(UserDTO user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var formData = BodyInserters
                .fromFormData("username", user.username())
                .with("password", user.password())
                .with("client_id", user.clientID())
                .with("grant_type", user.grantType());

        return webClient.post()
                .uri("http://keycloak:8080/realms/JMPERFUMARIA/protocol/openid-connect/token")
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .body(formData)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .block();
    }
}
