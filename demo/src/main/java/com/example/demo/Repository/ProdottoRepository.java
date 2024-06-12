package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Prodotto;

public interface  ProdottoRepository extends JpaRepository<Prodotto, Long> {
    
}
