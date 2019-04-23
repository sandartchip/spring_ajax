<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "kr.or.connect.mvcexam.vo.BoardVO"  %>
<%@ page import = "java.util.ArrayList"  %> 
<%@ page import = "java.util.Date"  %>  
<%@ page import = "com.google.gson.Gson" %>
<%@ page import = "com.google.gson.GsonBuilder" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<% 
	request.setCharacterEncoding("utf-8"); 
	session = request.getSession(true);
	
	String userId = (String) session.getAttribute("userId");
	String loginStatus = (String) session.getAttribute("loginStatus");
	Date startDate = (Date) session.getAttribute("startDate");
	Date endDate = (Date) session.getAttribute("endDate");
	ArrayList<BoardVO> list_item = new ArrayList<BoardVO>();
	
	// 지금은 JSON array가 날아온다.
	// json array -> json
	 
	if(loginStatus==null) loginStatus = "d";
%> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ajax test</title>
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
		.page_box{
			text-align:center;
		}
		.page_ul {
			display:inline-block; 
			*display:inline; 
			zoom:1;
		}
		.page_li {
			float:left;
			margin-left:-1px;
			z-index:1;
		}
	</style>
	<script src="js/bootstrap.min.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script type="text/javascript">
	
	console.log("hihi");
	var search_form; //JS의 전역 변수
	var page_input_element;
	
	
	console.log("search form"+search_form);
	var pageNo = 1;

	function make_jquery_ajax(pageNo){ //Document.ready 로 DOM Element 모두 로딩된 이후에만 호출 됨.
		
		var url_str = "${pageContext.request.contextPath}/list2"; 
		
		// HTML search form element 가져오기
		// form element의 요소에 pageNo 넣기
		
		var page_inputbox_element = document.getElementById("page_number");
		page_inputbox_element.value = pageNo.toString();
		
		var params = jQuery("#search_form").serialize();
		//검색어 없는 경우까지 와 페이지 정보까지 전부 고려되어 있는 URL 쿼리 

		$.ajax({
			url: url_str,
			data : params, //data sent to the server
			dataType: 'json', //서버에서 보내는 데이터 타입
			//processData : false,
			//contentType: false,
			method: "POST", //요청이 제대로 왔을까? 어디서??? 
			success:function (result) { //제어권 나에게 없음 . 컨트롤러가 가짐.
				
				var str = "";
				console.log("게시글 리스트"+ result.board_list);
				var board_list = JSON.stringify(result.board_list); //객체를 JSON으로
	//			var page_maker = JSON.stringify(result.page_maker);
				
				console.log("게시글 리스트 TO JSON" + board_list);
				
				//검색 처리를 위한 form 전송.
				
				//console.log("json values"+Object.values(board_list));
				var list_test;
				list_test = JSON.parse(board_list);
				
				console.log(list_test);
				$.each(list_test, function(key, data){ //JSON -> Object -> JSON ?
					str += '<tr>'; 							
					console.log("게시글 리스트 TO JSON" + key + " " + data.title + " "+data.writer);
					str += '<td>' + data.content_id + '</td>';
					str += '<td>' + data.title + '</td>';
					str += '<td>' + data.writer + '</td>';
					str += '</tr>';
				}); 
				// 현재 페이지 정보  & 현재 페이지에 따른 게시글  list의 정보가 넘어옴
				
				$(".board_content_list").append(str); //이까지는 잘 됨. 
			},
			error:function(request, status, error){
				console.log("error");
			}
		}); //end of ajax
	} // end of make ajax function
	$(document).ready(function(){ // document.ready
		
		search_form = document.getElementById('search_form');
		console.log("search form" + search_form);
		
		//var formData;
		//formData = new FormData(search_form);
		//formData.append('pageNO', pageNo_str);
		//var pageNo_str = pageNo.toString();
		
		// AJAX로 폼 전송을 가능케 해주는 formdata 객체
		//console.log("form data"+ formData);
		
		make_jquery_ajax(pageNo); // 초기 리스트 만들기.
		// document.ready와 함께 ajax 요청은 계속 성립되고 있지 않나? 굳이 또 호출 안해도. 
		
		$("#moreBtn").click(function(){ 
			pageNo++;

			make_jquery_ajax(pageNo);
			console.log("페이지번호 : " +pageNo);
		});
		
		$("#searchBtn").click(function(){
			
			/*
				form 처리를 위한 영역 
			*/
			var params = jQuery("#search_form").serialize();
			//입력된 모든 element를 문자열의 데이터에 serialize한다.
			console.log("넘어갈 파라미터"+params);
			// AJAX로 리스트 들고 올 때만 pageNo 필요하니까
			page_input_element = document.getElementById("page_number");
			console.log("page input element"+page_input_element);
			
			page_input_element.value=pageNo;
			 
			pageNo = 1; //검색-> 리스트초기화
			
			make_jquery_ajax(pageNo);
			 
			$(".board_content_list").empty();
			// get 요청 위해 list2 리로드.....
			
			console.log("페이지번호 : " +pageNo);
		});	
	}); //end of jquery document.ready 
	</script>	
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
		
		var login_status = '<%=loginStatus %>';
		
		//default = d
		
		if(login_status =="f"){
			alert("비밀번호가 잘못됨!");
		}
		else if(login_status=="n"){
			alert("아이디없음!!");
		} 
	</script>	
