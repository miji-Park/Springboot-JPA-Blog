package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class BoardController {

	@GetMapping({"","/"}) //아무것도 안 붙였을때랑 /만 있을때는 여기로 간다는 의미
	public String index() { //4444. 컨트롤러에서 세션을 어떻게 찾는지? =>@AuthenticationPrincipal PrincipalDetail principal 이렇게 세션에 접근하자!
		// /WEB-INF/views/index.jsp
		
		//System.out.println("로그인 사용자 아이디:" + principal.getUsername());  //로그인 됐느지 확인위해
		return "index";
	}
}
