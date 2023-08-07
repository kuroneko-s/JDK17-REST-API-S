# 서문
[Sample Code Git Repository](https://github.com/spring-projects/spring-security-samples/tree/main)      
Chat-GPT를 이용해서 번역파일 만드는 중.      
@Author - https://github.com/kuroneko-s     
시큐리티 버전 6.x 작업 후 MVC -> Spring Boot -> REST API -> REST DOCS -> HATEOAS 순으로 작업 예정.

# 본문

## Spring Security는 인증

권한 부여 및 일반적인 공격으로부터 보호하는 프레임워크입니다. 명령형 및 반응형 애플리케이션 모두를 보호하는 기능을 제공하며, Spring 기반 응용 프로그램의 보호를 위한 표준적인 프레임워크로 자리 잡고 있습니다.

전체 기능 목록은 레퍼런스의 기능 섹션을 참조하십시오.

## 시작하기
응용 프로그램의 보호를 시작하려면 서블릿과 반응형 Getting Started 섹션을 참조하십시오. 이러한 섹션에서 첫 번째 Spring Security 애플리케이션을 만드는 방법에 대해 안내합니다.

Spring Security의 작동 방식을 이해하려면 아키텍처 섹션을 참조하십시오.

문의 사항이 있으면 도움을 주기를 원하는 멋진 커뮤니티가 있습니다!

----

## 전제 조건
Spring Security는 Java 8 이상의 런타임 환경을 필요로 합니다.

Spring Security는 자체 포함 방식으로 작동하기 때문에 Java 런타임 환경에 별도의 설정 파일을 놓을 필요가 없습니다. 특히 특별한 Java 인증 및 권한 부여 서비스(JAAS) 정책 파일을 구성하거나 Spring Security를 공통 클래스 경로 위치에 배치할 필요가 없습니다.

마찬가지로 EJB Container나 Servlet Container를 사용하는 경우 특별한 구성 파일을 어디에도 놓을 필요가 없으며 Spring Security를 서버 클래스로더에 포함시킬 필요가 없습니다. 필요한 모든 파일은 애플리케이션 내에 포함되어 있습니다.

이 디자인은 대상 아티팩트(JAR, WAR 또는 EAR)를 시스템에서 다른 시스템으로 복사하여 즉시 작동할 수 있도록 최대 배포 시간 유연성을 제공합니다.

--- 

## Spring Security 커뮤니티
Spring Security 커뮤니티에 오신 것을 환영합니다! 이 섹션에서는 광범위한 커뮤니티를 최대한 활용하는 방법에 대해 설명합니다.

---

## 도움 받기       
Spring Security에 도움이 필요한 경우 여기에서 도와드리겠습니다. 다음은 도움을 받을 수 있는 최상의 방법 중 일부입니다.

- 이 문서를 읽어보십시오. [공식문서](https://docs.spring.io/spring-security/reference/community.html)
- 다양한 샘플 애플리케이션 중 하나를 시도해 보십시오.
- spring-security 태그가 있는 [스택오버플로우](https://stackoverflow.com)에서 질문을 하십시오.
- [깃허브 커뮤니티](https://github.com/spring-projects/spring-security/issues)에서 버그 및 기능 향상 요청을 보고하십시오.

--- 

## 참여하기
Spring Security 프로젝트에서 여러분들의 참여를 환영합니다. 스택 오버플로우에 대한 질문에 답변하거나, 새로운 코드를 작성하거나, 기존 코드를 개선하거나, 문서 작성에 도움을주거나, 샘플 또는 튜토리얼 개발, 버그 보고 또는 단순히 제안하는 것 등 여러 방법으로 기여할 수 있습니다. 더 자세한 정보는 기여 섹션을 참조하십시오.

---

## 소스 코드
Spring Security의 소스 코드는 GitHub에서 찾을 수 있습니다. [깃허브](https://github.com/spring-projects/spring-security/) 

---

## Apache 2 라이선스
Spring Security는 Apache 2.0 라이선스로 출시된 오픈 소스 소프트웨어입니다.

---

## 소셜 미디어
최신 뉴스를 살펴보려면 트위터에서 [@SpringSecurity](https://twitter.com/SpringSecurity) 및 [Spring Security](https://twitter.com/i/lists/75099534) 팀을 팔로우하실 수 있습니다. 전체 Spring 포트폴리오를 최신 상태로 유지하려면 [@SpringCentral](https://twitter.com/SpringCentral)을 팔로우하십시오.

--- 

## 7.0에 대비하기
Spring Security 7.0의 출시일은 아직 발표되지 않았지만 지금부터 대비하는 것이 중요합니다.

이 대비 가이드는 Spring Security 7.0에서 가장 큰 변경 사항을 요약하고 대비 단계를 제공하는 것을 목표로 합니다.

애플리케이션을 최신 Spring Security 6 및 Spring Boot 3 릴리스로 유지하는 것이 중요합니다.

### 구성
다음 단계는 HttpSecurity, WebSecurity 및 관련 컴포넌트를 구성하는 방법에 대한 변경 사항과 관련이 있습니다.

### 람다 DSL 사용하기
람다 DSL은 Spring Security 5.2 버전 이후부터 사용 가능하며, 람다를 사용하여 HTTP 보안을 구성할 수 있습니다.

Spring Security 문서나 샘플에서 이러한 구성 스타일을 볼 수 있습니다. 이제 람다를 사용한 HTTP 보안 구성이 이전 구성 스타일과 비교해서 어떻게 다른지 살펴보겠습니다.

```java
// 람다를 사용한 구성 예시
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/blog/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/login")
                .permitAll()
            )
            .rememberMe(Customizer.withDefaults());

        return http.build();
    }
}
```
```java
// 람다를 사용하지 않고 동등한 구성 예시
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .requestMatchers("/blog/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .rememberMe();

        return http.build();
    }
}
```
람다 DSL은 Spring Security를 구성하는 선호하는 방법입니다. Spring Security 7에서는 람다 DSL을 사용해야 하므로 이전 구성 스타일은 유효하지 않게 됩니다. 이는 대개 다음과 같은 몇 가지 이유 때문에 수행됩니다.

- 이전 구성 방식에서는 반환 유형을 알지 못하면 구성되는 객체가 무엇인지 명확하지 않았습니다. 중첩이 깊어질수록 더욱 혼란스러워집니다. 경험 많은 사용자도 자신의 구성이 하나의 작업을 수행하는 것으로 잘못 인식할 수 있습니다.
- 일관성. 많은 코드 기반에서 두 가지 스타일을 전환하면 구성을 이해하기 어렵고 종종 잘못된 구성으로 이어질 수 있는 불일치가 발생합니다.

### Lambda DSL 구성 팁

위의 두 샘플을 비교하면 몇 가지 주요 차이점을 알 수 있습니다.

- Lambda DSL에서는 .and() 메소드를 사용하여 구성 옵션을 체인으로 연결할 필요가 없습니다. 람다 메소드를 호출한 후 HttpSecurity 인스턴스가 자동으로 반환됩니다.
- Customizer.withDefaults()는 Spring Security에서 제공하는 기본값을 사용하여 보안 기능을 활성화합니다. 이는 람다 표현식 it -> {}의 약어입니다.

--- 

### WebFlux Security

WebFlux 보안도 유사한 방식으로 람다를 사용하여 구성할 수 있습니다. 아래 예제는 람다를 사용한 구성 예시입니다.

```java
// 람다를 사용한 구성 예시
@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/blog/**").permitAll()
                        .anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                );

        return http.build();
    }
}
```

### Lambda DSL의 목표

Lambda DSL은 다음과 같은 목표를 달성하기 위해 만들어졌습니다:

- 자동 들여쓰기는 구성을 더 읽기 쉽게 만듭니다.
- .and()를 사용하여 구성 옵션을 연결할 필요가 없습니다.
- Spring Integration 및 Spring Cloud Gateway와 같은 다른 Spring DSL과 유사한 구성 스타일을 가지고 있습니다.

---

## 6.0으로 마이그레이션하기

Spring Security 팀은 Spring Security 6.0으로 업그레이드를 간단히 하는 5.8 릴리스를 준비했습니다. 5.8 및 이에 대한 준비 단계를 사용하여 6.0으로 간단히 업데이트하세요. [5.8버전](https://docs.spring.io/spring-security/reference/5.8/migration/index.html)

5.8로 업데이트 한 후, 이 가이드를 따라 마이그레이션 또는 정리 작업을 완료하세요.

어려움에 봉착하는 경우, 준비 가이드에는 5.x 동작으로 되돌리는 opt-out 단계가 포함되어 있습니다.

## Spring Security 6.0으로 업데이트하기

첫 번째 단계는 Spring Boot 3.0의 최신 패치 릴리스를 사용하는지 확인하는 것입니다. 다음으로, Spring Security 6.0의 최신 패치 릴리스를 사용하는지 확인해야 합니다. Spring Security 6.0으로 업데이트하는 방법은 참조 가이드의 [Getting Spring Security](##Getting Spring Security) 섹션을 참고하시기 바랍니다.

## 패키지 이름 업데이트

Spring Security 6.0 버전 이상으로 업데이트한 경우, 모든 javax 패키지 import 문을 해당 jakarta 패키지의 대응되는 import 문으로 대체해야 합니다

## 애플리케이션별로 수행할 작업

[Servlet](#서블릿-마이그레이션) 또는 [Reactive](#Reactive) 애플리케이션인지에 따라 수행해야 할 특정 작업이 있습니다.


### 서블릿 마이그레이션

Servlet 애플리케이션의 초기 마이그레이션 단계를 이미 완료했다면, 이제 서블릿 애플리케이션에 특화된 단계를 수행할 준비가 되었습니다.

세부 요약 내용
- [세션 관리](#세션-관리-마이그레이션)
- [악용 방지](#악용-방지)
- [인증](#인증-마이그레이션)
- [인가](#인가-마이그레이션)

#### 세션 관리 마이그레이션

다음 단계는 세션 관리 지원 마이그레이션을 완료하는 방법에 관한 내용입니다.

#### SecurityContextRepository에 저장되도록 설정

Spring Security 5에서 기본 동작은 `SecurityContextPersistenceFilter`를 사용하여 `SecurityContext`가 자동으로 `SecurityContextRepository`에 저장되도록 하는 것입니다. 저장은 `HttpServletResponse`가 커밋되기 바로 직전 및 `SecurityContextPersistenceFilter` 직전에 수행해야 합니다. 그러나 `SecurityContext`의 자동 영속성은 요청이 완료되기 바로 직전에 수행되기 때문에 사용자를 놀라게 할 수 있습니다. 때로는 저장이 필요하지 않은 경우에도 상태를 추적하여 저장해야 하는지 여부를 결정하는 것이 복잡하므로 `SecurityContextRepository(i.e. HttpSession)`에 불필요하게 쓰기 작업이 발생할 수 있습니다.

Spring Security 6에서는 기본 동작으로 `SecurityContextHolderFilter`가 `SecurityContextRepository`에서 `SecurityContext`를 읽어와 `SecurityContextHolder`에 채워넣습니다. 사용자들은 이제 요청 사이에 `SecurityContext`를 유지하려면 `SecurityContextRepository`에 `SecurityContext`를 명시적으로 저장해야 합니다. 이렇게 함으로써 모호함을 제거하고 필요할 때만 `SecurityContextRepository(i.e. HttpSession)`에 쓰기 작업을 요구하여 성능을 향상시킵니다.

> 로그아웃 과정에서 SecurityContext를 지우는 경우에도 컨텍스트를 저장해야 됩니다. 이와 관련한 자세한 내용을 알고 싶다면, [해당 섹션](https://docs.spring.io/spring-security/reference/servlet/authentication/session-management.html#properly-clearing-authentication)을 참조하시면 됩니다.

만약 Spring Security 6의 새로운 기본값을 명시적으로 선택한다면, 다음 구성은 제거하여 Spring Security 6의 기본값을 수용할 수 있습니다.

```java
// SecurityContext 명시적 저장
public SecurityFilterChain filterChain(HttpSecurity http) {
	http
		// ...
		.securityContext((securityContext) -> securityContext
			.requireExplicitSave(true)
		);
	return http.build();
}
```

이 구성을 사용하면, `SecurityContext`를 `SecurityContextHolder`에 설정하는 코드는 요청 사이에 유지되어야 하는 경우 `SecurityContext`를 `SecurityContextRepository`에 저장해야 합니다.

다음과 같은 코드가 있다고 가정해보겠습니다.
```java
// SecurityContextPersistenceFilter를 사용하여 SecurityContextHolder에 설정하기
SecurityContextHolder.setContext(securityContext);
```

다음 구성은 대신 이렇게 변경할 수 있습니다.
```java
// SecurityContextHolderFilter를 사용하여 SecurityContextHolder에 설정하기
SecurityContextHolder.setContext(securityContext);
securityContextRepository.saveContext(securityContext, httpServletRequest, httpServletResponse);
```

#### 다중 SecurityContextRepository

Spring Security 5에서 기본 `SecurityContextRepository`는 `HttpSessionSecurityContextRepository`였습니다.

Spring Security 6에서는 기본 `SecurityContextRepository`가 `DelegatingSecurityContextRepository`로 변경되었습니다. `DelegatingSecurityContextRepository`는 기본적으로 `HttpSessionSecurityContextRepository`와 `InMemorySecurityContextRepository`를 사용합니다.

따라서 Spring Security 6에서는 기본적으로 `DelegatingSecurityContextRepository`를 사용하는 것이 좋습니다. 


> Chat-GPT가 추가한 내용.         
> 이 경우, `SecurityContext`는 기본적으로 세션에서 저장되고, 세션을 사용할 수 없는 경우 `InMemorySecurityContextRepository`에서 저장됩니다.
설정에 따라 특정 동작을 제어해야 하는 경우, `DelegatingSecurityContextRepository`에 여러 `SecurityContextRepository`를 추가하여 사용할 수 있습니다. 예를 들어 HTTP 요청 또는 메시지 처리 중에 특정 SecurityContextRepository를 사용하려는 경우가 있을 수 있습니다. 이 경우에는 `DelegatingSecurityContextRepository`에 다른 `SecurityContextRepository`를 추가하여 사용할 수 있습니다.
최신 버전의 Spring Security에서는 기본 `SecurityContextRepository`를 변경할 필요가 없으며, 구성하지 않아도 잘 작동합니다. 그러나 경우에 따라 구성이 필요할 수도 있습니다.

#### SecurityContextRepository에서 deprecation이 발생한 경우
이에 대한 추가적인 이전 작업은 더 이상 필요하지 않습니다

> Chat-GPT가 추가한 내용.         
> SecurityContextRepository 인터페이스에서는 일부 메소드가 deprecation 상태로 표시되어 있으며, 새로운 메소드를 사용하는 것이 권장됩니다. 최신 버전의 Spring Security로 업그레이드하여 보안 취약점이나 기능적인 결함이 해결될 수 있습니다.
구성에서 SecurityContextRepository를 사용하는 경우 메소드가 deprecation 상태인지 지속적으로 모니터링하고, 보안과 관련된 내용이나 기타 변경 사항이 있는 경우 최신 버전의 Spring Security로 업그레이드하는 것이 좋습니다.


#### 요청 캐시의 쿼리 최적화

Spring Security 5에서는 기본 동작이 모든 요청에서 저장된 요청을 쿼리하는 것입니다. 일반적인 설정에서는 RequestCache를 사용하기 위해 매 요청마다 HttpSession이 쿼리됩니다.

Spring Security 6에서는 `RequestCache`가 캐시된 요청에 대해 쿼리되는 경우 HTTP 매개 변수 continue가 정의된 경우에만 발생합니다. 이를 통해 Spring Security는 `RequestCache`를 사용하여 `HttpSession`을 불필요하게 읽지 않도록할 수 있습니다.

Spring Security 5에서는 기본적으로 매 요청마다 캐시된 요청을 쿼리하는 `HttpSessionRequestCache`를 사용합니다. 따라서 기본값을 재정의하지 않는 경우(즉, `NullRequestCache`를 사용하지 않는 경우), 다음 구성을 사용하여 Spring Security 5.8에서 명시적으로 Spring Security 6 동작에 참여할 수 있습니다.

```java
// RequestCache는 continue 매개 변수가 존재할 경우에만 저장된 요청을 확인합니다.
@Bean
DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
    HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
    requestCache.setMatchingRequestParameterName("continue");
    http
    // ...
        .requestCache((cache) -> cache
            .requestCache(requestCache)
        );
    return http.build();
}
```

#### 악용 방지 마이그레이션

5.8 마이그레이션 가이드에는 6.0으로 업데이트할 때 [악용 방지 마이그레이션](https://docs.spring.io/spring-security/reference/5.8/migration/servlet/exploits.html)을 위한 여러 단계가 포함되어 있습니다. 먼저 해당 단계를 수행하는 것이 좋습니다.

다음 단계는 악용 방지 지원 마이그레이션을 완료하는 방법과 관련이 있습니다.

#### CsrfToken 로딩 지연

Spring Security 5.8에서는 애플리케이션에서 `CsrfToken`을 사용할 수 있도록 하기 위한 기본 `CsrfTokenRequestHandler`가 `CsrfTokenRequestAttributeHandler`입니다. `csrfRequestAttributeName` 필드의 기본값은 null이며, 이로 인해 모든 요청에 CSRF 토큰이 로드됩니다.

Spring Security 6에서는 `csrfRequestAttributeName`의 기본값이 `_csrf`로 설정됩니다. 만약 업데이트를 위해 다음 구성만(Configure를) 만들었다면 이제 이를 제거할 수 있습니다.

```java
requestHandler.setCsrfRequestAttributeName("_csrf");
``` 

#### CSRF BREACH(CSRF 위반) 보호

Spring Security 5.8에서는 애플리케이션에서 `CsrfToken`을 사용할 수 있도록 하기 위한 기본 `CsrfTokenRequestHandler`가 `CsrfTokenRequestAttributeHandler`입니다. `XorCsrfTokenRequestAttributeHandler`가 추가되어 CSRF BREACH 지원에 참여할 수 있게 되었습니다.

Spring Security 6에서는 `XorCsrfTokenRequestAttributeHandler`가 기본 `CsrfTokenRequestHandler`로 설정되어 CsrfToken을 사용할 수 있게 됩니다. 만약 6.0으로 업데이트하기 위해 `XorCsrfTokenRequestAttributeHandler`를 구성했다면 완전히 제거할 수 있습니다.

> 지연된 토큰 사용을 포기하기 위해 `csrfRequestAttributeName`을 null로 설정했거나 다른 이유로 `CsrfTokenRequestHandler`를 구성한 경우, 구성을 그대로 두세요.       
> Chat-GPT에서 첨언한 부분         
> Spring Security 5.8에서는 `csrfRequestAttributeName` 필드의 기본값이 null인데, 이로 인해 모든 요청에서 CSRF 토큰이 로드됩니다. 반면, Spring Security 6에서의 `csrfRequestAttributeName` 기본값은 `_csrf`로 설정되어 있습니다. 이 변경으로 인해 모든 요청에서 자동으로 CSRF 토큰이 로드되지 않고 필요한 경우에만 로드되게 됩니다. 이것이 지연된 토큰 사용에 해당하는 내용입니다.

#### WebSocket 지원과 함께 사용되는 CSRF BREACH

Spring Security 5.8에서는 [WebSocket 보안](https://docs.spring.io/spring-security/reference/servlet/integrations/websocket.html)과 함께 CsrfToken을 사용할 수 있도록 하는 기본 `ChannelInterceptor`가 `CsrfChannelInterceptor`입니다. `XorCsrfChannelInterceptor`가 추가되어 CSRF BREACH 지원을 사용할 수 있게 되었습니다.

Spring Security 6에서는 `XorCsrfChannelInterceptor`가 기본 `ChannelInterceptor`로 설정되어 CsrfToken을 사용할 수 있게 됩니다. 만약 6.0으로 업데이트하기 위해 `XorCsrfChannelInterceptor`를 구성했다면 완전히 제거할 수 있습니다.


#### 인증 마이그레이션

다음 단계는 인증 지원 마이그레이션을 완료하는 방법과 관련이 있습니다.

#### AuthenticationServiceExceptions 전파

`AuthenticationFilter`는 `AuthenticationServiceExceptions`를 `AuthenticationEntryPoint`에 전파합니다. `AuthenticationServiceExceptions`은 클라이언트 측 오류가 아닌 서버 측 오류를 나타내므로 6.0에서는 컨테이너에 전파되도록 변경됩니다.

즉, `rethrowAuthenticationServiceException`을 true로 설정하여 이 동작을 선택한 경우 다음과 같이 제거할 수 있습니다.

```java
AuthenticationFilter authenticationFilter = new AuthenticationFilter(...);
AuthenticationEntryPointFailureHandler handler = new AuthenticationEntryPointFailureHandler(...);
handler.setRethrowAuthenticationServiceException(true);
authenticationFilter.setAuthenticationFailureHandler(handler);
```
위의 코드를 이와 같이 변경
```java
AuthenticationFilter authenticationFilter = new AuthenticationFilter(...);
AuthenticationEntryPointFailureHandler handler = new AuthenticationEntryPointFailureHandler(...);
authenticationFilter.setAuthenticationFailureHandler(handler);
```

#### Remember Me에서 SHA-256 사용

6.0에서는 `TokenBasedRememberMeServices`가 토큰을 인코드하고 매치하기 위해 SHA-256을 사용합니다. 마이그레이션을 완료하려면 기본값을 제거할 수 있습니다.

예를 들어, `encodingAlgorithm`과 `matchingAlgorithm`에 대해 6.0 기본값을 선택한 경우 다음과 같습니다:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http
                // ...
                .rememberMe((remember) -> remember
                    .rememberMeServices(rememberMeServices)
                );
        return http.build();
    }
    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        TokenBasedRememberMeServices rememberMe = new TokenBasedRememberMeServices(myKey, userDetailsService, encodingAlgorithm);
        rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.SHA256);
        return rememberMe;
    }
}
```
그러면 기본값을 제거할 수 있습니다.
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception {
        http
                // ...
                .rememberMe((remember) -> remember
                    .rememberMeServices(rememberMeServices)
                );
        return http.build();
    }
    @Bean
    RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
        return new TokenBasedRememberMeServices(myKey, userDetailsService);
    }
}
```

#### oauth2Login()의 기본 권한

Spring Security 5에서 OAuth2 또는 OpenID Connect 1.0 공급자를 통해 인증하는 사용자에게 부여되는 기본 `GrantedAuthority`는 `ROLE_USER`입니다.

Spring Security 6에서 OAuth2 공급자와 인증하는 사용자에게 부여되는 기본 권한은 `OAUTH2_USER`입니다. OpenID Connect 1.0 공급자와 인증하는 사용자에게 부여되는 기본 권한은 `OIDC_USER`입니다. 6.0으로 업데이트하기 위한 목적으로만 `GrantedAuthoritiesMapper`를 구성한 경우 완전히 제거할 수 있습니다.

#### 인가-마이그레이션

다음 단계는 권한 부여 지원 마이그레이션을 완료하는 방법과 관련이 있습니다.

#### 메소드 보안을 위한 AuthorizationManager 사용

이 기능에 대한 추가 마이그레이션 단계는 없습니다.

#### 메시지 보안을 위한 AuthorizationManager 사용

6.0에서는 `<websocket-message-broker>`의 기본값이 `use-authorization-manager`를 `true`로 설정합니다. 따라서 마이그레이션을 완료하려면 `websocket-message-broker@use-authorization-manager=true` 속성을 삭제해야 합니다.

예를 들어.
```xml
<websocket-message-broker use-authorization-manager="true"/>
```
이렇게 변경.
```xml
<websocket-message-broker/>
```
이 기능에 대한 자바 또는 코틀린 추가 마이그레이션 단계는 없습니다.

#### 요청 보안을 위한 `AuthorizationManager` 사용

6.0에서는 `<http>`의 기본값 중 `once-per-request`는 `false`, `filter-all-dispatcher-types`는 `true`이고 `use-authorization-manager`는 `true`로 설정됩니다. 또한 `authorizeRequests#filterSecurityInterceptorOncePerRequest`는 기본적으로 `false`이고 `authorizeHttpRequests#filterAllDispatcherTypes`는 기본적으로 `true`입니다.      
따라서 마이그레이션을 완료하려면 기본값을 제거할 수 있습니다.

예를 들어, `filter-all-dispatcher-types` 또는 `authorizeHttpRequests#filterAllDispatcherTypes`에 대한 6.0 기본값을 선택한 경우 다음과 같습니다:
```java
http
    .authorizeHttpRequests((authorize) -> authorize
        .filterAllDispatcherTypes(true)
        // ...
    )
```
그러면 기본값을 제거할 수 있습니다:
```java
http
    .authorizeHttpRequests((authorize) -> authorize
        // ...
    )
```
>`once-per-request`는 `use-authorization-manager="false"`일 때만 적용되고 `filter-all-dispatcher-types`는 `use-authorization-manager="true"`일 때만 적용됩니다.

### Reactive

리액티브 애플리케이션에 대한 초기 마이그레이션 단계를 이미 수행한 경우, 이제 리액티브 애플리케이션에 특정한 단계를 수행할 준비가 되었습니다.

#### 메소드 보안을 위한 AuthorizationManager 사용

6.0에서는 `@EnableReactiveMethodSecurity`의 기본값이 `useAuthorizationManager`를 `true`로 설정합니다. 따라서 마이그레이션을 완료하려면 [`@EnableReactiveMethodSecurity`](https://docs.spring.io/spring-security/site/docs/6.1.2/api/org/springframework/security/config/annotation/method/configuration/EnableReactiveMethodSecurity.html)에서 `useAuthorizationManager` 속성을 삭제하세요:


```java
@EnableReactiveMethodSecurity(useAuthorizationManager = true)
```
이렇게 변경.
```java
@EnableReactiveMethodSecurity
```

#### `AuthenticationServiceExceptions` 전파

`AuthenticationWebFilter`는 `AuthenticationServiceExceptions`를 `ServerAuthenticationEntryPoint`에 전파합니다. `AuthenticationServiceExceptions`가 클라이언트 측 오류가 아닌 서버 측 오류를 나타내므로, 6.0에서는 컨테이너에 전파되도록 변경됩니다.

따라서 `rethrowAuthenticationServiceException`을 `true`로 설정하여 이 동작을 선택한 경우 다음과 같이 제거할 수 있습니다:

```java
AuthenticationFailureHandler bearerFailureHandler = new ServerAuthenticationEntryPointFailureHandler(bearerEntryPoint);
bearerFailureHandler.setRethrowAuthenticationServiceException(true);
AuthenticationFailureHandler basicFailureHandler = new ServerAuthenticationEntryPointFailureHandler(basicEntryPoint);
basicFailureHandler.setRethrowAuthenticationServiceException(true);
```
이렇게 변경.
```java
AuthenticationFailureHandler bearerFailureHandler = new ServerAuthenticationEntryPointFailureHandler(bearerEntryPoint);
AuthenticationFailureHandler basicFailureHandler = new ServerAuthenticationEntryPointFailureHandler(basicEntryPoint);
```

>6.0으로 업데이트하기 위한 목적으로만 `ServerAuthenticationFailureHandler`를 구성한 경우 완전히 제거할 수 있습니다.

---

## Getting Spring Security

이 섹션에서는 Spring Security 바이너리를 가져오는 방법을 설명합니다. 소스 코드를 얻는 방법은 [소스 코드](https://docs.spring.io/spring-security/reference/community.html#community-source)를 참조하십시오.

### 릴리스 번호 지정

Spring Security 버전은 MAJOR.MINOR.PATCH 형식으로 지정되며, 다음과 같습니다:

- MAJOR 버전에는 호환되지 않는 변경 사항이 포함될 수 있습니다. 일반적으로 이러한 변경은 현대 보안 관행에 맞는 개선된 보안을 제공하기 위해 수행됩니다.
- MINOR 버전에는 개선 사항이 포함되어 있지만, 수동 업데이트로 간주됩니다.
- PATCH 레벨은 버그 수정을 담당하는 변경 사항을 제외하고는 완벽하게 호환되어야 하며, 앞뒤로 호환됩니다.
 
### Maven 사용법

대부분의 오픈 소스 프로젝트와 마찬가지로 Spring Security도 Maven 아티팩트로 종속성을 배포합니다. 이 섹션의 주제는 Maven을 사용할 때 Spring Security를 사용하는 방법에 대해 설명합니다.

### Maven과 함께 사용하는 Spring Boot

Spring Boot는 Spring Security 관련 종속성을 집계하는 `spring-boot-starter-security` 스타터를 제공합니다. 스타터를 사용하는 가장 간단하고 선호되는 방법은 Spring Initializr를 사용하여 IDE 통합(Eclipse, IntelliJ, NetBeans)이나 `start.spring.io`를 통해 사용하는 것입니다. 대안으로, 다음 예와 같이 스타터를 수동으로 추가할 수도 있습니다:

<p class="codeblock-label">pom.xml</p>

```xml
<dependencies>
	<!-- ... other dependency elements ... -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>
</dependencies>
```
Spring Boot가 종속성 버전을 관리하는 데 Maven BOM을 제공하기 때문에 버전을 지정할 필요가 없습니다. Spring Security 버전을 덮어쓰고 싶다면, Maven 속성을 제공하여 수행할 수 있습니다:
<p class="codeblock-label">pom.xml</p>

```xml
<properties>
	<!-- ... -->
	<spring-security.version>6.1.2</spring-security.version>
</properties>
```
Spring Security는 주요 릴리스에서만 호환되지 않는 변경 사항을 수행하기 때문에, Spring Boot와 함께 최신 버전의 Spring Security를 안전하게 사용할 수 있습니다. 하지만 때로는 Spring 프레임워크 버전을 업데이트해야 할 수도 있습니다. Maven 속성을 추가하여 업데이트할 수 있습니다:
<p class="codeblock-label">pom.xml</p>

```xml
<properties>
	<!-- ... -->
	<spring.version>6.0.11</spring.version>
</properties>
```
추가 기능(예: LDAP, OAuth 2 등)을 사용하는 경우, 적절한 [프로젝트 모듈과 종속성](https://docs.spring.io/spring-security/reference/modules.html#modules)도 포함해야 합니다.

### Spring Boot 없이 Maven 사용하기

Spring Boot 없이 Spring Security를 사용할 때 권장되는 방법은 프로젝트 전체에서 일관된 버전의 Spring Security를 사용하도록 보장하는 Spring Security의 BOM을 사용하는 것입니다. 다음 예제는 그 방법을 보여줍니다:
<p class="codeblock-label">pom.xml</p>

```xml
<dependencyManagement>
	<dependencies>
		<!-- ... other dependency elements ... -->
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-bom</artifactId>
			<version>{spring-security-version}</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

최소한의 Spring Security Maven 종속성 모음은 일반적으로 다음 예와 같습니다:
<p class="codeblock-label">pom.xml</p>

```xml
<dependencies>
	<!-- ... other dependency elements ... -->
	<dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.security</groupId>
		<artifactId>spring-security-config</artifactId>
	</dependency>
</dependencies>
```
추가 기능(예: LDAP, OAuth 2 등)을 사용하는 경우, 적절한 [프로젝트 모듈과 종속성](https://docs.spring.io/spring-security/reference/modules.html#modules)도 포함해야 합니다.

Spring Security는 Spring Framework 6.0.11과 함께 빌드되지만 일반적으로 Spring Framework 5.x의 더 새로운 버전과 함께 작동해야 합니다. 많은 사용자들이 Spring Security의 전이 종속성이 Spring Framework 6.0.11을 해결하고, 이로 인해 이상한 classpath 문제가 발생할 수 있다는 사실에 직면할 것입니다. 이 문제를 해결하는 가장 쉬운 방법은 pom.xml의 <dependencyManagement> 섹션에서 spring-framework-bom을 사용하는 것입니다:
<p class="codeblock-label">pom.xml</p>

```xml
<dependencyManagement>
	<dependencies>
		<!-- ... other dependency elements ... -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-framework-bom</artifactId>
			<version>6.0.11</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

이전 예제는 Spring Security의 모든 전이 종속성이 Spring 6.0.11 모듈을 사용하도록 보장합니다.

> Chat-GPT 첨삭       
> 하지만, 위의 예제 코드를 Spring Framework 5.x 버전으로 적용하려면 다음과 같이 수정해 주세요.
> ```xml
> <dependencyManagement> 
>   <dependencies>
>       <dependency>
>           <groupId>org.springframework</groupId>
>           <artifactId>spring-framework-bom</artifactId>
>           <version>5.x.y.RELEASE</version>
>           <type>pom</type>
>           <scope>import</scope>
>       </dependency>
>   </dependencies>
> </dependencyManagement>
> ```
> 위 코드에서 5.x.y.RELEASE를 사용하려는 Spring Framework 5.x 버전으로 교체해 주세요. 이렇게 하면 프로젝트의 모든 Spring 종속성이 동일한 버전되 가능한 문제를 줄일 수 있습니다.
        
> 이 방법은 Maven의 "bill of materials" (BOM) 개념을 사용하며, Maven 2.0.9 이상에서만 사용할 수 있습니다. 종속성이 어떻게 해결되는지에 대한 추가 정보는, Maven의 종속성 메커니즘 소개 문서를 참고하십시오.

### Maven 저장소

모든 GA 릴리스(즉, .RELEASE로 끝나는 버전)는 Maven Central에 배포되므로, pom에 추가적인 Maven 저장소를 선언할 필요가 없습니다.

SNAPSHOT 버전을 사용하는 경우, Spring Snapshot 저장소가 정의되어 있는지 확인해야 합니다:
<p class="codeblock-label">pom.xml</p>

```xml
<repositories>
	<!-- ... possibly other repository elements ... -->
	<repository>
		<id>spring-snapshot</id>
		<name>Spring Snapshot Repository</name>
		<url>https://repo.spring.io/snapshot</url>
	</repository>
</repositories>
```
마일스톤 버전이나 릴리스 후보 버전을 사용하는 경우, Spring Milestone 저장소가 정의되어 있는지 확인해야 합니다.  
다음 예와 같이 설정할 수 있습니다:


```xml
<repositories>
	<!-- ... possibly other repository elements ... -->
	<repository>
		<id>spring-milestone</id>
		<name>Spring Milestone Repository</name>
		<url>https://repo.spring.io/milestone</url>
	</repository>
</repositories>
```

### Gradle

대부분의 오픈 소스 프로젝트와 마찬가지로, Spring Security는 Maven 아티팩트로 의존성을 배포합니다. 이로 인해 Gradle에서도 일류 지원을 할 수 있습니다. 다음 주제에서는 Gradle을 사용할 때 Spring Security를 사용하는 방법에 대해 설명합니다.

### Gradle과 함께 사용하는 Spring Boot

Spring Boot는 Spring Security 관련 의존성을 모으는 `spring-boot-starter-security` 스타터를 제공합니다. 이 스타터를 사용하는 가장 간단하고 선호되는 방법은 (Eclipse, IntelliJ, NetBeans 등의) IDE 통합을 사용하여 Spring Initializr를 사용하거나 [start.spring.io](https://start.spring.io/)를 통해 프로젝트를 생성하는 것입니다.

또는 다음과 같이 스타터를 수동으로 추가할 수 있습니다:
<p class="codeblock-label">build.gradle</p>

```groovy
dependencies {
    compile "org.springframework.boot:spring-boot-starter-security"
}
```

Spring Boot는 의존성 버전을 관리하기 위해 Maven BOM을 제공하므로 버전을 따로 명시할 필요가 없습니다. 그러나 Spring Security 버전을 덮어쓰고 싶다면 다음과 같이 Gradle 속성을 제공할 수 있습니다:
<p class="codeblock-label">build.gradle</p>

```groovy
ext['spring-security.version']='6.1.2'
```

Spring Security에서는 주요 릴리스에서만 호환성을 깨는 변경 사항이 발생하므로, Spring Boot와 함께 새로운 버전의 Spring Security를 안전하게 사용할 수 있습니다. 그러나 때때로, Spring Framework의 버전도 업데이트해야 할 수도 있습니다. 이렇게 하려면 Gradle 속성을 추가하십시오:
<p class="codeblock-label">build.gradle</p>

```groovy
ext['spring.version']='6.0.11'
```

추가 기능(예: LDAP, OAuth 2 등)을 사용하려면 적절한 [프로젝트 모듈과 의존성](https://docs.spring.io/spring-security/reference/modules.html#modules)도 포함해야 합니다.

### Spring Boot 없이 Gradle 사용하기

Spring Boot 없이 Spring Security를 사용할 때, 권장되는 방법은 Spring Security의 BOM을 사용하여 프로젝트 전체에서 일관된 버전의 Spring Security가 사용되도록 하는 것입니다. [Dependency Management](https://github.com/spring-gradle-plugins/dependency-management-plugin) 플러그인을 사용하여 이렇게 할 수 있습니다:
<p class="codeblock-label">build.gradle</p>

```groovy
plugins {
	id "io.spring.dependency-management" version "1.0.6.RELEASE"
}

dependencyManagement {
	imports {
		mavenBom 'org.springframework.security:spring-security-bom:6.1.2'
	}
}
```

최소한의 Spring Security Maven 의존성 세트는 일반적으로 다음과 같이 구성됩니다:
<p class="codeblock-label">build.gradle</p>

```groovy
dependencies {
	compile "org.springframework.security:spring-security-web"
	compile "org.springframework.security:spring-security-config"
}
```