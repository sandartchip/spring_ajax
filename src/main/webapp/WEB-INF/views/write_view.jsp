<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>쓰기 페이지</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<style>
			.content_container {
				margin-top:30px;
			}
			.t_header {
				background-color: gray;
				text-align:center;
				width:20%;
			}
			.t_body {
				width:80%;
			}
		</style>
	</head>
	<!-- 컨트롤러의  @RequestMapping으로 /write로.. 
		이 request 값 받아오기 위해     Board Controller에서 write함수의 매개변수로 HttpServletResponse request를 매개변수로 준다.
		
		해당 request를 다른 파일에서 사용 위해 
		model.addAttribute로     request 객체를 넣어줌
		
		해당 리퀘스트  객체를 BWriteCommand에서 받는다. -->
	<body>
		<div class="container">
			<div class="content_container">
				<form action="write" method="post" onsubmit="return formCheck();"> 
					<table class="table table-bordered table-hover active">
						<tbody>
							<tr>
								<td class="t_header">제목</td>
								<td class="t_body"> 
									<input type="text" name="title"/> 
								</td>
							</tr>
							<tr>
								<td class="t_header">내용</td>				
								<td class="t_body"> 
									<textarea type="text" cols="40" name="content" /> 
								</td>
							</tr>
						</tbody>
					</table>
					<button type="submit" value="저장" />	
				</form> 
			</div>
		</div>

		<script>
			function formCheck(){
				var title = document.forms[0].title.value; 
				var content = document.forms[0].content.value;
				
				if(title.length==0) 
					alert("제목 업음");
				if(content.length ==0)
					alert("내용 없음");
				if(title.length>80)
					alert("제목의 길이가 너무 깁니다");
				if(  (title.length> 80) || (content.length==0) || (title.length==0) )
					return false;
			}
		</script>		
		<!--  jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요함) -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
		<!-- 모든 컴파일된 플러그인을 포함합니다. -->
		<script src="js/bootstrap.min.js"></script>
	</body>
</html>