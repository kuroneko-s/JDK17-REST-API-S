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

- 이 문서를 읽어보십시오. [공식문서] [0]
- 다양한 샘플 애플리케이션 중 하나를 시도해 보십시오.
- spring-security 태그가 있는 [스택오버플로우] [1]에서 질문을 하십시오.
- [깃허브 커뮤니티] [2]에서 버그 및 기능 향상 요청을 보고하십시오.

[0]: https://docs.spring.io/spring-security/reference/community.html
[1]: https://stackoverflow.com
[2]: https://github.com/spring-projects/spring-security/issues

--- 

## 참여하기
Spring Security 프로젝트에서 여러분들의 참여를 환영합니다. 스택 오버플로우에 대한 질문에 답변하거나, 새로운 코드를 작성하거나, 기존 코드를 개선하거나, 문서 작성에 도움을주거나, 샘플 또는 튜토리얼 개발, 버그 보고 또는 단순히 제안하는 것 등 여러 방법으로 기여할 수 있습니다. 더 자세한 정보는 기여 섹션을 참조하십시오.

---

## 소스 코드
Spring Security의 소스 코드는 GitHub에서 찾을 수 있습니다. [깃허브] [3] 

[3]: https://github.com/spring-projects/spring-security/

---

## Apache 2 라이선스
Spring Security는 Apache 2.0 라이선스로 출시된 오픈 소스 소프트웨어입니다.

---

## 소셜 미디어
최신 뉴스를 살펴보려면 트위터에서 [@SpringSecurity] [4] 및 [Spring Security] [5] 팀을 팔로우하실 수 있습니다. 전체 Spring 포트폴리오를 최신 상태로 유지하려면 [@SpringCentral] [6]을 팔로우하십시오.

[4]: https://twitter.com/SpringSecurity
[5]: https://twitter.com/i/lists/75099534
[6]: https://twitter.com/SpringCentral

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

Spring Security 팀은 Spring Security 6.0으로 업그레이드를 간단히 하는 5.8 릴리스를 준비했습니다. 5.8 및 이에 대한 준비 단계를 사용하여 6.0으로 간단히 업데이트하세요. [5.8버전] [7]

5.8로 업데이트 한 후, 이 가이드를 따라 마이그레이션 또는 정리 작업을 완료하세요.

어려움에 봉착하는 경우, 준비 가이드에는 5.x 동작으로 되돌리는 opt-out 단계가 포함되어 있습니다.

[7]: https://docs.spring.io/spring-security/reference/5.8/migration/index.html

## Spring Security 6.0으로 업데이트하기

첫 번째 단계는 Spring Boot 3.0의 최신 패치 릴리스를 사용하는지 확인하는 것입니다. 다음으로, Spring Security 6.0의 최신 패치 릴리스를 사용하는지 확인해야 합니다. Spring Security 6.0으로 업데이트하는 방법은 참조 가이드의 [Getting Spring Security](##Getting Spring Security) 섹션을 참고하시기 바랍니다.

## 패키지 이름 업데이트

Spring Security 6.0 버전 이상으로 업데이트한 경우, 모든 javax 패키지 import 문을 해당 jakarta 패키지의 대응되는 import 문으로 대체해야 합니다

## 애플리케이션별로 수행할 작업

[Servlet](###서블릿 마이그레이션) 또는 [Reactive](#Reactive) 애플리케이션인지에 따라 수행해야 할 특정 작업이 있습니다.



### 서블릿 마이그레이션


### Reactive


---



## Getting Spring Security