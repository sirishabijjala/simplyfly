package com.wipro.simplyfly.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    // ✅ ADD THIS PASSWORD ENCODER BEAN
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // Disable CSRF for development
            .csrf(csrf -> csrf.disable())

            // Authorize requests
            .authorizeHttpRequests(auth -> auth

                // Allow access to static files
                .requestMatchers(
                        "/",
                        "/login",
                        "/register",
                        "/user/**",
                        "/css/**",
                        "/js/**",
                        "/images/**"
                ).permitAll()

                // Any other request requires authentication
                .anyRequest().authenticated()
            )

            // Custom login configuration
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/user/dashboard.html", true)
                    .permitAll()
            )

            // Logout configuration
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
            );

        return http.build();
    }
}