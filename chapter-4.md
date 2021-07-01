---
description: 스프링 시큐리티
---

# chapter-4

1. 시큐리티 기본 설정

```text

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	// 두개 configure() 메소드 오버라이딩 
	// HttpSecurity 는 HTTP 보안을 구성
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		    .antMatchers("/design", "/orders")
		    .access("hasRole('ROLE_USER')")
		    .antMatchers("/","/**").access("permitAll")
		    .and()
		    .httpBasic();
	}
	// AuthenticationManagerBuilder 사용자 인증 정보 구성
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		//인메모리사용자스토어
		auth.inMemoryAuthentication() // -> 이 메소드 사용하여 보안 구성자체에 사용자 정보 직접 지정
			.withUser("user1")//사용자 이름
			.password("{noop}password1")//비밀번호
			.authorities("ROLE_USER")//권한
			.and()
			.withUser("user2")
			 // * 스프링5부터 패스워드 메소드 사용하여 암호화를 하지 않으면 접근거부(HTTP 403) , 또는 인터넷 서버 오류 (HTTP 500 발생)
			 //{noop} 지정하면 비밀번호 암호화가 안된다
			.password("{noop}password2")
			.authorities("ROLE_USER");
	}
}

```

2. LDAP 기반 사용자 스토어

```text
protected void configure(HttpSecurity http) throws Exception{
auth.ldaoAuthentication
		    .userSearchFilter("(uid={0})")
				.groupSearchFilter("member={0}");
	}
```

* ldapAuthentication\(\) - ldap 기반 인증으로 스프링 시큐리티 구성\(jdbcAuthenticaton 처럼 사용\)
* userSearchFilter\(\) ,groupSearchFileter 메소드는 ldap 기본 쿼리의 필터를 제공하기 위해 사용
* 사용자 그룹모두 ldap 기본 쿼리가 비어 있어서 쿼리에 대한 검색이 ldap 계층의 루트부터 수행
* 기준점쿼리 지정 방법 - userSearchBase\(사용자 찾기 위한 기준점\), groupSearchBase\(그룹을 찾기 위한 기준점 쿼리 기준점\)

  ```text
  protected void configure(HttpSecurity http) throws Exception{
  auth.ldaoAuthentication
  				.userSearchBase("ou=peple")
  		    .userSearchFilter("(uid={0})")
  				.groupSearchBase("ou=groups")
  				.groupSearchFilter("member={0}");
  	}
  ```

* 비밀번호 비교 구성 방법은 passwordCompare\(\);

3. CSRF 공격 방어하기 \(cross-site Request Forgery\) 크로스 사이트 요청 위조

* 사용자가 웹 사이트에서 로그인한 상태에서 악의적인 코드가 삽입된 페이지를 열면 공격대상이 되는 웹사이트에 자동으로 폼이 제출되고 이 사이트는 위조된 공격 명령이 믿을 수 있는사용자로부터 제출 된것으로 판단하게 되어 공격으로 노출.
*  공격을 막기 위해서는 폼의 숨긴 필드에 넣을 CSRF 토큰을 생성.
* thymeleaf 를 스프링 시큐리티와 함께 사용중이라면 자동생성되므로 지정할 필요가 없다 &lt;form th:action&gt; - 자동으로 생성.

4. 인증된 사용자 정보는 @AuthenticationPrincipal 사용해서 컨트롤러에 주입 

5. SecurityContext : threadLocal 보관 되며 SecurityContextHolder 를 통해 사용 할 수 있음.

6. 

```text
public ApiResult<AuthenticationResultDto> authentication(@RequestBody AuthenticationRequest authRequest) throws UnauthorizedException {
    try {
      JwtAuthenticationToken authToken = new JwtAuthenticationToken(authRequest.getPrincipal(), authRequest.getCredentials());
      Authentication authentication = authenticationManager.authenticate(authToken);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      return OK(
        new AuthenticationResultDto((AuthenticationResult) authentication.getDetails())
      );
    } catch (AuthenticationException e) {
      throw new UnauthorizedException(e.getMessage());
    }
  }
```

* {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}에 대응되는 역할을 한다.
* HTTP Request-Body 에서 로그인 파라미터\(email, password\)를 추출하고 로그인 처리를
* {@link AuthenticationManager}로 위임한다.
* 실제 구현 클래스는 {@link org.springframework.security.authentication.ProviderManager}이다.

=&gt; 결국 

* sernamePasswordAuthenticationFilter 디폴트 인증 필터                   사용자 인증 요청을 Authentication 인터페이스를 추상화 하여 AuthenticationManager를 호출한다. \( 요청한 아이디,비밀번호 등이 포함되어 있음\(인증처리 전\)\)
* AuthenticationManager  사용자 아이디/비밀번호 인증 하기 위헤서 적절한 ProviderManager를 찾아 위임처리 한다.\( 
  * AuthenticationManager  에 ProviderManager가 리스트 형식으로 되어있어 .Support 로 찾아서 성공처리하는 provider를 찾음\)
* AuthenticationProvider  실질적으로 사용자 인증 처리를 하고 인증 결과를 Authentication으로 리턴 \( 인증 처리 정상 된 인증 내용을 담아서 옴\) 

