package io.github.mateuussilvapb.app_jm_perfumaria.config.persistence;

import io.github.mateuussilvapb.app_jm_perfumaria.config.security.AuthenticationResolverFacade;
import io.github.mateuussilvapb.app_jm_perfumaria.domain.usuario.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
public class PersistenceConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        return () -> AuthenticationResolverFacade.resolve().map(Usuario::login);
    }
}
