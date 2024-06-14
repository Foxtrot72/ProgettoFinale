package com.example.demo.Controller;

import java.util.List;
import java.util.UUID;

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

@Controller
public class UtenteController {

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
        Utente utente = utenteService.findUtenteByEmail(email);
        if (utente == null) {
            model.addAttribute("error", "No user found with this email address.");
            return "RecuperoPassword";
        }
        String token = UUID.randomUUID().toString();
        utenteService.createPasswordResetTokenForUtente(utente, token);
        String resetUrl = "http://localhost:8080/reset-password?token=" + token;
        emailService.sendSimpleMessage(email, "Password Reset Request",
                "To reset your password, click the link below:\n" + resetUrl);
        model.addAttribute("message", "A password reset link has been sent to " + email);
        return "RecuperoPassword";
    }

    @GetMapping("/reset-password")
    public String mostraFormResetPassword(@RequestParam("token") String token, Model model) {
        String result = utenteService.validatePasswordResetToken(token);
        if (result != null) {
            model.addAttribute("error", "Invalid token.");
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
            model.addAttribute("error", "Invalid token.");
            return "ResetPassword";
        }
        Utente utente = utenteService.getUtenteByPasswordResetToken(token);
        utenteService.changeUtentePassword(utente, password);
        model.addAttribute("message", "Your password has been reset successfully.");
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
        List<Prodotto> prodotti= prodottoRepository.findAll();
        model.addAttribute("prodotti", prodotti);
        return "Catalogo";
    }


}


