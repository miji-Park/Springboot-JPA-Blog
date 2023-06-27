package com.cos.blog.repositoty;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//jsp에서?  DAO
//빈으로 등록되나요?=스프링 IoC에서 객체를 가지고 있나요? =>자동으로 빈으로 등록이 된다
//@Repository 생략이 가능하다
public interface UserRepository extends JpaRepository<User, Integer> //제네릭 / User테이블이 관리하는 레파지토리,이 User테이블의 PK는 Integer다  
{																	 //findAll()이라는 함수를 들고 있다 => User 테이블이 들고있는 모든 행을 다 return
	//기본적인 CRUD는 {} 이렇게만 만들어줘도 JpaRepository가 모든 함수를 다 들고있어서 가능하다
	//SELECT & FROM WHERE username = 1?(첫번째 파라미터); 첫번째 파라미터가 들어가면서 해당 쿼리 실행  
	Optional<User> findByUsername(String username); //findBy~~ 네이밍 규칙!where절에 나오는 값을 뒤에 붙여
}





//기본적인 CRUD는 이렇게만 만들어줘도 JpaRepository가 모든 함수를 다 들고있어서 가능하다

//JPA 네이밍 전략
//SELECT * FROM user WHERE username = ?1 AND password=?2; 인 쿼리가 동작한다
//User findByUsernameAndPassword(String username, String password);

//위와 같음
//@Query(value="SELECT * FROM user WHERE username = ?1 AND password=?2", nativeQuery=true)
//User login(String username, String password);