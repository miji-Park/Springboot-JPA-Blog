package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

//import com.cos.blog.config.auth.PrincipalDetailService;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것

@Configuration // 빈등록 (IoC관리)
@EnableWebSecurity // 시큐리티에 필터가 등록이 된다.= 이미 활성화된 스프링 시큐리티의 어떤 설정을 해당 파일(SecurityConfig)에서 하겠다 
//Controller에서 특정 권한이 있는 유저만 접근을 허용하려면 @PreAuthorize 어노테이션을 사용하는데, 해당 어노테이션을 활성화 시키는 어노테이션이다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
//위 3개 어노테이션은 세트
public class SecurityConfig{
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean // IoC가 된다
	public BCryptPasswordEncoder encodePWD() {
		
		//String encPassword = new BCryptPasswordEncoder().encode("1234"); //이런식으로 1234를 넣으면 암호화해서 encPassword에 넣어줘
		return new BCryptPasswordEncoder();   //시큐리티가 들고있는 함수=> 원래 기존 함수라는건지 IoC에 등록해서 된다는건지 찾아보자
	}
	
	
	// 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데 
	// 해당 password가 뭘로 해쉬가 되어 회원가입이 되었는지알아야 
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음.
	 
//	 @Bean 
//	 protected void configure(AuthenticationManagerBuilder auth) throws Exception { //패스워드 비교를 못해서 꼭 이걸 만들어줘야해
//	 auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
//	 //222.principalDetailService가 로그인 요청을 하고 리턴이 되면 passwordEncoder로 해서 사용자가 적은패스워드를 encodePWD()로 암호화 후 DB랑 비교 //비교가 끝나서 다 맞으면 스프링 시큐리티영역에 우리 유저 정보가principalDetail로 감싸져서 저장이 된다
//	 }

	
	@Bean
	 public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()  //csrf 토큰 비활성화 (홈페이지가 완성된게 아니라 테스트 중이니까 걸어두는 게 좋음)
			.authorizeRequests() //요청이 들어오면
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**") //이런 주소가 들어오면
				.permitAll()	//요청을 허용하고
				.anyRequest()   //이게 아닌 다른 모든 요청은
				.authenticated()  //인증이 돼야해라는 필터링 
			.and()
				.formLogin() 
				.loginPage("/auth/loginForm") //인증이 필요한 곳으로 요청이 오면 유저컨트롤러에서 페이지 리턴
				.loginProcessingUrl("/auth/loginProc") //11111. 로그인 요청이 오는 순간 얘가 가로채서 PrincipalDetailService에 있는 loadUserByUsername한테 던져 
				.defaultSuccessUrl("/"); // 스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인 해준다.
										//3333333333.로그인이 완료되면 /로 이동=>보드컨트롤러
		 return http.build();
	}
}