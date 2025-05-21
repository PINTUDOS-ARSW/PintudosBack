package pintudos.game.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests(authz -> authz
                        // Permite acceso sin autenticación a los endpoints SockJS
                        .requestMatchers(
                                "/game", "/game/**", "/game/info/**"
                        ).permitAll()
                        // Archivos públicos
                        .requestMatchers(
                                "/", "/login/", "/error", "/css/", "/js/"
                        ).permitAll()
                        // Endpoints que requieren autenticación
                        .requestMatchers(
                                "/app/**", "/topic/**"
                        ).authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2Login();

        return http.build();
    }
}
