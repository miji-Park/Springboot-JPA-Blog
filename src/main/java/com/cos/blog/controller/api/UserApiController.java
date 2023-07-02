package com.cos.blog.controller.api;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController // 데이터만 리턴해줄거라서
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

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
	
	
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) { //json데이터 받을거니까 @RequestBody걸자
		userService.회원수정(user);
		// 여기서는 트랜잭션이 종료되기 때문에 DB에 값은 변경이 됐음.
		// 하지만 세션값은 변경되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해줄 것임.
		//세션등록
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
	
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
}
