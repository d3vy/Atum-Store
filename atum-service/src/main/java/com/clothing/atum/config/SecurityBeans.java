package com.clothing.atum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(request -> request
                        .requestMatchers(HttpMethod.GET)
                        .hasAuthority("SCOPE_view_catalogue")
                        .requestMatchers(HttpMethod.POST, "/atum-api/products")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.PATCH, "/atum-api/products/{productId:\\d+}")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .requestMatchers(HttpMethod.DELETE, "/atum-api/products/{productId:\\d+}")
                        .hasAuthority("SCOPE_edit_catalogue")
                        .anyRequest().permitAll())
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults()))
                .build();
    }
}
