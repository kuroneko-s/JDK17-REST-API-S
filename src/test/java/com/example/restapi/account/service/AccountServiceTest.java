package com.example.restapi.account.service;

import com.example.restapi.account.domain.Account;
import com.example.restapi.account.domain.AccountRole;
import com.example.restapi.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveAccount(String password) {
        Account account = Account.builder()
                .email("test@test.com")
                .password(password)
                .roles(Collections.singleton(AccountRole.ADMIN))
                .build();

        account.setPassword(passwordEncoder.encode(account.getPassword()));

        Account newAccount = this.repository.save(account);
    }

    @Test
    public void findByUsername() {
        this.saveAccount("1234");

        String username = "test@test.com";

        UserDetails userDetails = accountService.loadUserByUsername(username);

        assertEquals(userDetails.getUsername(), username);
        assertTrue(passwordEncoder.matches("1234", userDetails.getPassword()));
    }

}