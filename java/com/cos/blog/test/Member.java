package com.cos.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter
//@Setter
//@Data  //getter,setter 다 생성해줌
//@AllArgsConstructor //생성자 만들어줌
//@RequiredArgsConstructor //final붙은 애들의 생성자를 만들어줘 //어차피 DB에서 값이 들고와서 넣을거라 데이터가 변경되지 않도록 final로 해줘 = 불변성 유지

@Data
//@AllArgsConstructor	//전체 생성자
@NoArgsConstructor	//빈 생성자
public class Member {

	private int id;   
	private String username;
	private String password;
	private String email;
	
	@Builder //
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	

	
}
