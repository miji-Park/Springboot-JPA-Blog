package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;


@RestController // 데이터만 리턴해줄거라서
public class UserApiController {

	@Autowired
	private UserService userService;


	//@Autowired private HttpSession session; //세션 객체는 스프링 컨테이너가 빈으로 등록을해서 갖고 있다
	

	@PostMapping("/auth/joinProc") // 회원가입로직(/api/user=>/auth/joinProc)
	public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
		System.out.println("UserApiController : save 호출됨");

		// 실제로 DB에 insert하고 아래에서 return이 되면 된다
		// int result = userService.회원가입(user);
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);

		// return new ResponseDto<Integer>(HttpStatus.OK.value(),result); //OK가 enum타입이네
		// //DB에 인서트 하고나서 리턴된 결과값이 두번째 파라미터자리
		// 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}

	
//	 //다음시간에는 스프링 시큐리티 이용해서 로그인/ 이거는 전통적인 스프링 로그인 방식
//	 @PostMapping("/api/user/login") 
//	 public ResponseDto<Integer> login(@RequestBody User user){ //(@RequestBody User user, HttpSession session) 위애 DI로 안하면 이렇게도 가능
//	 System.out.println("UserApiController : login호출됨"); 
//	 User principal =userService.로그인(user); //principal (접근주체)
//	  
//	 if(principal != null) { 
//		 session.setAttribute("principal", principal); 
//	 }
//	 return new ResponseDto<Integer>(HttpStatus.OK.value(),1); //세션을 만들고 1을 응답하면 
//  }

}
