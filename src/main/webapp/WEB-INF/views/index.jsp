<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="layout/header.jsp"%>

<div class="container">

<c:forEach var="board" items="${boards}"> <!--${boards} =요청정보가 넘어올때 jstl에서 EL표현식 =>컨트롤러에서 인덱스 페이지로 boards가 넘어가-->
	<div class="card m-2">
		<!-- 마진m-2 -->
		<div class="card-body">
			<h4 class="card-title">${board.title}</h4>
			<a href="#" class="btn btn-primary">상세보기</a>
		</div>
	</div>
</c:forEach>

</div>

<%@ include file="layout/footer.jsp"%>



