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
      .disable() // Necesario para SockJS
      .authorizeHttpRequests(authz ->
        authz
          .requestMatchers(
            "/game/**", // SockJS handshake y WebSocket transport
            "/ws/**", // Si usas /ws como endpoint de registro STOMP
            "/topic/**", // Canal de suscripciones
            "/app/**" // Canal de envío desde el cliente
          )
          .permitAll()
          .anyRequest()
          .authenticated() // El resto necesita auth
      )
      .formLogin()
      .disable()
      .httpBasic()
      .disable();

    return http.build();
  }
}
