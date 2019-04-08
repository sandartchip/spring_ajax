<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.ArrayList"  %>
<%@ page import = "kr.or.connect.mvcexam.vo.BoardVO"  %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=UTF-8");
%>
    
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>수정 페이지</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
	</head>
	<style> 
		.content_container {
			width: 80%;
			margin-top:30px;
			margin:0 auto;
		}
		.t_header {
			background-color: gray;
			text-align:center;
			width:20%;
		}
	</style>
	<body> 
		<div class="container">
			<div class="page-header">
				<h1>Board</h1>
			</div> 
		
			<div class="content_container">
				<form action="modify" method="post" onsubmit="return formCheck();">
					<table class="table table-bordered tabel-hover active">
						<tbody>
							<tr>
								<td class="t_header">글 제목</td>
								<td class="t_body">
									<input type="text" name="title" value="${content.title}"
									style="width:100%;"> 
								</td>
							</tr>
							<tr>
								<td class="t_header">글 내용</td>
								<td class="t_body" style="height:500px">
									<textarea type="text" 
											name="content" 
											style="height:490px; width:100%;">${content.content}
									</textarea>
								</td>	
							</tr>
						</tbody>
					</table>
					<div class="footer" style="text-align:center">
						<!-- 실제 MODIFY 시키기 -->
						<input type="hidden" name="content_id" value="${content.content_id}">
						<!-- request 객체에  파라미터 값 넘기기 -->
						
						<button type="submit" class="btn btn-lg btn-default btn-sm">
							MODIFY
						</button>

						<button class="btn btn-lg btn-default btn-sm" 
								type="button"
								onclick="location.href='list?pageNO=${page_index}&search_text=${search_text}&search_type=${search_type}&start_date=${start_date}&end_date=${end_date}'">CANCEL</button>
					</div>
				</form>
			</div>
		</div>
		
		<script>
			function trim(value) {
				 value = value.replace(/^\s+/, ""); // 왼쪽 공백 제거
				 value = value.replace(/\s+$/g, "");//오른쪽 공백 제거
				 value = value.replace(/\n/g, "");//행바꿈제거
				 value = value.replace(/\r/g, "");//엔터제거 
	
				 return value;
			}
			function formCheck(){
				var title = document.forms[0].title.value; 
				var content = document.forms[0].content.value;
				
				title = trim(title);
				content = trim(content);			//공백 문자제거
				
				if(title.length==0) 
					alert("제목 없음");
				if(content.length ==0)
					alert("내용 없음");
				if(title.length>80)
					alert("제목의 길이가 너무 깁니다");
				if(  (title.length> 80) || (content.length==0) || (title.length==0) )
					return false;
			}
		</script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		
		<!-- 모든 컴파일된 플러그인을 포함합니다. -->
		<script src="js/bootstrap.min.js"></script>
		<!-- src 폴더에서 찾아 옴. -->
	</body>
</html>