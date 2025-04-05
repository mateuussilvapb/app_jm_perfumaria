package io.github.mateuussilvapb.app_jm_perfumaria.auth;

import io.github.mateuussilvapb.app_jm_perfumaria.auth.dtos.TokenResponse;
import io.github.mateuussilvapb.app_jm_perfumaria.auth.dtos.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/token")
public class TokenController {

    private final TokenService tokenService;

    public TokenController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    public TokenResponse token(@RequestBody UserDTO user) {
        return tokenService.getToken(user);
    }
}

