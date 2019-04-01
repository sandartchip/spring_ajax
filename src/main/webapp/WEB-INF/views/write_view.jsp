<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>쓰기 페이지</title>
	</head>
	<body>
		<form action="write" method="post" onsubmit="return formCheck();"> 
		
		<!-- 컨트롤러의  @RequestMapping으로 /write로.. 
		이 request 값 받아오기 위해     Board Controller에서 write함수의 매개변수로 HttpServletResponse request를 매개변수로 준다.
		
		해당 request를 다른 파일에서 사용 위해 
		model.addAttribute로     request 객체를 넣어줌
		
		해당 리퀘스트 객체를 BWriteCommand에서 받는다. -->
		
			제목: <input type="text" name="title"/>
			내용: <input type="text" name="content" style="height:100px" />
			
			<input type="submit" value="저장" />	
		</form> 
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
	</body>
</html>