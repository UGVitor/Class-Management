package com.kvy.demogerenciamentoaulas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("test")
public class TestSecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(TestSecurityConfig.class);

    @Bean
    public SecurityFilterChain testFilterChain(HttpSecurity http) throws Exception {
        logger.info("TestSecurityConfig ativo: todas as requisições estão permitidas.");
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )
                .build();
    }
}