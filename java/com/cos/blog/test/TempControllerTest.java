package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller//해당 경로에 있는 파일을 리턴 @RestController는 문자열 자체를 리턴
public class TempControllerTest {
	
	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		//파일리턴 기본경로: src/main/resources/static =>static 파일은 정적파일만 돌아가서(브라우저가 인식하는/사진파일같은) jsp 파이을 넣으면 인식x
		//리턴명:/home.html이라고해야해
		//풀경로: src/main/resources/static/home.html
		return "/home.html";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//yml에서 명시된 prefix: /WEB-INF/views/
		//yml에서 명시된 suffix: .jsp
		//풀경로: /WEB-INF/views/test.jsp
		return "test";
	}
}
