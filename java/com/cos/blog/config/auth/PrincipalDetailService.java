package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repositoty.UserRepository;

@Service //빈 등록
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	//스프링이 로그인 요청을 가로챌 때 username, password 변수 2개를 가로채는데 
	//password 부분처리는 알아서 함
	//username이 DB에 있는지만 확인해주면 됨. =>확인을 loadUserByUsername 이 함수가 함
	@Override //밑에꺼 컨트롤+스페이스 치니까 나왔어
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User principal = userRepository.findByUsername(username)
			.orElseThrow(()->{
				return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다.:" + username);
			});
		return new PrincipalDetail(principal); //시큐리티 세션에 유저 정보가 저장이 됨. 이떄 타입이 UserDetails타입이 되는거지 implements했으니까
		//이걸 오버라이딩해서 안 만들면 아이디:user, 패스워드:콘솔창에 뜬거 로 들어가지 우리 유저값이 안 들어가 
	}
}
