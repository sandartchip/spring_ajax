<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.ArrayList"  %>
<%@ page import = "kr.or.connect.mvcexam.vo.BoardVO"  %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=UTF-8");	
	/*

	JSTL의 EL구문 쓰면 getAttribute 안 해줘도 됨.

	Model 객체에 이미 해당 번호에 해당하는 VO가
	BContentCommand를 통해 들어가 있음.
	*/
%>  
    
<!DOCTYPE html>
<html lang="ko">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- 반응형 -->
		
		<title>Board Project</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<!-- bootstrap 라이브러리 추가  -->
		<style>
			.t_header {
				background-color: gray;
				text-align:center;
				width:20%;
			}
			.content_container {
				width: 80%;  				
				margin: 0 auto; <!-- container 내부에서 중간정렬 시키기 위해-->
				margin-top:30px;
			}

			.t_body {
				width:80%;
			}
		</style>
	</head>
<body>

	<div class="container">
		<div class="page-header">
			<h1>Board</h1>
		</div>
		
		<div class="content_container">
			<table class="table table-bordered table-hover active"> 
				<tbody>
					<tr>
						<td class="t_header">글 제목</td>
						<td class="t_body">${content.title}</td>
					</tr>
					<tr>
						<td class="t_header" style="height:400px">글 내용</td>
						<td class="t_body">${content.content}</td>
					</tr>
					<tr>
						<td class="t_header">등록 일자</td>
						<td class="t_body">${content.regDate}</td>
					</tr>
					<tr>
						<td class="t_header">수정 일자</td>
						<td class="t_body">${content.modDate}</td>
					</tr>
				</tbody>
			</table>
			
			<div class="footer" style="text-align:center">
				<button type="button"
						class="btn btn-lg btn-default btn-sm"
						onclick="location.href='list'">
					LIST
				</button>
		
				<button type="button"
						class="btn btn-lg btn-default btn-sm"
						onclick="location.href='modify_view?content_id=${content.content_id}'">
					MODIFY
				</button>
				
				<button type="button"
						class="btn btn-lg btn-default btn-sm"
						onclick="location.href='delete?content_id=${content.content_id}'">
					DELETE				
				</button>	
			</div>
		</div>
	</div>

	<!--  jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요함) -->	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	
	<!-- 모든 컴파일된 플러그인을 포함합니다. -->
	<script src="js/bootstrap.min.js"></script>
</body>
</html>