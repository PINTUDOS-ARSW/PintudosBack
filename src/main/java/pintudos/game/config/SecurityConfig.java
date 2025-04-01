package pintudos.game.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
    throws Exception {
    // Desactivamos la seguridad para permitir el acceso sin autenticación
    http
      .csrf(csrf -> csrf.disable()) // Desactivar CSRF
      .authorizeHttpRequests(requests ->
        requests
          .requestMatchers("/**")
          .permitAll() // Permitir acceso a todas las rutas sin autenticación
          .anyRequest()
          .authenticated()
      ); // Las demás rutas requieren autenticación
    return http.build();
  }
}
