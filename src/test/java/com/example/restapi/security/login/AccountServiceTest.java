package com.example.restapi.security.login;

import com.example.restapi.account.domain.Account;
import com.example.restapi.account.domain.AccountRole;
import com.example.restapi.account.repository.AccountRepository;
import com.example.restapi.account.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void findByUsername() {
        String username = "test@email.com";
        String password = "1234";

        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.USER, AccountRole.ADMIN))
                .build();

        accountRepository.save(account);

        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(userDetails.getPassword(), password);
    }

    @Test
    void findByUsername_error() {
        assertThrows(UsernameNotFoundException.class, () -> {
            String username = "test@email.com";
            String password = "1234";

            UserDetailsService userDetailsService = accountService;
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            assertNotNull(userDetails);
            assertEquals(userDetails.getPassword(), password);
        }, "test@email.com");
    }
}