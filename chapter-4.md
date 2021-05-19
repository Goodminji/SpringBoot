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

