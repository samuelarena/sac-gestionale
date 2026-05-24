package com.sac_gestionale.config; // O il package che preferisci

import org.springframework.lang.NonNull;
import com.sac_gestionale.service.CustomUserDetailsService;
import com.sac_gestionale.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    // Costruttore
    public JwtAuthenticationFilter(JwtService jwtService, CustomUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Estrai l'header "Authorization" dalla request
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;

        // 2. Se l'header è nullo o NON inizia con "Bearer ", passa oltre (la richiesta verrà bloccata dopo)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Estrai il token (taglia i primi 7 caratteri: "Bearer ")
        jwt = authHeader.substring(7);

        // 4. Estrai lo username usando il jwtService
        username = jwtService.extractUsername(jwt);

        // 5. Se abbiamo uno username E l'utente non è già autenticato nel contesto attuale
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            
            // 5a. Carica i dettagli dell'utente dal database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 5b. Se il token è valido
            if (jwtService.isTokenValid(jwt, userDetails)) {
                
                // Crea il "biglietto d'ingresso" ufficiale per Spring Security
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                
                // Aggiunge i dettagli della richiesta (IP, sessione, ecc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                
                // Aggiorna il contesto di sicurezza (Apre la porta)
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        
        // Passa la richiesta al filtro successivo
        filterChain.doFilter(request, response);
    }
}