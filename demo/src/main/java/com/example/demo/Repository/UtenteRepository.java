package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    Utente findByEmail(String email);

    Utente findByUsername(String username);
}
