### 하이스트릭스(Hystrix)

concurrency 동작중에서 Reactive Stream 동작보다가 넷플릭스(Netflix)의 무슨 뭐가 있다고해서 번역문 작성.      

> [원문](https://github.com/Netflix/Hystrix/wiki)

---

#### 하이스트릭스(Hystrix)란?

분산 환경에서 불가피하게 많은 서비스 의존성 중 일부가 실패하게 됩니다. 하이스트릭스는 지연 허용 및 장애 허용 로직을 추가하여 분산 서비스 간 상호 작용을 제어 할 수 있게 도와주는 라이브러리입니다.

하이스트릭스는 서비스 간 액세스 지점을 격리하여 서비스 간의 연쇄적인 장애를 멈추고 대안 옵션을 제공하여 전반적인 내구성을 향상시키는 기능을 수행합니다.

이를 통해 하이스트릭스는 분산 환경에서 서비스 간 상호 작용을 보다 안정적으로 유지하고, 장애가 발생할 경우 그 영향을 최소화하며 사용자에게 우수한 사용 경험을 제공하는데 도움이 됩니다.        

#### 하이스트릭스(Hystrix)의 역사

하이스트릭스는 Netflix API 팀이 2011년에 시작한 내구성 엔지니어링 작업에서 발전되었습니다. 2012년에는 하이스트릭스가 계속 발전하고 성숙해지면서 Netflix의 많은 팀에서 사용하기 시작했습니다.

오늘날 Netflix에서는 하이스트릭스를 통해 매일 수십 억 개의 스레드 격리 및 수십 억 개의 세마포어 격리 호출이 실행됩니다. 이로 인해 Netflix의 가동 시간과 내구성이 크게 개선되었습니다.     

하이스트릭스 및 이에 대한 대응 방안에 대한 자세한 정보는 다음 링크를 참조하실 수 있습니다:

- [Making Netflix API More Resilient](https://netflixtechblog.com/making-netflix-api-more-resilient-a8ec62159c2d)
- [Fault Tolerance in a High Volume, Distributed System](https://netflixtechblog.com/fault-tolerance-in-a-high-volume-distributed-system-91ab4faae74a)
- [Performance and Fault Tolerance for the Netflix API](https://speakerdeck.com/benjchristensen/performance-and-fault-tolerance-for-the-netflix-api-august-2012)
- [Application Resilience in a Service-oriented Architecture](http://radar.oreilly.com/2013/06/application-resilience-in-a-service-oriented-architecture.html)
- [Application Resilience Engineering & Operations at Netflix](https://speakerdeck.com/benjchristensen/application-resilience-engineering-and-operations-at-netflix)


#### 하이스트릭스(Hystrix)의 설계 목적

- 제 3자 클라이언트 라이브러리를 통해 (일반적으로 네트워크를 통해) 액세스 된 의존성으로부터 지연 및 장애로부터 보호 및 제어를 제공합니다.
- 복잡한 분산 시스템에서 연쇄적인 장애를 방지합니다.
- 빠르게 실패하고 신속하게 복구합니다.
- 가능한 경우 대안 및 우아하게 감쇠합니다.
- 대기 시간이 거의 없는 모니터링, 경보 및 운영 제어를 가능하게 합니다.

#### Hystrix는 어떤 문제를 해결합니까?     

하이스트릭스(Hystrix)는 복잡한 분산 아키텍처에서 애플리케이션이 수십 개의 의존성을 가지고 있으며, 그 중 어느 하나라도 반드시 언젠가는 실패할 것임을 해결하기 위해 설계되었습니다. 만약 호스트 애플리케이션이 이러한 외부 장애로부터 격리되어 있지 않으면, 외부 장애로 인해 애플리케이션이 함께 다운될 위험이 있습니다.

한 예로, 하나의 애플리케이션이 30개의 서비스에 의존하고, 각 서비스마다 99.99%의 가동률을 가지고 있다고 가정해보겠습니다. 이 경우에는 다음과 같은 결과를 예상할 수 있습니다:

- 99.99%^30 = 99.7%의 가동률
- 10억 요청 중 0.3%에 해당하는 3백만 건의 실패가 발생합니다.
- 모든 종속성이 높은 가동률을 유지하더라도, 매월 2시간 이상의 다운타임이 발생할 수 있습니다.

하지만 실제로는 이보다 더 좋지 않은 상황이 벌어질 가능성이 큽니다.

모든 종속성이 잘 동작하더라도, 수십개의 서비스 각각에서 0.01%의 다운타임이 발생하면, 전체 시스템에서 심각한 다운타임이 발생할 수 있습니다. 이러한 다운타임을 방지하기 위해서는 전체 시스템이 탄력적인 구조로 설계되어야 합니다. 이를 위해서 하이스트릭스(Hystrix)와 같은 도구를 사용하여, 시스템이 실패에 대응하는 방식을 우아하게 처리함으로써 전체 시스템의 영향을 최소화할 수 있습니다. 결국 전체 시스템이 신뢰성을 유지할 수 있도록 구조화하는 것이 중요합니다.

모든 것이 정상적으로 동작할 때, 요청 흐름은 다음과 같이 구성될 수 있습니다.        
![정상처리](./image/image1.png)
하지만 백엔드 시스템 중 하나가 느려지게 되면, 전체 사용자 요청이 차단될 수 있습니다.
![오류발생_1](./image/image2.png)

높은 트래픽량에서는 하나의 백엔드 종속성이 느려지면, 모든 서버에서 몇 초 안에 모든 리소스가 포화되어 버리는 문제가 발생할 수 있습니다. 이러한 상황은 서버에서 다른 요청도 처리하지 못하게 만들어 전체 시스템의 성능 저하를 초래하게 됩니다.

애플리케이션에서 네트워크에 연결하거나 네트워크 요청을 유발할 가능성이 있는 클라이언트 라이브러리 등의 모든 지점은 잠재적인 실패 요소입니다. 더 심각한 것은, 이러한 애플리케이션은 서비스 간 딜레이(latency)가 증가되어 큐, 스레드 및 기타 시스템 리소스가 백업되어 전체 시스템에서 더 많은 장애가 발생할 수도 있습니다.

따라서 시스템 전반에 걸쳐 장애 확산이 일어나지 않도록 하기 위해서는, 이러한 지점들에서 실패가 발생하더라도 시스템이 안정적으로 운영될 수 있도록 보호 및 격리 처리가 필요합니다. 이를 위해, 서킷 브레이커와 같은 실패 처리 기법이 적용되고, 서비스 구조가 탄력적인 구조로 설계되어야 합니다.
![오류발생_2](./image/image3.png)

이러한 문제는, 네트워크 액세스가 제 3자 클라이언트를 통해 수행될 때 더욱 심화됩니다. 이 경우 "블랙 박스"로 구현된 제 3자 클라이언트를 사용하게 되는데, 이는 구현 세부 정보가 숨겨져 있으며 언제든지 변경될 수 있다는 문제가 있습니다. 또한, 각 클라이언트 라이브러리마다 네트워크 또는 리소스 구성도 다르며, 이러한 구성은 모니터링 및 변경하기가 어려울 수 있습니다. 이를 해결하기 위해서는, 다양한 종속성에 대한 제어권과 모니터링이 필요합니다. 결국 제 3자 클라이언트를 사용하는 경우에는, 해당 라이브러리가 미치는 영향을 잘 파악하고 테스트 및 검증을 수행하여 안정성을 유지해야 합니다.      

이러한 간접 종속성에서 발생하는 문제는 직접적인 종속성에서 발생하는 문제보다 찾아내기가 어렵고, 대처하기도 어렵습니다. 이러한 종속성은 애플리케이션 실행 시점에 이미 로딩되어 작동하기 때문입니다. 따라서 종속성 관리를 철저히 하고, 필요하지 않은 종속성은 제거하고, 필요한 종속성이라면 해당 종속성에 대해 모니터링과 버전 관리를 철저히 수행하여 안정성을 유지해야 합니다.

네트워크 연결이 실패하거나 성능 저하가 발생할 수 있습니다. 서비스 및 서버가 다운되거나 지연될 수 있습니다. 새로운 라이브러리나 서비스 배포가 동작 및 성능 특성을 변경할 수 있습니다. 클라이언트 라이브러리에 버그가 있을 수 있습니다. 이러한 문제들로 인해 전체 서비스 시스템이 더 이상 정상적으로 작동하지 않거나 일부 기능이 막힐 수 있습니다. 따라서 시스템 구성 및 종속성에 대한 중앙 집중적인 모니터링 및 예방 절차를 마련해야 합니다.

따라서, 이러한 문제들은 하나의 실패하는 종속성이 전체 애플리케이션 또는 시스템을 다운시키지 않도록 분리 및 관리해야 합니다. 이를 위해서는, 여러 가지 예방 및 복구 절차를 마련하고, 애플리케이션이나 시스템 내에서 어떠한 부분에서 문제가 발생했는지 빠르게 식별하고 대처해야 합니다.

#### Hystrix의 디자인 원칙은 다음과 같습니다.

1. 단일 종속성이 모든 컨테이너 (예 : Tomcat) 사용자 스레드를 사용하는 것을 방지합니다.
2. 대기열로 처리하지 않고 로드 쉐딩 및 빠른 실패를 통해 장애 정책을 수행합니다.
3. 실패에 대한 보호를 위해 가능한 경우 대체 기능을 제공합니다.
4. `Bulkhead`, `Swimlane` 및 `Circuit Breaker 패턴`과 같은 격리 기술을 사용하여 단일 종속성의 영향을 제한합니다.
5. 거의 실시간 메트릭, 모니터링 및 경고를 통해 탐지 시간을 최적화합니다.
6. 대부분의 Hystrix 측면에서 동적 속성 변경 지원 및 낮은 지연 피드백 루프를 통해 구성 변경의 대역폭을 높입니다.
7. 네트워크 트래픽이 아닌 종속성 클라이언트 실행 전체에서 장애에 대한 보호를 제공합니다.

#### Hystrix는 다음과 같은 방법으로 목표를 달성합니다.

1. 모든 외부 시스템 또는 "종속성" 호출을 `HystrixCommand` 또는 `HystrixObservableCommand` 객체로 래핑합니다. 이 객체는 일반적으로 별도의 스레드에서 실행됩니다(command pattern).
2. 사용자가 정의한 임계 값보다 오래 걸리는 호출은 타임아웃합니다. 기본값은 있지만 대부분의 종속성에 대해 각각이 측정된 99.5% 기준보다 약간 높은 시간으로 타임아웃 값을 정의하기 위해 "프로퍼티"를 사용하여 커스텀할 수 있습니다.
3. 각 종속성에 대해 작은 스레드풀 또는 세마포어를 유지합니다. 스레드풀 또는 세마포어가 꽉 차면 해당 종속성에 대한 요청이 대기열에 추가되는 대신 즉시 거부됩니다.
4. 성공, 실패(클라이언트에서 발생한 예외), 타임아웃 및 스레드 거부를 측정합니다.
5. 일정 기간 동안 특정 서비스로의 모든 요청을 중단하기 위해 회로 차단기를 작동시킵니다. 서비스의 오류 비율이 임계 값을 초과하면 수동으로 또는 자동으로 회로 차단이 작동합니다.
6. 요청이 실패, 거부, 타임아웃 또는 숏서킷일 때 대체 로직을 수행합니다.
7. 거의 실시간으로 메트릭 및 구성 변경을 모니터링합니다.

Hystrix를 사용하여 각 하위 종속성을 래핑하면, 위의 다이어그램과 같은 아키텍처는 다음 다이어그램과 같이 변경됩니다. 각 종속성은 다른 종속성으로부터 격리되며, 지연이 발생할 때 포화 할 수있는 리소스가 제한되며, 종속성에서 어떤 종류의 실패가 발생 할 때 어떤 응답을 보낼지 결정하는 대안 로직으로 덮여 있습니다.
![리팩토링](./image/image4.png)

Hystrix가 [작동하는 방식](https://github.com/Netflix/Hystrix/wiki/How-it-Works)과 [사용 방법](https://github.com/Netflix/Hystrix/wiki/How-To-Use)