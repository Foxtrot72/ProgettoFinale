package com.example.demo.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.Token;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.TokenRepository;
import com.example.demo.Repository.UtenteRepository;

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
        Token myToken = new Token(token, utente);
        tokenRepository.save(myToken);
    }

    public String validatePasswordResetToken(String token) {
        Token passToken = tokenRepository.findByToken(token);
        if (passToken == null) {
            return "invalidToken";
        }
        return null;
    }

    public Utente getUtenteByPasswordResetToken(String token) {
        return tokenRepository.findByToken(token).getUtente();
    }

    public void changeUtentePassword(Utente utente, String password) {
        utente.setPassword(password);
        utenteRepository.save(utente);
    }
}
