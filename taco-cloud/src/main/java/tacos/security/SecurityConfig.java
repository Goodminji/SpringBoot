package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
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
		/* 인메모리사용자스토어
		 * auth.inMemoryAuthentication() // -> 이 메소드 사용하여 보안 구성자체에 사용자 정보 직접 지정
		 * .withUser("user1")//사용자 이름 .password("{noop}password1")//비밀번호
		 * .authorities("ROLE_USER")//권한 .and() .withUser("user2") // * 스프링5부터 패스워드 메소드
		 * 사용하여 암호화를 하지 않으면 접근거부(HTTP 403) , 또는 인터넷 서버 오류 (HTTP 500 발생) //{noop} 지정하면
		 * 비밀번호 암호화가 안된다 .password("{noop}password2") .authorities("ROLE_USER");
		 */
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery(
				"select username, password, enabled from users" + 
			   "where username=?"	
			 )
			.authoritiesByUsernameQuery(
					"select username,authority from authorities"+
			   "where username=?"
			)
			.passwordEncoder(new NoEncodingPasswordEncoder());
		
	}
}
