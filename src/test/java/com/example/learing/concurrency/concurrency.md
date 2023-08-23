### [Java Concurrency Evolution](https://homoefficio.github.io/2020/12/11/Java-Concurrency-Evolution/)

> [원문 (영어)](https://dzone.com/articles/java-concurrency-evolution)

---

초기에 가상 스레드를 사용하다가 OS 스레드로 변경되어 사용되었다가 프로젝트 룸이 활성화 되면서 다시금 가상 스레드가 주류로 올라섰다.

프로젝트 룸(Loom)
0. [공식문서](https://cr.openjdk.org/~rpressler/loom/Loom-Proposal.html)
1. [블로그_1](https://velog.io/@recordsbeat/Project-Loom-%EC%83%88%EB%A1%9C%EC%9A%B4-%ED%8C%A8%EB%9F%AC%EB%8B%A4%EC%9E%84%EC%9D%BC%EA%B9%8C)
2. [블로그_2](http://guruma.github.io/posts/2018-09-27-Project-Loom-Fiber-And-Continuation/)

#### 프로젝트 룸 (Project Loom)

[Countinuation](https://en.wikipedia.org/wiki/Continuation)과 Scheduler를 사용한 [Fiber](https://ko.wikipedia.org/wiki/%ED%8C%8C%EC%9D%B4%EB%B2%84_(%EC%BB%B4%ED%93%A8%ED%84%B0_%EA%B3%BC%ED%95%99)) 경량 스레드를 제공.     
가장 최근에는 Vertual Thread를 제고앟여 기존에 쓰던 Thread와 크게 다르지 않게 사용하나, 내부적으로는 경량 스레드의 장점을 취할 수 있다.     

#### 중량 스레드         

웹 어플리케이션들은 요청당 1개의 스레드를 배정해서 처리한다.        
블로킹 처리방식을 사용하기에 요청이 완료되기 전까지는 재할당이 불가능하다.        
OS 레벨의 스레드는 [무겁기 때문에](#OS_레벨의_스레드의_단점.) 스레드 풀을 사용하는 방식을 선택해왔다. (한계점 존재)

> #### OS 레벨의 스레드의 단점.
> 1. 리소스 부하가 크다.
>    - 리소스가 커서 서버당 수천개 정도가 한계이다. 그에 반해 소켓은 수백만개 생성할 수 있다. 웹서버는 하나의 스레드가 하나의 Rquest(요청)을 처리하기에 동시처리 갯수가 월등이 떨어진다. 
> 2. 동시성 작업 단위가 크다.
>    - 스레드의 비용이 크기때문에 가볍게 쓰고 버리기엔 부담이 크다. 그렇다보니 큰 작업을 Pooling 한 후 재사용하는 식으로 사용하게 된다. 하지만 요청이 모두 큰 요청만 있다는 법이 없다보니 이러한 문제가 나타난다.
> 3. 선점형 스케줄러이다.  
>    - OS가 제공하는 커널 스레드는 선점형 스케줄러에 의해 처리된다. 스레드에서 스레드로 제어(CPU 할당)가 넘어가는 문맥전환(Context Switch)이 전적으로 OS 담당이다. 그렇다보니 프로그래머는 언제 제어가 넘어가는지에 대해서 알수가 없다. 더욱이 제어가 되는 시점이 코드 레벨이 컴파일된 코드 레벨에서 이뤄지기에 더더욱 알기 힘들다.
>      그렇다보니 여러 개념들을 사용하게 된다. (세마포어, 뮤텍스, 크리티컬 섹션 등)       
>      - 세마포어 - 공유된 자원의 데이터 혹은 임계영역(Critical Section) 등에 여러 Process 혹은 Thread가 접근하는 것을 막아줌 (하나이상)       
>      - 뮤텍스 - 공유된 자원의 데이터 혹은 임계영역(Critical Section) 등에 하나의 Process 혹은 Thread가 접근하는 것을 막아줌 (하나)
>      - 임계영역(Critical Section) - 여러 프로세스가 데이터를 공유하면서 수행될 때 각 프로세스에서 공유 데이터를 접근(Access)하는 프로그램 코드 부분을 가르킨다.

OS 스레드는 커널이 스케줄링을 담당하기에 예측이 불가하며 값비싼 문맥교환이(무언가들 주고받음) 일어난다.             
(기존 자바(가상 스레드 방식)는 OS 독립적인 프로그래밍을 위하여 스레드 스케줄링은 concurrent 패키지 등에 위임하는 것을 권장했다.)

> 참고. [Fiber와 Continuation](http://guruma.github.io/posts/2018-09-27-Project-Loom-Fiber-And-Continuation/)      

---
### Fiber와 Continuation

OS 레벨의 스레드를 대체하는 것이 경량형 스레드이다.      
문제라고 대두되었던 모든 내용들과 상반되어 있다.     
자바는 그중에서 Fiber를 채택했다.       
이를 기반으로 나온 프레임워크들은 다음과 같다.      

- [Vert.x](https://vertx.io/)
- [akka](https://akka.io/)
- [RxJava](https://github.com/ReactiveX/RxJava)
- [Quasar](http://docs.paralleluniverse.co/quasar/)(Project Loom 제안자)

이로써 자바에는 경량 스레드를 정식 지원하게 됬다. (경량 스레드 지원 프로젝트 이름이 Project Loom인거네.)

- 매우 적은 리소스.
    - 수백 바이트 정도.
    - 스위칭 오버헤드는 거의 제로 수준.
    - 하나의 JVM에서 수백만 개 생성 및 원활한 동작 가능.
- synchronous, blocking 콜 가능.
  - 성능때문에 비동기 코드 작성 필요 없음. (node의 콜백헬이 없다)
  - 동시성 프로그래밍이 단순해지며, 또한 손쉽게 규모 확장이 가능해진다.
- Fiber의 API들은 Thread 클래스와 거의 비슷.
  - 다만, Fiber를 중단/재시작하는 park/unpark 관련 메소드가 추가됨.
  - unpark 메소드는 인수로 스케줄러를 받을 수 있어서 fiber의 스케줄링을 바꿀 수 있다.
  - Thread와 공통되는 부분은 부모 클래스 Strand로 추출.
- Serializable
  - Fiber는 스토리지 저장 및 네트웍을 통한 전송이 가능해진다.
  - 이를 통해 데이터가 있는 곳에서 실행되는 함수(Function As Service)가 가능해진다.
  - Financial Transaction이나 실행 블록체인.
- Continuation
  - Fiber = Continuation + Scheduler
  - Scheduler는 훌륭하게 구현된 기존의 ForkJoinPool을 그대로 사용
  - Continuation(정확히는 Delimited Continuation)의 구현이 서브 과제
  - channel, actor, dataflow 등을 구현할 수 있다.
- UAI(Unwind And Invoke)
  - tail call

--- 

이미 자바의 비동기 개발방식 중 Reactive 프로그래밍을 통해 이의를 제기할 수 있지만 Project Loom은 이에 대해서도 문제점을 지적한다.

> 참고1. [블로그_3](https://gunsdevlog.blogspot.com/2020/09/reactive-streams-reactor-webflux.html)       
> 참고2. [YOUTUBE_스프링캠프](https://www.youtube.com/watch?v=2E_1yb8iLKk)   
> 참고3. [KAKAO_TECK](https://tech.kakao.com/2018/05/29/reactor-programming/)


### 