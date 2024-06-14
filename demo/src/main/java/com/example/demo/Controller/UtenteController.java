package com.example.demo.Controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Model.Prodotto;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.ProdottoRepository;
import com.example.demo.Repository.UtenteRepository;
import com.example.demo.Service.EmailService;
import com.example.demo.Service.UtenteService;

import org.slf4j.Logger;

@Controller
public class UtenteController {
    private static final Logger logger = LoggerFactory.getLogger(UtenteController.class);

    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private EmailService emailService;

    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }

    @GetMapping("/utenti")
    public String getAllutenti(Model model) {
        List<Utente> utenti = utenteRepository.findAll();
        model.addAttribute("utenti", utenti);
        return "Utenti";
    }

    @GetMapping("/")
    public String paginaIniziale() {
        return "Inizio";
    }

    @GetMapping("/home")
    public String home() {
        return "Home";
    }

    @GetMapping("/sign")
    public String mostraFormSign(Utente utente, Model model) {
        model.addAttribute("utente", utente);
        return "Sign";
    }

    @PostMapping("/sign")
    public String aggiungiUtente(Utente utente) {
        utenteRepository.save(utente);
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String mostraFormLogin(Model model) {
        model.addAttribute("utente", new Utente());
        return "Login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("utente") Utente utente, Model model) {
        Utente existingUtente = findByUsername(utente.getUsername());
        if (existingUtente != null && existingUtente.getPassword().equals(utente.getPassword())) {
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Username o password non corretti");
            return "Login";
        }
    }

    @GetMapping("/recupero-password")
    public String mostraFormRecuperoPassword(Model model) {
        model.addAttribute("email", new String());
        return "RecuperoPassword";
    }

    @PostMapping("/recupero-password")
    public String recuperaPassword(@RequestParam("email") String email, Model model) {
        try {
            logger.info("Received request to send password recovery link for email {}", email);
            Utente utente = utenteService.findUtenteByEmail(email);
            if (utente == null) {
                logger.info("No user found with email {}", email);
                model.addAttribute("error", "Nessun utente con questa email trovato");
                return "RecuperoPassword";
            }
            logger.info("User found, generating password reset token");
            String token = UUID.randomUUID().toString();
            utenteService.createPasswordResetTokenForUtente(utente, token); // Aggiornato per passare l'oggetto Utente e
                                                                            // il token
            logger.info("Password reset token generated, sending email");
            emailService.sendSimpleMessage(email, "Richiesta Reset Password",
                    "Per resettare la tua password, clicca sul link sotto:\n" + token);
            logger.info("Email sent successfully");
            model.addAttribute("message", "Un link per resettare la password è stato mandato a " + email);
            return "RecuperoPassword";
        } catch (Exception e) {
            logger.error("Error sending password recovery link", e);
            model.addAttribute("error", "Errore durante l'invio del link di recupero password");
            return "RecuperoPassword";
        }
    }

    @GetMapping("/reset-password")
    public String mostraFormResetPassword(@RequestParam("token") String token, Model model) {
        String result = utenteService.validatePasswordResetToken(token);
        if (result != null) {
            model.addAttribute("error", "Token non valido");
            return "RecuperoPassword";
        }
        model.addAttribute("token", token);
        return "ResetPassword";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam("token") String token,
            @RequestParam("password") String password,
            Model model) {
        String result = utenteService.validatePasswordResetToken(token);
        if (result != null) {
            model.addAttribute("error", "Token non valido");
            return "ResetPassword";
        }
        Utente utente = utenteService.getUtenteByPasswordResetToken(token);
        utenteService.changeUtentePassword(utente, password);
        model.addAttribute("message", "La tua password è stata resettata con successo!");
        return "Login";
    }

    @GetMapping("/venditore")
    public String mostraVenditore() {
        return "Venditore";
    }

    @GetMapping("/acquirente")
    public String mostraFromProdotti(Prodotto prodotto, Model model) {
        model.addAttribute("prodotto", prodotto);
        return "Acquirente";
    }

    @PostMapping("/acquirente")
    public String ricercaProdotto(@ModelAttribute Prodotto prodotto, Model model) {
        List<Prodotto> prodotti = prodottoRepository.findByNome(prodotto.getNome());
        model.addAttribute("prodotti", prodotti);
        return "Risultati";
    }

    @GetMapping("/prodotticatalogo")
    public String mostraCatalogo(Model model) {
        List<Prodotto> prodotti = prodottoRepository.findAll();
        model.addAttribute("prodotti", prodotti);
        return "Catalogo";
    }
}
