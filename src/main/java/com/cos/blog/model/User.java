package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import org.hibernate.annotations.CreationTimestamp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더패턴
//ORM -> Java(다른언어도) Object를 -> 테이블로 매핑해주는 기술
@Entity//이 클래스를 테이블화 시킨다 =>User 클래스가 MySQL에 테이블이 생성된다
// @DynamicInsert //insert시 null인 필드를 제외시켜준다
public class User {

	@Id//얘가 시퀀스라는걸 알려주기 위함 & PK =>테이블에 insert될 때 값이 비어져있어도 자동으로 들어간다
	@GeneratedValue(strategy = GenerationType.IDENTITY)//넘버링 전략 //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다=>오라클에서 시퀀스, mySql에서는 auto-increment로 하겠다는 의미
	private int id; //오라클에서 시퀀스, mySql에서는 auto-increment
	
	@Column(nullable = false, length = 100, unique = true) //null이 될 수 없다
	private String username;//아이디
	
	@Column(nullable = false, length = 100) // 123456 => 해쉬로 변경할거라서 (비밀번호 암호화때문에)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
//	@ColumnDefault("'user'") //'user'로 문자라는걸 알려줘야함 디폴트값 정해
//	DB는 RoleType이라는 게 없다.
	@Enumerated(EnumType.STRING) //타입이 스트링이다라고 알려줌
	private RoleType role; //String이 아니라 Enum을 쓰는게 좋다(스트링은 실수 가능 users이런식으로 적을 수도 있으니까)// 도메인 설정이 가능=어떤 범위를 설정//회원가입했을때 역할이 ADMIN, USER등 권한을 줄 수 있다
	
	@CreationTimestamp//시간이 자동으로 입력 => 회원이 가입한 시간/=>테이블에 insert될 때 값이 비어져있어도 자동으로 들어간다
	private Timestamp createDate; //자바sql이 들고있는 Timestamp가 있다 
	
	//회원정보 수정하는 @UpdateTimestamp도 있다
	
	
}
