package com.vsvet.example.onlinebanking.repository;

import com.vsvet.example.onlinebanking.domain.CardAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface CardAccountRepository extends JpaRepository<CardAccount, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<CardAccount> findByClientEmailAndByDefaultIsTrue(String email);

    @Lock(LockModeType.OPTIMISTIC)
    Optional<CardAccount> findByClientEmailAndByDefaultIsTrueAndActiveIsTrue(String email);

    @Lock(LockModeType.OPTIMISTIC)
    Optional<CardAccount> findOneByCardNumberAndClientEmail(String cardNumber,String email);
}
