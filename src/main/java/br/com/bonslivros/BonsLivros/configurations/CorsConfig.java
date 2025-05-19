package br.com.bonslivros.BonsLivros.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Aplica a configuração CORS a todos os caminhos da sua API
                        .allowedOrigins("http://localhost:4200") // Permite pedidos vindos da sua aplicação Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                        .allowedHeaders("*") // Permite todos os cabeçalhos nos pedidos
                        .allowCredentials(true) // Permite o envio de credenciais (como cookies ou tokens de autorização)
                        .maxAge(3600); // Tempo (em segundos) que o resultado de um pedido preflight pode ser cacheado pelo navegador
            }
        };
    }
}
