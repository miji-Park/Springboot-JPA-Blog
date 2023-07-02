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
//	@Transactional(readOnly = true)//Select할때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료(이 때까지 정합성 유지시킨다)
//	public User 로그인(User user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	
	@Transactional
	public void 회원수정(User user) { //이 유저는 외부로부터 받은 유저
		// 수정시에는 영속성 컨텍스트 User 오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정
		// select를 해서 User오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서!!
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려주거든요.
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{ //null값이라 못 찾을 수도 있으니까
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		//유저를 찾았으면 persistance에 오브젝트가 들어와있겠지
		String rawPassword = user.getPassword(); //사용자가 다시 입력한 패스워드
		String encPassword = encoder.encode(rawPassword); //그 패스워드를 암호화
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
		//회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 됨
		// 영속화 된 persistance 객체의 변화가 감지되면 더티체킹이 되어 변화된 것들을 update문을 날려줘 
	}

}
