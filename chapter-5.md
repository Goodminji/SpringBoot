---
description: 구성속성
---

# chapter-5

1. 구성 속성

* 빈 연결 : 빈으로 생성되는 애플리케이션 컴포넌트 및 상호 간에 주입되는 방법을 선언하는 구성
* 속성 주입 : 빈의 속성값을 설정하는 구성
* @bean 어노테이션이 지정된 메서드는 사용하는 빈의 인스턴스를 생성하고 속성값을 주입 

2. 데이터 소스 구성하기

* MySLQ 데이터 베이스를 사용한다면 application.yml 파일에 추가

```text
spring:
    datasource:
        url:jdbc:mysql://localhost/tacocloud
        username:tacodb
        password:tacopassword
        driver-class-name:com.mysql.jdbc.Driver
```

* 위 데이터소스 빈을 자동-구성할때 스프링 부트가 이런 속성 설정을 연결 데이터로 사용한다. 톰캣의 JDBC 커넥션 풀을 classpath에서 자동으로 찾을 수 있다면 datasource빈이 그것을 사용. 그렇지 않다면 스프링부트는  hikaricp, commons dbcp2 중 커넥션 풀을 classpath에서 찾아 사용
* 애플리케이션 시작될때 데이터베이스 초기화하는 SQL 실행방법 : spring.datasourde.schema , spring.datasource.data
* JNDI\(java Naming and directory interface\) 구성은  spring.datasource.jndi-name 속성 사용

3. 내장 서버 구성하기

* server.port 속성 사용하여 서블릿 컨테이너의 포트를 설정
* https 요청을 처리하기 위해서는 keytool ,
  * jdk의 keytool 명령행 유틸리티를 사용하여 keystore 먼저 생성\(실행되면 저장 위치 등 여러 정보 입력받음\) 
  * 키스토어 끝난후 아래 내용을 application.properties or application.yml 파일에 저

```text
server:
    port:8443
    ssl:
        key-store: file://path/to/mykeys.jks //키스토어 파일 생성된 경로
        key-store-password:letemein //키스토어를 생성할때 지정했던 비밀번
        key-password:letemein
```

4. 로깅 구성하기

* 로깅구성은 classpath의 루트\(src/main/resource\)에 logback.xml 파일 설정.그러나 스프링 부트는 파일 생성 필요 없다. application.yml에 지정 , 스프링 부트는 기본적으로 로그 파일이 10MB가득 차게 되면 새로운 로그파일이 생성되는데 스프링2.0부터는 날짜 별로 로그 파일이 남으며 지정된 일 수가 지난 로그 파일은 삭제 된다.

```text
logging:
    path:/var/logs
    file:TacoCloud.log
    level:
        root:WARN
        org:
            springframework:
                security:DEBUG
```

5. 다른 속성의 값 가져오기

* ${}를 사용해서 greeting.welcome을 설정.

```text
greeting:
    welcome: ${spring.application.name}
```

7. 구성 속성 설정 방법 

```text
@ConfigurationProperties(prefix="taco.orders")//구성 속성설정
//pagesize 속성 구성값을 사용 하려면 taco.orders.pageSize 이름으로 사용해야 한다.
public class OrderController {

	private int pageSize = 20;
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	private OrderRepository orderRepo;
	
	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}
	.......
```

* 기본값은 20이다. 하지만 application.yml에 속성을 설정하면 쉽게 변경 가능 

```text
taco:
  orders:
    pageSize: 10
```

8. 구성 속성 홀더 정의 하기 

* @ConfigurationProperties - 구성 데이터의 홀더로 사용되는 빈에 지정되는 경우가 많다. 컨트롤러와 이외의 다른 애플리케이션 클래스 외부에 구성 관련정보를 따로 유지할 수 잇꼬 여러 빈에 공통적인 구성 속성을 쉽게 공유 가능.

```text
@Component// 스프링 컴토넌트 검색에서 자동으로 스프링 애플리케이션 컨텍스트 빈 생성해준다.
@ConfigurationProperties(prefix="taco.orders")
@Data
public class OrderProps {

	 private int pageSize = 20;

}
```

9. profile

```text
	@Profile({"dev","qa"}) 
	// profile - 프로덕션 환경에서 애플리케이션이 시작될때마다 데이터 로드되는것을 방지
	//        - dev, qa,  프로파일 중 하나가 활성화가 될때 아래 메소드가 빈이 생김(개발환경에서는 dev 활성화)
	// @Profile("!prod") prod 파일이 활성화 되지 않을경우 빈이 생성
```

