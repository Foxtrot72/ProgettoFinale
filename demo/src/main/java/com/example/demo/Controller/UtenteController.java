package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.example.demo.Model.Prodotto;
import com.example.demo.Model.Utente;
import com.example.demo.Repository.UtenteRepository;





@Controller
public class UtenteController {

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente findByUsername(String username) {
        return utenteRepository.findByUsername(username);
    }
    

    @GetMapping("/")
    public String paginaIniziale() {
        return "Inizio";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/utenti")
    public String getAllutenti(Model model) {
        List<Utente> utenti = utenteRepository.findAll();
        model.addAttribute("utenti", utenti);
        return "Utenti";
    }

    @GetMapping("/sign")
    public String mostraFormSign(Utente utente, Model model) {
        model.addAttribute("utente", utente);
        return "Sign";
    }

    @PostMapping("/sign")
    public String aggiungiUtente(Utente utente) {
        utenteRepository.save(utente);
        // model.addAttribute("utente", utente);
        return "redirect:/home"; 
    } 

    @GetMapping("/home")
    public String mostraFormSign() {
       
        return "Home";
    }
    @GetMapping("/venditore")
    public String mostraVenditore() {
       
        return "venditore";
    }
    @GetMapping("/inizio")
    public String mostraInzio() {
       
        return "Inizio";
    }
    @GetMapping("/acquirente")
    public String mostraFromProdotti(Prodotto prodotto, Model model) {
        model.addAttribute("prodotto", prodotto);
        return "Acquirente";
    }


    // @PostMapping("/acquirente")
    // public String ricercaProdotto(String nome, Model model) {
    //     List<Prodotto> prodotti = prodottoRepository.findByNome(nome);
    //     model.addAttribute("prodotti", prodotti);
    //     return "redirect:/";
    // }
    
//


//     @PostMapping("/sign")
// public ModelAndView aggiungiUtente(Utente utente) {
//     utenteRepository.save(utente);
//     ModelAndView mav = new ModelAndView("redirect:/utenti");
//     mav.addObject("messaggio", "Utente aggiunto con successo");
//     return mav;
// }


@GetMapping("/login")
public String mostraFormLogin(Model model) {
    model.addAttribute("utente", new Utente());
    return "Login";
}

@PostMapping("/login")
public String autentificaUtente(@ModelAttribute("utente") Utente utente, Model model) {
    Utente existingUtente = findByUsername(utente.getUsername());
    if (existingUtente != null && existingUtente.getPassword().equals(utente.getPassword())) {
        return "redirect:/home";
    } else {
        model.addAttribute("error", "Username o password non corretti");
        return "login";
    }
}
}

