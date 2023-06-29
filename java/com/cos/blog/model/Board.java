package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
	private int id; 
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터
	private String content; // 섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨.=>글자 용량이 커져 그래서 대용량이 됨
	

	//@ColumnDefault("0")//User클래스랑 다르게 ''없이 그냥 0(int니까)
	private int count; // 조회수
	
	@ManyToOne(fetch = FetchType.EAGER)  // Board:Many, User:One  =>연관관계 //fetch = FetchType.EAGER => 보드 테이블을 select하면 User정보는 바로 조인해서 가져올게 
	@JoinColumn(name="userId")//실제 DB에 만들어질때는 UserId로 만들어질거다 필드값 =>테이블에어 알아서 FK로 userId Integer로 만들어짐!
	private User user; // DB는 오브젝트를 저장할 수 없다. FK/ 자바는 오브젝트를 저장할 수 있다. =>자바가 DB에 맞춰서 오브젝트가 아닌 FK 키 값을 저장하게 된다 JAP ORM을 사용하게되면 오브젝트로 저장가능
	//위는 유저 객체니까 유저 클래스를 참조하겠지 자동으로 FK가 만들어짐
	
	@OneToMany(mappedBy = "board", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)  //fetch = FetchType.LAZY => 필요하면 들고오고 아니면 안 들고올게 
	// mappedBy 적혀있으면 연관관계의 주인이 아니다 (난 FK가 아니에요) = DB에 칼럼을 만들지 마세요./EAGER
//	@JsonIgnoreProperties({"board"})
//	@OrderBy("id desc")
	private List<Reply> reply;
	
	@CreationTimestamp //데이터 insert나 update시 자동으로 들어감
	private Timestamp createDate;
}