</head>
<body>
	<div class="container">
		<div class="page-header">
			<h1>Board</h1>
		</div>
		

		<div class="login_header">
			<c:choose>
				<c:when test="${sessionScope.userId==null}">
					<form action="login" method="post">
						ID <input type="text" name="userId" /> 
						Password <input type="password" name="userPasswd" maxlength='10'/>
						<button type="submit">LOGIN </button>
						<button type="submit">LOGOUT</button>
					</form>
				</c:when>
				<c:otherwise>
					${sessionScope.userId}님이 로그인 중입니다.
					<a href="logout">로그아웃</a>
				</c:otherwise>
			</c:choose>		
		</div>
		
		<div class="header_div">
			<a href="write_view" >등록 페이지</a>
		</div>  
		
		<div class="content_container" >
			<table class="boardList table-striped table-bordered table-hover active">
				<thead> 
					<tr>
						<th width="15%" style="text-align:center">글 번호</th>
						<th width="55%" style="text-align:center">글 제목</th>
						<th width="30%" style="text-align:center">글 작성자</th>
					</tr>
				</thead>
				<tbody class="board_content_list">
					<!--  tr는 JS에서 append 시킴. -->				
				</tbody>

			</table>	
		
		</div>
		<input type="button" id="moreBtn" value="MORE" />
 
		<!-- 컨트롤러에서 넘어 온 페이지 Maker 사용해서 페이지 리스트 만듬-->
		<!-- next 버튼 -->
		 	 	
	 	<!--  검색 관련  box -->
	 	<div class="search_box" style="text-align:center">
	 		<form action="list2" id="search_form" method="POST" name="search_form">
		 		<select name="search_type">
			 		<option value="title"> 제목 </option>
			 		<option value="content"> 내용 </option>
			 		<option value="writer">작성자</option>
			 	</select>
			 	<!-- form에서 파라미터명은 name에 바인딩 되므로. search type = value값.으로 바인딩되서  넘어가겠지.-->
			 	<!-- input box에 있는 데이터는 get방식 파라미터로 넘어간다. --> 
			 	
			 	<input type="text" name="search_text" class="search_text_input" value="${search_text}"/> 
			 	
		 		<button type="submit" id = "searchBtn">검색하기</button>
		 		<span style="margin-left:40px;">날짜 </span>
		 		
		 		<input type="date" name="start_date" class="start_date_input" value="${start_date}"/>
			 	<input type="date" name="end_date" class="end_date_input" value="${end_date}"/>	
			 	<input type="hidden" name="pageNO" id = "page_number" value="1"/>
	 		</form> 
	 	</div>
	 	
		<!-- 컨트롤러에서 넘어 온 페이지 Maker 사용해서 페이지 리스트 만듬-->
		<!-- next 버튼 -->
	</div> <!--  end of page footer -->
  </div> <!--  END OF CONTAINER -->
</body>

</html> 