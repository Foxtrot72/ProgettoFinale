package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Model.Token;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.TokenRepository;
import com.example.demo.Repository.UtenteRepository;

import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public Utente findUtenteByEmail(String email) {
        return utenteRepository.findByEmail(email);
    }

    public void createPasswordResetTokenForUtente(Utente utente, String token) {
        Optional<Token> existingToken = tokenRepository.findById(utente.getId());
        if (existingToken.isPresent()) {
            existingToken.get().setToken(token);
            tokenRepository.save(existingToken.get());
        } else {
            Token newToken = new Token(token, utente);
            tokenRepository.save(newToken);
        }
    }

    public String validatePasswordResetToken(String token) {
        Token passToken = tokenRepository.findByToken(token);
        if (passToken == null) {
            return "invalidToken";
        }
        return null;
    }

    public Utente getUtenteByPasswordResetToken(String token) {
        Token passToken = tokenRepository.findByToken(token);
        if (passToken == null) {
            throw new RuntimeException("Token non valido");
        }
        return passToken.getUtente();
    }

    @Transactional
    public void changeUtentePassword(Utente utente, String password) {
        utente.setPassword(password);
        utenteRepository.save(utente);
    }
}
