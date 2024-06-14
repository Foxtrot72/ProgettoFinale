package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.Model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);

    Token getByToken(String token);
}
