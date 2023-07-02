package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repositoty.UserRepository;

@RestController //회원가입이 잘됐다 안됐다 정도만 확인하려고
public class DummyControllerTest {
	
	@Autowired//DummyControllerTest가 메모리에 뜰 때 @Autowired도 메모리에 같이 뜬다 /의존성주입 DI
	private UserRepository userRepository;
	
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) { //어떤 예외처리 해야할지 모르겠음녀 제일 부모인 Exception해도 되긴해 그치만 정확히 어떤 예외에 걸리는지는 모르겠지
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		

		return "삭제되었습니다. id:" + id;
	}
	
	
	//save 함수는 id를 전달하지 않으면 insert를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert 를 해요.
	//email,password
	@Transactional //함수 종료시에 자동 commit이 됨 =>변경된 값이 있으니 영속화된 user객체를(영속성 컨텍스트 안 1차 캐시 안에 있는) 커밋하면서 업데이트한다
	@PutMapping("/dummy/user/{id}") 
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {//json 데이터를 요청=>Java Object(MassageConverter가 Jackson 라이브러리가 변환해서 받아준다)=>이때 필요한 어노테이션이 @RequestBody

		System.out.println("id:" +id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());

		User user = userRepository.findById(id).orElseThrow(()->{ //orElseThrow=찾는게 없으면
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());  //로 비번이랑 이메일 변경해서
		
//		requestUser.setId(id);
//		requestUser.setUsername("ssar");
//		userRepository.save(requestUser); //save는 insert할때 쓰는 메서드 //http://localhost:8000/blog/dummy/user/1 포스트맨에서 보내면 기존1번 아이디를 찾아서 save해줌 = update도 해줌 그치만 없는 값은 null로 들어가서 업데이트시 save는 쓰지 않아
	
		//userRepository.save(user); //변경된 오브젝트user를 넣으면 정상적으로 업데이트 된다(이 user는 null이 없다)
		//save를 안 하고 값만 set으로 넣었을때 @Transactional를 쓰니까 업데이트가 된다
		
		//더티체킹 = 변경을 감지해서 DB에 udpate를 날려
		//더티체킹을 이용한 업데이트
		
		//DB에서 selelct로 값을 받아와서 그 객체에 값만 변경하고 @Transactional하면 없데이트 된다
		
		return user;
	}
	
	
	
	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
				
	}
	
	//http://localhost:8000/blog/dummy/user?page=0
	//한 페이지당 2건의 데이터를 리턴받아 볼 예정(페이징) =>0이 첫번째 페이지 1이 두번째 페이지
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction=Sort.Direction.DESC) Pageable pageable){ //최근에 가입한 순서
		//2건씩 ,id로 분류,id를 최신순으로해서
		Page<User> pagingUser = userRepository.findAll(pageable); //findAll는 페이지타입을 리턴해
		
		if(pagingUser.isLast()) {//페이징 유저가 마지막 유저인가요?하면서 분기 처리도 가능
			
		}
		List<User> users = pagingUser.getContent();
		return users; //Page라는 클래스가 제공해주는걸 어떤 로직이 끝나면 결과는 List로 받아서 리턴하는게 좋다
	
	}
	
	
	
	
	// {id} 주소로 파라미터를 전달 받을 수 있음
	// http://localhost:8000/blog/dummy/user/5
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) //{}안에 들어간 id가 고대로 와야해
	{
		//user/4를 찾으면 내가 DB에서 못 찾아오게되면 user가 null이 될거자나
		//그럼 return null이 리턴이 되자나.. 그럼 프로그램에 문제가 있지 않겠니?
		//Optional로 너의 User객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해!!
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() //익명객체가 만들어진다 인터페이스는 new 못해 new하려면 익명클래스를 만들어야해 
//		{
//			@Override	//Supplier 인터페이스가 들고있는 get함수를 오버라이딩해줘야해 그러면 객체생성 가능해짐
//			public User get() {
//				// TODO Auto-generated method stub
//				return new User(); //빈 객체 리턴,DB에 존재하는 Id를 찾으면 상관없는데 없는 Id를 찾으면 이 빈 객체를 넣어준다(null값이 되지 않도록)
//				
//		}
		
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
				//=>나중에 화면에 보여지는건 다시 만질거야
			}
		});
		// 요청: 웹브라우저
		//user 객체 = 자바 오브젝트
		//자바 객체를 웹브라우저는 이해하지못해 =>변환필요(웹브라우저가 이해할 수 있는 데이터)->json(Gson 라이브러리)
		//스프링 부트 = MassageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MassageConverter가 Jackson 라이브러리를 호출해서 
		//user 오브젝트를 json으로 변환해서 브라우저에게 던져줍니다.
		return user;
		
//		//람다식 =>Supplier타입을 리턴해야하는지 몰라도 된다
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 사용자는 없습니다.");
//		});
//		return user;

	}
	

	
	//http://localhost:8000/blog/dummy/join(요청)
	//http의body에 username,password,email 데이터를 가지고(요청)
	@PostMapping("/dummy/join")
	public String join(User user)//(String username, String password, String email) //key=value형태의 데이터를 받아준다(약속된 규칙)
	{							 //x-www-form-urlencoded로 전송되는 데이터는 키=벨류로 오는데 스프링이 함수의 파라미터로 파싱해서 집어넣어준다
								 //오브젝트로도 받게 해준다
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());

		user.setRole(RoleType.USER); //role 자동으로 안 들어가서 넣어줘야해 //setRole("user")하니가 빨간줄뜨네 어? 마우스 갖다대니까ㅣ 아~ RoleType이 있네
		userRepository.save(user);
		return "회원가입 성공";
	}

}
