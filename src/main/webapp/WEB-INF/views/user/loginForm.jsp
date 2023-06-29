<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%><!-- ../ =내 폴더에서 한칸위로 올라간다는 의미 -->

<div class="container">

	<!-- <form action="/blog/api/user/login">로그인은 select 하는거 =>js로 할거라서 그냥 폼으로 해 -->
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username</label> 
			<input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>
		
		<div class="form-group">
			<label for="pwd">Password</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		
		<div class="form-group form-check">
			<label class="form-check-label"> 
			<input name="remember" class="form-check-input" type="checkbox"> Remember me
			</label>
		</div>
		<button id="btn-login" class="btn btn-primary">로그인</button>
	</form>
	

</div>

<!-- <script src="/js/user.js"></script> --><!-- 시큐리티 쓸거라 폼 로그인 할거야 -->
<%@ include file="../layout/footer.jsp"%>



