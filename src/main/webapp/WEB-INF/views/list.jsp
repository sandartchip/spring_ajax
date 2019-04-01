
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "kr.or.connect.mvcexam.vo.BoardVO"  %>
<%@ page import = "java.util.ArrayList"  %> 
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>게시글 목록 페이지</title>
</head>
<body>
	<H1>게시글 목록 페이지</H1> 
	
	<div>
		<table>
			<thead>
				<tr>
					<th>글번호</th>
					<th width="100px">제목</th>
				</tr>
			</thead>
			<tbody>			
				<c:forEach items="${list_item}" var="vo"> 
					<tr>
						<td>${vo.content_id}</td>
						<td> <a href="view?content_id=${vo.content_id}"> ${vo.title}</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div> 
	<a href="write_view">등록 페이지</a> 
</body>
</html>