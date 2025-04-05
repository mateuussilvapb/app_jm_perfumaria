package io.github.mateuussilvapb.app_jm_perfumaria.config.security;

import io.github.mateuussilvapb.app_jm_perfumaria.infra.usuario.PerfilUsuario;
import io.github.mateuussilvapb.app_jm_perfumaria.infra.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthenticationResolverJwtImpl implements AuthenticationResolver {

    private static final String TOKEN = "token";
    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_PREFERRED_USERNAME = "preferred_username";

    private final JwtAuthenticationToken auth;

    @Override
    public Optional<Usuario> resolve() {
        return jwt(auth.getPrincipal()).map(AuthenticationResolverJwtImpl::usuario);
    }

    private static Optional<Jwt> jwt(Object principal) {
        if (principal instanceof Jwt jwt) {
            return Optional.of(jwt);
        }
        return Optional.empty();
    }

    private static Usuario usuario(Jwt jwt) {
        final var claims = jwt.getClaims();
        return new Usuario(
                String.valueOf(claims.get(CLAIM_PREFERRED_USERNAME)),
                String.valueOf(claims.get(CLAIM_EMAIL)),
                PerfilUsuario.perfiss(ClaimsRolesExtractor.extract(claims)),
                Map.of(TOKEN, jwt.getTokenValue()));
    }
}
