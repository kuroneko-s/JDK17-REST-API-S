package com.example.restapi;

import com.example.restapi.account.domain.AccountRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.RequestCacheConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Migration-Guide
 * 현재 OAuth 2.x에서 Spring Seuciryt 5.2.x로 기능들이 대체되고 있다. (ex. TokenStore)
 * 우선적으로는 OAuth 2.x 방식대로 구현할 것.
 */

@Slf4j
@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // 외부에서 참조할 수 있도록 별도로 등록
        // 변경된 설정에서는 AuthenticationManager 빈 생성 시 스프링의 내부 동작으로 인해 위에서 작성한 UserSecurityService와 PasswordEncoder가 자동으로 설정됩니다.
        return authenticationConfiguration.getAuthenticationManager();
    }

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.anonymous(AbstractHttpConfigurer::disable);
        httpSecurity.formLogin(withDefaults());

        *//**
         * 지금 favicon이나 api/**에 대한 접근이 이상하게 됨.
         * 5.7 이상 버전에 맞게 수정해야함 .
         *//*
        httpSecurity
                .authorizeHttpRequests(registry -> registry
                        // Spring Boot가 지원해줌 - PathRequest.toStaticResources
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/docs/index.html").hasAnyRole(AccountRole.USER.name(), AccountRole.ADMIN.name())
                        // .requestMatchers(HttpMethod.GET, "/api/events", "/api/events/{id}").permitAll()
                        // .requestMatchers("/api").authenticated()
                        .anyRequest().authenticated());

        return httpSecurity.build();


    }*/
}
