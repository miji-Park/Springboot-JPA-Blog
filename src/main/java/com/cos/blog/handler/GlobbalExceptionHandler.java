package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice//어디에서든 예외가 발생하면 이 클래스로 들어오게 만든다
@RestController
public class GlobbalExceptionHandler {

//	@ExceptionHandler(value=Exception.class)//예외에 대한 에러를 스프링이 이 함수에 전달해준다 IllegalArgumentException인 애만 오게 하고싶으면 벨류값에 넣어
//	public String handleArgumentException(IllegalArgumentException e) {
//		return "<h1>" + e.getMessage() + "</h1>";
//		
//	}
	
	@ExceptionHandler(value=Exception.class)
	public ResponseDto<String> handleArgumentException(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()); // 500
	}
	
}