package org.koreait.project.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean // 시큐리티 무력화
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception {
        return http.build();
    }
}
