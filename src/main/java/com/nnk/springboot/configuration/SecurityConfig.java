package com.nnk.springboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authz -> authz
                //.requestMatchers("/login", "register").permitAll()
                .requestMatchers("/login", "/register", "/css/**", "/images/**", "/js/**").permitAll()
                .requestMatchers("user/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        )
                .formLogin(form -> form
                        .loginPage("/app/login")
                        .loginProcessingUrl("/app/perform-login")
                        .defaultSuccessUrl("/bid/list", true)
                        .failureUrl("/app/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/app/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                )
                .csrf(csrf -> csrf.disable());

                return httpSecurity.build();
    }
}