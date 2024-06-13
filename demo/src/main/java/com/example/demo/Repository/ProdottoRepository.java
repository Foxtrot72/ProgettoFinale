package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Prodotto;

@Repository
public interface  ProdottoRepository extends JpaRepository<Prodotto, Long> {
    
}
