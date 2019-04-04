
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
	
	<%
		
	
	%>
	<div class="page_footer"> <!--  페이지를 표시하기 위한 footer -->
		<!-- 컨트롤러에서 받아온 page_list_maker로 하단에 표시.  -->
		<!-- prev 버튼 -->
		
		<!--  
			이전 페이지 버튼 누르면 1~10까지  
			11~20까지 표시하다가
			다음페이지 누르면
			21~30 으로 표시.
		-->	
		<!--  시작 번호:startPage to  endPage -->
		
		<ul>
			<c:if test="${ page_list_maker.prev }"> 
				<li style="float:left; list-style-type:none; padding-right:10px">
					<a href="<c:url value='list?pageNO=${page_list_maker.startPage-1}'/>"> 이전 페이지 </a>
				</li> <!--  리스트 & pageMaker를 다시 들고 온다. pageMaker-->
			</c:if>
			<c:forEach var="page_index" begin="${page_list_maker.startPage}" end="${page_list_maker.endPage}">
			 	<li style="float:left; list-style-type:none; padding-right:10px">
			 		<a href="<c:url value='list?pageNO=${page_index}'/>"> ${page_index }</a>  
			 	</li>
		 	</c:forEach>
		 	<c:if test="${ page_list_maker.next }">
		 		<li style="float:left; list-style-type:none;">
		 			<a href="<c:url value='list?pageNO=${page_list_maker.endPage+1}' />">다음 페이지</a>
		 		</li>
		 	</c:if>
	 	</ul>
	 	
		<!--
			int curPage;    //현재 페이지 번호
			int pagePerNum; //1페이지당 몇갠지
	 
		 -->
		 
		<!-- 컨트롤러에서 넘어 온 페이지 Maker 사용해서 페이지 리스트 만듬-->
		
		<!-- next 버튼 -->
	</div>
	 
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	
	<!-- 모든 컴파일된 플러그인을 포함합니다. -->
	<script src="js/bootstrap.min.js"></script>
	<!-- src 폴더에서 찾아 옴. -->
</body>
</html>