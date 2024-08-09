package com.abdrabo.librarymanagementsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    /**
     *  Use JDBC Authentication
     * */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     *  Restrict access based on roles : Authorization
     * */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/books/**").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/patrons/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/records").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.POST, "/api/books").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/patrons").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/borrow/{bookId}/patron/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/books/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/patrons/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/return/{bookId}/patron/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/patrons/**").hasRole("ADMIN")
        );

        // Use HTTP Basic Authentication
        http.httpBasic(Customizer.withDefaults());

        // disable Cross-Site Request Forgery CSRF
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
