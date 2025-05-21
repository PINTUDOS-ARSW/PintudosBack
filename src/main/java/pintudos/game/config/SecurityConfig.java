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
      .disable() // Recomendado si usas WebSockets o APIs sin cookies
      .authorizeHttpRequests(authz -> authz
          .requestMatchers(
              "/", "/login/**", "/error", "/css/**", "/js/**"
          ).permitAll() // Archivos públicos
          .requestMatchers(
              "/ws/**", "/game/**", "/app/**", "/topic/**"
          ).authenticated() // Requieren login
          .anyRequest().authenticated()
      )
      .oauth2Login(); // Habilita login con Google

    return http.build();
  }
}
