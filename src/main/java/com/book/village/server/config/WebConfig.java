package com.book.village.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true) // 인가로 사용자 인증도 서비스
                        .allowedOriginPatterns("https://bookvillage.kro.kr")
                        .allowedOriginPatterns("*")
                        .allowedHeaders("*")    // 어떤 헤더들도 허용
                        .allowedMethods("GET", "POST", "DELETE", "PATCH")
                        .exposedHeaders("Authorization", "RefreshToken");
            }
        };
    }
}
