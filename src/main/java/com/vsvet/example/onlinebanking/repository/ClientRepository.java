package com.vsvet.example.onlinebanking.repository;

import com.vsvet.example.onlinebanking.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<Client> findOneByEmail(String email);
}
