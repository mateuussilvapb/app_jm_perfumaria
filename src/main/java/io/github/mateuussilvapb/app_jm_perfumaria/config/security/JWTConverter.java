package io.github.mateuussilvapb.app_jm_perfumaria.config.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JWTConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private static final String CLIENT_ID = "app_jm_perfumaria"; // Use o mesmo ID do client no Keycloak

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        Collection<SimpleGrantedAuthority> authorities = extractAuthorities(jwt);
        return new JwtAuthenticationToken(jwt, authorities);
    }

    @SuppressWarnings("unchecked")
    private Collection<SimpleGrantedAuthority> extractAuthorities(Jwt jwt) {
        try {
            Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess == null) return List.of();

            Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(CLIENT_ID);
            if (clientAccess == null) return List.of();

            Collection<String> roles = (Collection<String>) clientAccess.get("roles");
            if (roles == null) return List.of();

            return roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Log o erro
            return List.of();
        }
    }
}