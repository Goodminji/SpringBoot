# 기초셋팅1장

1.STS Pom.xml

스트링부터 스타터 의존성

* 의존성을 선언하지 않아도 되므로 빌드 파일이 훨씬 작아지고 관리하기 쉬워짐
* 라이브러리 이름이 아닌 기능의 관점으로의 의존성
* 버전 걱정 NO
* 의존성에 지정된 모든 라이브러리가 실행가능 JAR 파일 포함 되어있는지 classpath 확인가

2.@SpringBootApplication

* 실행가능 JAR 파일에서 애플리케이션을 실행하므로 제일 먼저 시작되는 부트스크랩 클래스.
* 최소한의 스프링 구성 포함
* main\(\) 메서드 - JAR 파일이 실행될때 호출되는 실행되는 메서드\(run\(\) 호출\)

3.@SpringBootTest

* junit 스프링 부트 기능 테스트.

4. tomcat 설치 X

5. 기본 예 - 웹 예제 실행 - MockMvc 이용 Junit 실행 / localhost:8080 확인

![&#xC6F9; &#xC608;&#xC81C; &#xC2E4;&#xD589; - MockMvc &#xC774;&#xC6A9; Junit &#xC2E4;&#xD589; / localhost:8080 &#xD655;&#xC778;](.gitbook/assets/springboot_1.png)







 

