package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repositoty.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌=IoC를 해준다(메모리에 대신 띄워줌)
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); //1234 입력하는 패스워드
		String encPassword = encoder.encode(rawPassword); //입력한 패스워드가 해쉬값으로 변경됨
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
		
//		try {
//			
//			userRepository.save(user);
//			return 1;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("UserService: 회원가입()" +e.getMessage());
//		}
//		
//		return -1;
		
	}
	
	//전통적인 로그인처리
//	@Transactional(readOnly = true)//Select할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(정합성)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}
