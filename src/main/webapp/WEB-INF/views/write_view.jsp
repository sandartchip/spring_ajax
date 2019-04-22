<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("utf-8"); 
	session = request.getSession(true);
	
//	String userId = (String) session.getAttribute("userId");
	
%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>쓰기 페이지</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<style>
			.content_container {
				margin-top:30px;
				width:80%;
				margin: 0 auto;
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
			<div class="page-header">
				<h1>Board</h1>
			</div>
			<div class="content_container">
				<form action="write" method="post" onsubmit="return formCheck();" 
				      enctype="multipart/form-data">  
					<table class="table table-bordered table-hover active">
						<tbody>
							<tr>
								<td class="t_header">제목</td>
								<td class="t_body"> 
									<input type="text" name="title" style="width:100%" value="ggg"/> 
									<input type="hidden" name="writer" value="${userId}"> 
								</td>
							</tr>
							<tr>
								<td class="t_header">내용</td>				
								<td class="t_body" style="height:500px"> 
									<textarea type="text" 
											cols="40" 
											name="content" 
											style="height:490px; width:100%;">
											내용내용내용ㅇ
									</textarea> 
								</td>
							</tr>
							<tr>
								<td class="t_header">첨부 파일</td>
								<td class="t_body">
									
									<input type="file" id="file1" name="file1" style="padding-bottom:10px">
								</td>
							</tr>
						</tbody>
					</table>
					
					<div class="footer" style="text-align:center">
						<button class="btn btn-lg btn-default btn-sm cancel_btn"
							type="button" style="text-align:center" onclick="location.href='list'">CANCEL</button>
						<button class="btn btn-lg btn-default btn-sm save_btn"
							type="submit" style="text-align:center">SAVE</button>
					</div>
				</form> 
			</div> 
		</div> <!--  end of container -->

		<script>
			function trim(value) {
				 value = value.replace(/^\s+/, ""); // 왼쪽 공백 제거
				 value = value.replace(/\s+$/g, "");//오른쪽 공백 제거
				 value = value.replace(/\n/g, "");//행바꿈제거
				 value = value.replace(/\r/g, "");//엔터제거 
	
				 return value;
			}
			function formCheck(){
				<!--  form Check를 호출한 객체가     누구인지 알아 와야 한다. -->
				
				var title = document.forms[0].title.value; 
				var content = document.forms[0].content.value;
				
				title = trim(title);
				content = trim(content);
				<!-- 공백 제거 -->
				
				if(title.length==0) 
					alert("제목을 입력해주세요.");
				if(content.length == 0)
					alert("내용을 입력해주세요.");
				if(title.length>80)
					alert("제목의 길이가 너무 깁니다. ");
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