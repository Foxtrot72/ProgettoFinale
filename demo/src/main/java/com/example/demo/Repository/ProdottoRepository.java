package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.Prodotto;


@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {
    List<Prodotto> findByNome(String nome);
    List<Prodotto> findByNomeAndPrezzoBetween(String nome, Double prezzoMin, Double prezzoMax);
    List<Prodotto> findByNomeAndPrezzoGreaterThanEqual(String nome, Double prezzoMin);
    List<Prodotto> findByNomeAndPrezzoLessThanEqual(String nome, Double prezzoMax);
}
