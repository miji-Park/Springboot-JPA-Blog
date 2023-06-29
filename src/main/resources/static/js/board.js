let index = {
	init: function(){
		$("#btn-save").on("click",()=>{	// function(){} , ()=>{} this를 바인딩하기 위해서!!
			this.save(); //on 첫번째 파라미터:어떤 이벤트, 두번째 파라미터: 무엇을 할건지
		}); 
	}, 
	
	save: function(){
		let data = {
			title: $("#title").val(),  //해당 #id가 들고있는 값을 변수에 바인딩한다
			content: $("#content").val()
		};
		
		$.ajax({
			type: "POST", //글쓰기니까 post요청
			url: "/api/board",
			data: JSON.stringify(data), 
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			location.href="/";	
		}).fail(function(error){
			alert(JSON.stringify(error));
		}); 
	},
} 

index.init();
