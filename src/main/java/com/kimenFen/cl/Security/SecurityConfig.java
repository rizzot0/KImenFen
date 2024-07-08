package com.kimenFen.cl.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        UserDetails apoderado = User.withDefaultPasswordEncoder()
                .username("apoderado")
                .password("password")
                .roles("APODERADO")
                .build();

        UserDetails profesor = User.withDefaultPasswordEncoder()
                .username("profesor")
                .password("password")
                .roles("PROFESOR")
                .build();

        UserDetails administrador = User.withDefaultPasswordEncoder()
                .username("administrador")
                .password("password")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(apoderado, profesor, administrador);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/administrador/**").hasRole("ADMIN")
                        .requestMatchers("/profesor/**").hasRole("PROFESOR")
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {
            String redirectUrl = request.getContextPath();
            if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_APODERADO"))) {
                redirectUrl += "/apoderado/menu";
            } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PROFESOR"))) {
                redirectUrl += "/profesor/menu";
            } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                redirectUrl += "/administrador/menu";
            }
            response.sendRedirect(redirectUrl);
        };
    }
}
