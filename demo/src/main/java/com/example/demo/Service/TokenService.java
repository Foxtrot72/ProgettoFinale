package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Token;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.TokenRepository;
import com.example.demo.Repository.UtenteRepository;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UtenteRepository utenteRepository; // Supponendo che tu abbia anche un repository per Utente

    @Transactional
    public void createPasswordResetTokenForUtente(Long userId, String token) {
        // Recupera l'oggetto Utente dal repository
        Utente utente = utenteRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utente non trovato con id: " + userId));

        // Crea un nuovo Token usando l'oggetto Utente recuperato
        Token newToken = new Token(token, utente);
        tokenRepository.save(newToken);
    }
}
