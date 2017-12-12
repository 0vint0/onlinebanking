package com.vsvet.example.onlinebanking.repository;

import com.vsvet.example.onlinebanking.domain.CardAccount;
import com.vsvet.example.onlinebanking.domain.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardTransactionRepository extends JpaRepository<CardTransaction, Long> {

    List<CardTransaction> findByCardAccountCardNumberAndClientEmail(String cardNumber, String email);

    List<CardTransaction> findByClientEmail(String email);

}
