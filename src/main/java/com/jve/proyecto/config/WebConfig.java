package com.jve.proyecto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Permite solicitudes a todas las rutas que empiezan con /api/
            .allowedOrigins("http://localhost:4200") // Permite solicitudes solo desde el frontend en localhost:4200
            .allowedMethods("GET", "POST", "PUT", "DELETE") // Métodos permitidos
            .allowedHeaders("*") // Permite todos los headers
            .allowCredentials(true);
    }
}
