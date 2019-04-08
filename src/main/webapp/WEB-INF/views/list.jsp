
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

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js">
	</script>
	<script src="js/bootstrap.min.js"></script>

</head>

<body>

	<div class="container">
		<div class="page-header">
			<h1>Board</h1>
		</div>
		
		<div class="header_div">
			<a href="write_view" >등록 페이지</a>
		</div>
		
		<div class="content_container" >
			<table class="table table-striped table-bordered table-hover active">
				<thead> 
					<tr>
						<th width="15%" style="text-align:center">글 번호</th>
						<th width="85%" style="text-align:center">글 제목</th>
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
	<div class="page_footer"> <!--  페이지를 표시하기 위한 footer -->
		<!-- 컨트롤러에서 받아온 page_list_maker로 하단에 표시.  -->
		<!-- prev 버튼 --> 
		<!--  시작 번호:startPage to  endPage -->
		 
	 	<div class="search_box">
	 		<form action="list">
		 		<select name="search_type">
			 		<option value="title"> 제목 </option>
			 		<option value="content"> 내용 </option>
			 		<option value="date"> 날짜 </option>
			 	</select>
			 	<!-- form에서 파라미터명은 name에 바인딩 되므로. search type = value값.으로 바인딩되서  넘어가겠지.-->
			 	
			 	<input type="text" name="search_text" class="search_text_input"/> 
			 	<!-- input box에 있는 데이터는 get방식 파라미터로 넘어간다. -->
			 	<input type="date" name="start_date" class="start_date_input"/>
			 	<input type="date" name="end_date" class="end_date_input"/>
			 	
		 		<button type="submit" style="float:left">검색</button> 
	 		</form>	
	 	</div> 
	 	
	 	
	 	<ul>
			<c:if test="${ page_list_maker.prev }"> 
				<li style="float:left; list-style-type:none; padding-right:10px">
					<a href="<c:url value='list?pageNO=${page_list_maker.startPage-1}&search_text=${search_text}&search_type=${search_type}&start_date=${start_date}&end_date=${end_date}'/>"> 이전 페이지 </a>
				</li> <!--  list 호출 -> 리스트 & pageMaker를 다시 들고 온다. pageMaker-->
			</c:if>
			<c:forEach var="page_index" begin="${page_list_maker.startPage}" end="${page_list_maker.endPage}">
			 	<li style="float:left; list-style-type:none; padding-right:10px">
			 		<a href="<c:url value='list?pageNO=${page_index}&search_text=${search_text}&search_type=${search_type}&start_date=${start_date}&end_date=${end_date}'/>"> ${page_index}</a>  
			 	</li>
		 	</c:forEach>
		 	<c:if test="${ page_list_maker.next }">
		 		<li style="float:left; list-style-type:none;">
		 			<a href="<c:url value='list?pageNO=${page_list_maker.endPage+1}&search_text=${search_text}&search_type=${search_type}&start_date=${start_date}&end_date=${end_date}' />">다음 페이지</a>
		 		</li>
		 	</c:if>
	 	</ul>
	 	
		<!-- 컨트롤러에서 넘어 온 페이지 Maker 사용해서 페이지 리스트 만듬-->
		<!-- next 버튼 -->
	</div>
	
	
	<script type="text/javascript">
		var date_start_elems = document.getElementsByClassName('start_date_input');
		var date_start_elem = date_start_elems[0];
		
		var date_end_elems = document.getElementsByClassName('end_date_input');
		var date_end_elem = date_end_elems[0];
		// ???????????왜 document 밖으로 빼야하지???
		
		var text_elems = document.getElementsByClassName('search_text_input');
		var text_elem  = text_elems[0];
		
		console.log(date_start_elem);
		console.log(date_end_elem);
		console.log(text_elem);
		
		$(document).ready(function(){		

			
			$("select").change(function(){
				console.log("hi!! change!!");
				$(this).find("option:selected").each(function(){
					 
					var optionValue = $(this).attr("value");
					console.log("선택 option"+optionValue);
					if(optionValue=="date"){
						
						date_start_elem.style.display="inline";
						date_end_elem.style.display = "inline";
						text_elem.style.display = "none";
						console.log(" date  선택");
					}
					else{
						text_elem.style.display="inline";
						date_start_elem.style.display="none";
						date_end_elem.style.display = "none";
					}
				});
			}).change();
		});
	</script>	
	<!-- src 폴더에서 찾아 옴. -->
</body>
</html>