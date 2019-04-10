     
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "kr.or.connect.mvcexam.vo.BoardVO"  %>
<%@ page import = "java.util.ArrayList"  %> 
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	request.setCharacterEncoding("utf-8"); 
	session = request.getSession(true);
	
	String userId = (String) session.getAttribute("userId");
	
	String loginStatus = (String) session.getAttribute("loginStatus");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
		<div class="login_header">
			<c:choose>
				<c:when test="${sessionScope.userId!=null}">
					${sessionScope.userId}님이 로그인 중입니다.
					<a href="logout">로그아웃</a>
				</c:when> 
			</c:choose>		
		</div>
</body>
</html>