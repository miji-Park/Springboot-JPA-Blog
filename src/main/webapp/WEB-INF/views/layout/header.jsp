<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!-- jstl 튜토리얼 -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %><!-- 시큐리티 taglib -->
<sec:authorize access="isAuthenticated()"> <!-- 로그인 된건지 확인하고 로그인 됐으면 로그인 정보를 principal로 가져올 수 있다-->
<!-- 	<script>
		alert("로그인 된 사용자입니다.");
	</script> -->
	<sec:authentication property="principal" var="principal"/> <!--principal : Allows direct access to the principal object representing the current user //맵핑-var로 이 페이지에서 사용할 수 있는 변수로 등록이 된다 -->
</sec:authorize>
<!-- 5555. isAuthenticated()에 접근을 해서 principal을 들고오면 시큐리티가 들고있는 세션 정보에 접근이 된다 
	근데 이거는 jsp에서 jstl로 하는거고-->


<!DOCTYPE html>
<html lang="en">
<head>
<title>블로그</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<!-- html은 인터프리터 언어 자바스크립트<script>는 맨 마지막에 두자 -->
<!-- 근데 못 읽는거 같으니 다시 위로 올릴게 -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.6.4/dist/jquery.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>

</head>
<body>

	<%-- <h1>${principal}</h1> <!-- principal이 무슨 값을 들고있는지 봐볼까나 --> --%>
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		<a class="navbar-brand" href="/">Blog</a>
		<!-- 메인화면으로 이동 -->
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="collapsibleNavbar">

			<c:choose>
				<c:when test="${empty principal}"><!-- 세션이 비어있다면 sessionScope.principal-->
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="/auth/loginForm">로그인</a></li>
						<li class="nav-item"><a class="nav-link" href="/auth/joinForm">회원가입</a></li>
					</ul>
				</c:when>
				<c:otherwise><!-- 세션이 비어있지않다면 -->
					<ul class="navbar-nav">
						<li class="nav-item"><a class="nav-link" href="/board/saveForm">글쓰기</a></li>
						<li class="nav-item"><a class="nav-link" href="/user/updateForm">회원정보</a></li>
						<li class="nav-item"><a class="nav-link" href="/logout">로그아웃</a></li>
					</ul>
				</c:otherwise>
			</c:choose>

		</div>
	</nav>
	<br />