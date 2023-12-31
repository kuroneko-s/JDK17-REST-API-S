package com.example.restapi.auth;

import com.example.restapi.account.domain.Account;
import com.example.restapi.account.domain.AccountRole;
import com.example.restapi.account.service.AccountService;
import com.example.restapi.event.contorller.AbstractControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthServerConfigTest extends AbstractControllerTest {

    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("인증 토큰을 발급 받는 테스트")
    public void getAuthToken() throws Exception {
        String username = "test@test.com";
        String password = "aa1234";
        Account account = Account.builder()
                .email(username)
                .password(password)
                .roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
                .build();
        this.accountService.saveAccount(account);

        // 토큰을 발급받는 스프링 Auth2.0 제공하는 6가지 방법
        // Grant Type - password
        // 한번의 요청으로 토큰 발급 가능.

        String clientId = "testApp";
        String clientSecret = "pass";

        this.mockMvc.perform(post("/oauth/token")
                        .with(httpBasic(clientId, clientSecret)) // BASIC_AUTH Header 생성
                        .param("username", username)
                        .param("password", password)
                        .param("grant_type", "password")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists())
        ;

        // BearerTokenAuthenticationToken
//         SecurityContextRepository
//        DelegatingSecurityContextRepository
//        HttpSessionSecurityContextRepository
        // AuthenticationEntryPoint

    }


}