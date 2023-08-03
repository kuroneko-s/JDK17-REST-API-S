package com.example.restapi.account.repository;

import com.example.restapi.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmail(String username);
}
