package com.sac_gestionale.controller;

import com.sac_gestionale.dto.AuthenticationRequest;
import com.sac_gestionale.dto.AuthenticationResponse;
import com.sac_gestionale.service.CustomUserDetailsService;
import com.sac_gestionale.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtService jwtService;

    // Iniezione delle dipendenze tramite costruttore
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService userDetailsService,
            JwtService jwtService
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        
        // 1. Verifica delle credenziali
        // Se username o password sono errati, l'AuthenticationManager lancia
        // un'eccezione (es. BadCredentialsException) e il metodo si ferma qui.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // 2. Se l'autenticazione ha successo, carichiamo i dettagli dell'utente dal DB
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());

        // 3. Generiamo il token JWT per l'utente
        final String jwtToken = jwtService.generateToken(userDetails);

        // 4. Rispondiamo con l'oggetto AuthenticationResponse contenente il token
        return new AuthenticationResponse(jwtToken);
    }
}