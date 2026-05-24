package com.sac_gestionale.service;

import com.sac_gestionale.entity.Utente;
import com.sac_gestionale.repository.UtenteRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtenteRepository utenteRepository;

    public CustomUserDetailsService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Cerchiamo l'utente nel database usando il nostro Repository
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utente non trovato: " + username));

        // 2. Costruiamo l'oggetto UserDetails che Spring Security si aspetta
        return User.builder()
                .username(utente.getUsername())
                .password(utente.getPassword())
                .disabled(!utente.getActive()) // Se active è false (es. dipendente licenziato), blocca l'accesso
                // Usiamo authorities invece di roles perché la stringa ha già "ROLE_" dentro!
                .authorities(new SimpleGrantedAuthority(utente.getRuolo().getName()))
                .build();
    }
}