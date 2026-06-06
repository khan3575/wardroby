package com.khan.wardroby.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(csrf -> csrf.disable())
               // .csrf(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login", "/css/**", "/js/**", "/register").permitAll()
//                        .requestMatchers("/", "/home", "/register").permitAll() // Public access
//                        .requestMatchers("/admin/**").hasRole("ADMIN")         // Restricted to ADMIN
                        .anyRequest().authenticated())
                .logout(logout -> logout
                        .logoutSuccessUrl("/logint?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder()
    {
        return new BCryptPasswordEncoder();
    }




}
