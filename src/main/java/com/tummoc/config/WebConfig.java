package com.tummoc.config;

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
                registry.addMapping("/**")
                       .allowedOrigins("http://localhost:63342", "https://tummoc-production.up.railway.appmhjn")
                       .allowedMethods("*")
                       .allowedHeaders("*")
                       .allowCredentials(true);
            }
        };
    }
}
