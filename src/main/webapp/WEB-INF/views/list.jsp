
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "kr.or.connect.mvcexam.vo.BoardVO"  %>
<%@ page import = "java.util.ArrayList"  %> 
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>게시판</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	<style>
		.container {
			width:70%;
			margin: 0 auto; 
			padding-top: 10%;
		}
		.content_container{
			margin-top:10px;
		}
		.header_div {
			text-align: right;
		}
	</style>
</head>

<body>
	<!-- 
		<h3>게시판</h3>
	 -->
	<div class="container">
		
		<div class="header_div">
		
			<h3 style="text-align: center">게시판</h3>
			<a href="write_view" >등록 페이지</a>
		</div>
		
		<div class="content_container" >
			<table class="table table-striped table-bordered table-hover active">
				<thead> 
					<tr>
						<th width="20%" style="text-align:center">글 번호</th>
						<th width="80%" style="text-align:center">글 제목</th>
					</tr>
				</thead>
				<tbody>		
					<c:forEach items="${list_item}" var="vo"> 
						<tr>
							<td style="text-align:center">${vo.content_id}</td>
							<td> <a href="view?content_id=${vo.content_id}"> ${vo.title}</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	
	<!-- 모든 컴파일된 플러그인을 포함합니다. -->
	<script src="js/bootstrap.min.js"></script>
	<!-- src 폴더에서 찾아 옴. -->
</body>
</html>