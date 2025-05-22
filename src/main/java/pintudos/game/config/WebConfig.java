package pintudos.game.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry
          .addMapping("/**")
                .allowedOrigins("http://game.arswpintudos.com:5173", "http://www.arswpintudos.com:3000")
          .allowedMethods("*")
          .allowedHeaders("*")
          .allowCredentials(true); // Habilitar credenciales
      }
    };
  }
}
