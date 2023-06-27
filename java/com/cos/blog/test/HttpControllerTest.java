package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.NoArgsConstructor;

//만약에 HTML파일을 응답하는 컨트롤러를 만드려면 @Controller를 쓴다

//사용자가 요청->응답(Data로 응답)
@RestController
public class HttpControllerTest {
	
	private static final String TAG="HttpController Test : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		
		//Member m1 = new Member(1, "ssar", "1234", "email");  //@AllArgsConstructor
		//Member m2 = new Member(); //@NoArgsConstructor
		Member m = Member.builder().password("1234").username("ssar").email("ssar@nate.com").build(); //Member클래스에서 @Builder를 썼기때문에 가능 //내가 넣는 값의 순서를 지키지 않아도 되는게 장점
		System.out.println(TAG+"getter:" + m.getUsername());
		m.setUsername("cos");
		System.out.println(TAG+"getter:" + m.getUsername());
		
		return "lombok test 완료";
	}

	
	//인터넷 브라우저 요청은 무조건 get 요청밖에 할 수가 없다.(쿼리스트링 이용)
	//http://localhost:8080/http/get(select)
	//@GetMapping("/http/get")
	//public String getTest(@RequestParam int id, @RequestParam String username) {
	//	return "get 요청:" + id + "," + username;   //리턴타입이 스트링이니까 문자열을 리턴한다
	//}
	
	@GetMapping("/http/get") //get은 데이터를 주소에 붙여서 보낸다
	public String getTest(Member m) //쿼리스트링 방법으로 id=1&username=ssar&password=1234&email=ssar@nate.com 이렇게 데이터를 실어보내면 컨트롤러에서 Member라는 객체에 집어넣어주는 역할을 스프링이 한다
	{								//=>스프링부트의 MessageConverter가 해준다
		
		return "get 요청:" + m.getId() + "," + m.getUsername() + "," +m.getPassword()+ "," + m.getEmail();
	}
	
	//http://localhost:8080/http/post(insert)
//	@PostMapping("/http/post")
//	public String PostTest(Member m) //바디에 데이터를 담아보내
//	{
//		return "post 요청" + m.getId() + "," + m.getUsername() + "," +m.getPassword()+ "," + m.getEmail();
//	}
	
//	@PostMapping("/http/post") //MIME타입 : text/plain(평문)
//	public String PostTest(@RequestBody String text) //바디에 데이터를 담아보내
//	{
//		return "post 요청:" +text;
//	}
	
	@PostMapping("/http/post") //MIME타입 : application/json 
	public String PostTest(@RequestBody Member m) //바디에 데이터를 담아보내 =>스프링부트의 MessageConverter가 해준다
	{
		return "post 요청" + m.getId() + "," + m.getUsername() + "," +m.getPassword()+ "," + m.getEmail();
	}
	
	//http://localhost:8080/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청" + m.getId() + "," + m.getUsername() + "," +m.getPassword()+ "," + m.getEmail();
	}
	
	//http://localhost:8080/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
	
	
}
