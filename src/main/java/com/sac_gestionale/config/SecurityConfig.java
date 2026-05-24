package com.sac_gestionale.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;



import com.sac_gestionale.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthenticationFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // MODIFICA: Ora sia ADMIN che OPERATORE possono entrare nella stanza del singolo cliente!
                .requestMatchers(HttpMethod.GET, "/api/clienti/{id}").hasAnyRole("ADMIN", "OPERATORE")
                
                // La lista generale rimane accessibile a entrambi
                .requestMatchers(HttpMethod.GET, "/api/clienti").hasAnyRole("ADMIN", "OPERATORE")
                
                // Solo gli ADMIN possono creare, modificare o eliminare i clienti
                .requestMatchers(HttpMethod.POST, "/api/clienti").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/clienti/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/clienti/**").hasRole("ADMIN")
                
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                .requestMatchers("/api/auth/**").permitAll()
                
                // Tutto il resto richiede comunque di essere loggati
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Usa la fabbrica di Spring per creare un encoder "intelligente"
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}