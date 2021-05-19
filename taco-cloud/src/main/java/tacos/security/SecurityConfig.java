package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	// 두개 configure() 메소드 오버라이딩 
	// HttpSecurity 는 HTTP 보안을 구성 
	@Override
	protected void configure(HttpSecurity http)
	 throws Exception{ 
		http.authorizeRequests() 
		.antMatchers("/design", "/orders")
		 .access("hasRole('ROLE_USER')") 
		 .antMatchers("/","/**")
		 .access("permitAll")
		 .and() 
		 .formLogin()//로그인 폼 구성
		 .loginPage("/login")
		 .defaultSuccessUrl("/design", true)//로그인 성공시 기본 홈페이로 이동 
		 .and()
		 .logout()
		 .logoutSuccessUrl("/")// 기본적으로 로그인 페이지로 다시 이동;
		 .and()
		 .csrf()
		 //.disable()// csrf 비 활성화시 사용
		 ;
	 }
	 
	@Bean// BCryptPasswordEncoder 인스컨스가 스프링 애플리케이션 컨텍스트에 등록 , 관리 되며 이 인스턴스가 컨텍스트로부터 주입되어 반환.
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		 auth.userDetailsService(userDetailsService)
		 .passwordEncoder(encoder());
	 }
	// AuthenticationManagerBuilder 사용자 인증 정보 구성
	/*	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		/* 인메모리사용자스토어
		 * auth.inMemoryAuthentication() // -> 이 메소드 사용하여 보안 구성자체에 사용자 정보 직접 지정
		 * .withUser("user1")//사용자 이름 .password("{noop}password1")//비밀번호
		 * .authorities("ROLE_USER")//권한 .and() .withUser("user2") // * 스프링5부터 패스워드 메소드
		 * 사용하여 암호화를 하지 않으면 접근거부(HTTP 403) , 또는 인터넷 서버 오류 (HTTP 500 발생) //{noop} 지정하면
		 * 비밀번호 암호화가 안된다 .password("{noop}password2") .authorities("ROLE_USER");
		 */
		/*
		 * auth.jdbcAuthentication() .dataSource(dataSource) .usersByUsernameQuery(
		 * "select username, password, enabled from users" + "where username=?" )
		 * .authoritiesByUsernameQuery( "select username,authority from authorities"+
		 * "where username=?" ) .passwordEncoder(new NoEncodingPasswordEncoder());
		 */
	/*	auth.ldapAuthentication()
			.userSearchBase("ou=people")//사용자 검색 기준 설정
			.userSearchFilter("(uid={0}")
			.groupSearchBase("ou=groups")//그룹 검색 기준 설정
			.groupSearchFilter("member={0}")
			.contextSource()
			.root("dc=tacoCloud,dc=com")
			.ldif("classpath:users.ldif")//ldap 서버가 시작될때는 ldif(ldap data interchange format) 파일로부터 데이터 로드
			.and()
			.passwordCompare()//비밀번호 비교
			.passwordEncoder(new BCryptPasswordEncoder())// 암호화
			.passwordAttribute("userPasscode");// 비밀번호 속성값
			
	}*/
}
