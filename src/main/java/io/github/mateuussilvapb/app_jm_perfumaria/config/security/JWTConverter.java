package io.github.mateuussilvapb.app_jm_perfumaria.config.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.Map;

public class JWTConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String CLIENT_ID = "app_segundo";

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Map<String, Collection<String>> resourceAccess = jwt.getClaim("resource_access");
        if (resourceAccess == null) return null;

        Map<String, Collection<String>> client = (Map<String, Collection<String>>) resourceAccess.get(CLIENT_ID);
        if (client == null || client.get("roles") == null) return null;

        var grants = client.get("roles").stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();
        return new JwtAuthenticationToken(jwt, grants);
    }
}