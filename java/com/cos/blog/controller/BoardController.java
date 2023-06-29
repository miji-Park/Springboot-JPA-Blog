package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.service.BoardService;


@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;

	@GetMapping({"","/"}) //아무것도 안 붙였을때랑 /만 있을때는 여기로 간다는 의미
	public String index(Model model) { //4444. 컨트롤러에서 세션을 어떻게 찾는지? =>@AuthenticationPrincipal PrincipalDetail principal 이렇게 세션에 접근하자!
		// /WEB-INF/views/index.jsp
		//System.out.println("로그인 사용자 아이디:" + principal.getUsername());  //로그인 됐느지 확인위해
		
		//메인페이지로 갈때 데이터를 가져가야해 (리스트를 보여줘야하니까) => model이용
		model.addAttribute("boards", boardService.글목록()); //키값,데이터값 순서
		return "index"; // viewResolver 작동!! @Controller니까
	}
	
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
			return "board/saveForm";
		
	}
}
