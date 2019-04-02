<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.ArrayList"  %>
<%@ page import = "kr.or.connect.mvcexam.vo.BoardVO"  %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=UTF-8");
%>
<% 
	System.out.println("수정 페이지");
	
	BoardVO vo = (BoardVO) request.getAttribute("content"); //jsp에서 a로 넘길 때 받아온 content id 값
	//안에서 깨지는 줄 알았음.
	//근데 안이 아니라 밖에서 깨져서 온 거 였음. 그 받아온 값 안써도 이미 화면전체가 깨진다.
	//이거 나중에 JSTL로 고치기. getattribute말고.
	
	String data_title, data_reg_date, data_mod_date, data_content;
	int content_id = vo.getContent_id();
	
	data_title = vo.getTitle();
	data_content = vo.getContent();
	
	data_reg_date = vo.getRegDate();	
	data_mod_date = vo.getModDate();	
	System.out.println("받아온 제목= " + data_title);
	
	//request 스코프에 BoardVO값이 유지 
	
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>수정 페이지</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	</head>
<body>

	<h1>수정 페이지</h1>
		
	<div class="page-header">
		<h1>Tables</h1>
	</div>
	
	
	
	<table>
		<form action="modify" method="post">	
			<input type="hidden" name="content_id" value="<%=content_id %>">	
			<tr>
				<td>글 제목</td>
				<td><input type="text" name="title" value="<%=data_title %>"> </td>
			</tr>
			<tr>
				<td>글 내용</td>
				<td><input  type="text" name="content" value="<%= data_content %>"> </td>
			</tr>
			<tr>
				<td>등록 일자</td>
				<td><%= data_reg_date %></td>
			</tr>
			<tr>
				<td>수정 일자</td>
				<td><%= data_mod_date %></td>
			</tr>
			<input type="submit" value="MODIFY">
		</form>
	</table>
	
	<a href="delete?content_id=<%=content_id%>">DELETE</a> 
	
	<a href="write_view">등록 페이지</a> 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	
	<!-- 모든 컴파일된 플러그인을 포함합니다. -->
	<script src="js/bootstrap.min.js"></script>
	<!-- src 폴더에서 찾아 옴. -->
</body>
</html>