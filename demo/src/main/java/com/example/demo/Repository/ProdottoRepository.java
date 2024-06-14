package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Prodotto;
import com.example.demo.Model.Utente;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
    List<Prodotto> findByUtente(Utente utente);
    List<Prodotto> findByNome(String nome); // Assicurati che il nome sia 'Nome' e non 'Name'
}
