# REST-API Server

---
구현내용
- JDK version.17 학습
- Spring Boot 2.7 학습
- REST-API Server 구현 학습
- OAuth2.0 로그인 구현 학습
- Security 학습


FunctionalInterface, Stream, Optional 관련 블로그
https://mangkyu.tistory.com/20

FunctionalInterface
- Function
- Consumer
- Predicate
- Supplier

Stream      
일회성 객체.

Optional        
Stream과 엮어서 사용하기 위해서 도입된 Null Safety한 Wrapper.

JUnit5      
https://donghyeon.dev/junit/2021/04/11/JUnit5-%EC%99%84%EB%B2%BD-%EA%B0%80%EC%9D%B4%EB%93%9C/

### 기본 비지니스 로직 룰.
지금 이 프로젝트에선 DTO랑 VO의 구분이 없음.        
기본적인 VO 역할을 하는 객체는 테이블 명을 가진 객체가 유일하며,      
DTO의 역할을 가진 객체는 뒤에 Params가 붙인 파라미터 객체가 유일하다.        
즉, VO = Entity, DTO = {Object}Params        
근데 사실상 VO랑 DTO를 구분하고 사용하진 않는다.      
외부에서 값을 받는다. = {Object}Params.      
DB에서 값을 받는다. = Entity.      
와 같은 개념으로 사용한다.    
그리고 기본적으로 Entity와 {Object}Params의 경우에는 특별한 경우가 아니면 서비스 내에서 setter를 사용하여 값의 변형을 주지 않는다.