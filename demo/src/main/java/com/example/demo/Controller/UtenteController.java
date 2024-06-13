package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Model.Utente;
import com.example.demo.Repository.ProdottoRepository;
import com.example.demo.Repository.UtenteRepository;



@Controller
public class UtenteController {
    
    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @GetMapping("/")
    public String paginaIniziale() {
        return "Inizio";
    }

    // @GetMapping("/utenti")
    // public @ResponseBody void getAllUtenti() {
    //   utenteRepository.findAll();
    //   return;
    // }

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
        return "redirect:/"; 
    } 

    // @GetMapping("/login")
    // public String mostraFormLogin(@RequestParam("id") Long id, Model model) {
    //     Optional<Utente> optionalUtente = UtenteRepository.findById(id);
    //     if (optionalUtente.isPresent()) {
    //         model.addAttribute("utente", optionalUtente.get());
    //         return "Login";
    //     } else {
    //         return "redirect:/home";
    //     }
    // }

}

