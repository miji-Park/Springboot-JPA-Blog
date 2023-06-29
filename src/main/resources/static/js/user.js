let index = {
	init: function(){
		$("#btn-save").on("click",()=>{	// function(){} , ()=>{} this를 바인딩하기 위해서!!
			this.save(); //on 첫번째 파라미터:어떤 이벤트, 두번째 파라미터: 무엇을 할건지
		}); 
//		$("#btn-login").on("click",()=>{	// function(){} , ()=>{} this를 바인딩하기 위해서!!
//			this.login(); //on 첫번째 파라미터:어떤 이벤트, 두번째 파라미터: 무엇을 할건지
//		}); 		
	}, //여기 콤마 안 찍으면 save에 빨간줄 뜨네
	
	save: function(){
		//alert("user의 save 함수 호출됨");
		let data = {
			username: $("#username").val(),  //해당 #id가 들고있는 값을 변수에 바인딩한다
			password: $("#password").val(),
			email: $("#email").val()
		};
		
		//console.log(data);
		
		//ajax 호출시 default가 비동기 호출
		//ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
		//ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요.
		$.ajax({
			//회원가입 수행 요청 =>성공하면 done함수, 실패하면 fail함수 실행
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), //JSON문자열로 변경//http body데이터
			contentType: "application/json; charset=utf-8", //위랑 이거 두줄이 세트//body데이터가 어떤 타입인지(MIME)
			dataType: "json"//요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경해서 응답하게 해줘
		}).done(function(resp){
			alert("회원가입이 완료되었습니다.");
			//console.log(resp); =>UserApiController에서 리턴하는 값
			location.href="/";	
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
	
//		login: function(){
//		//alert("user의 save 함수 호출됨");
//		let data = {
//			username: $("#username").val(),  //해당 #id가 들고있는 값을 변수에 바인딩한다
//			password: $("#password").val()
//		};
		
//		$.ajax({
//			type: "POST",
//			url: "api/user/login",
//			data: JSON.stringify(data), //JSON문자열로 변경//http body데이터
//			contentType: "application/json; charset=utf-8", //위랑 이거 두줄이 세트//body데이터가 어떤 타입인지(MIME)
//			dataType: "json"//요청을 서버로해서 응답이 왔을 때 기본적으로 모든 것이 문자열 (생긴게 json이라면) => javascript오브젝트로 변경해서 응답하게 해줘
//		}).done(function(resp){
//			alert("로그인이 완료되었습니다.");
//			//console.log(resp);
//			location.href="/";	
//		}).fail(function(error){
//			alert(JSON.stringify(error));
//		}); 
//	}
}//여기까진 오브젝트라 아무일도 생기지 않아 joinForm.jsp에서 쫙 읽어질때 <script>를 읽으면 

index.init();
