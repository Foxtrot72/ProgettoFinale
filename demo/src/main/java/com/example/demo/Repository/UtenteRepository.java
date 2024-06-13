package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long> {

}
