package kr.or.connect.mvcexam.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.connect.mvcexam.command.BCommand;
import kr.or.connect.mvcexam.command.BContentCommand;
import kr.or.connect.mvcexam.command.BDeleteCommand;
import kr.or.connect.mvcexam.command.BListCommand;
import kr.or.connect.mvcexam.command.BModifyCommand;
import kr.or.connect.mvcexam.command.BWriteCommand;
import kr.or.connect.mvcexam.vo.BoardVO;
import kr.or.connect.mvcexam.vo.Criteria;
import kr.or.connect.mvcexam.vo.PageMaker;
 
@Controller
public class BoardController {
	
	/* 브라우저의 요청을 받아서  데이터-모델을 서비스(command)로 넘겨준다. */ 
	
	BCommand command; 
	Criteria paging_info; //현재 페이지 번호, 페이지 당 게시물 수를 가지고 하나의 페이지를 표시하기 위한 정보를 가진 page VO. 
	PageMaker page_list_maker;
	String redirect_with_search; 
	 
	// 파일 첨부 처리 용 
/*	public String make_search_url (MultipartHttpServletRequest request) throws UnsupportedEncodingException {
		// 어떤 작업 후 List로 리턴할 때 검색어, 검색 타입, 시작 날짜, 끝나는 날짜 파라미터를 유지하기 위해.
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(); 
		
		PageMaker page_maker;
		page_maker = (PageMaker) session.getAttribute("page_list_maker"); 

		// 검색 시 redirect 위해서. 
		String pageNO;
		String search_text = (String) session.getAttribute("search_text");
		String search_type = (String) session.getAttribute("search_type");
		Date start_date = (Date) session.getAttribute("start_date");
		Date end_date   = (Date) session.getAttribute("end_date");
		
		String s_start_date, s_end_date;
		
		s_start_date = start_date==null?"":start_date.toString(); //date가 null인 경우 
		s_end_date = end_date	 ==null?"":end_date.toString();
		 
		String encode_search_text = URLEncoder.encode(search_text, "UTF-8");
		String return_url = "redirect:list2?pageNO="+((page_maker.getStartPage()) -1)+
				"&search_text="+encode_search_text + "&search_type="+search_type+
				"&start_date="+s_start_date + "&end_date="+s_end_date;
				
		return return_url;
	}*/

	// 검색어 파라미터 처리를 위한 URL만드는 함수
	public String make_search_url (HttpServletRequest request) throws UnsupportedEncodingException {
		// 어떤 작업 후 List로 리턴할 때 검색어, 검색 타입, 시작 날짜, 끝나는 날짜 파라미터를 유지하기 위해.
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(); 
		
		PageMaker page_maker;
		page_maker = (PageMaker) session.getAttribute("page_list_maker"); 

		/* 검색 시 redirect 위해서.*/ 
		String pageNO;
		String search_text = (String) session.getAttribute("search_text");
		String search_type = (String) session.getAttribute("search_type");
		Date start_date = (Date) session.getAttribute("start_date");
		Date end_date   = (Date) session.getAttribute("end_date");
		
		String s_start_date, s_end_date;
		
		s_start_date = start_date==null?"":start_date.toString(); //date가 null인 경우 
		s_end_date = end_date	 ==null?"":end_date.toString();
		 
		String encode_search_text = URLEncoder.encode(search_text, "UTF-8");
		String return_url = "redirect:list2?pageNO="+((page_maker.getStartPage()) -1)+
				"&search_text="+encode_search_text + "&search_type="+search_type+
				"&start_date="+s_start_date + "&end_date="+s_end_date;
				
		return return_url;
	}

	
	//JSON 미 적용 버전
	//@SuppressWarnings("unchecked")
	//@RequestMapping("/list")  //브라우저의 요청을 받을 때 
	public String list(HttpServletRequest request, Model model) throws ParseException, UnsupportedEncodingException { //필요한 데이터 넘기기 위해 model 객체를 파라미터로 넘겨준다.
		
		/* list item, curPage, total Count만 바뀜. 나머지는 다 같다. */ 
		
		/* form으로 넘어온 데이터를 request로 받음.*/
		request.setCharacterEncoding("UTF-8");
		
		String search_keyword = request.getParameter("search_text"); 
		
		String pageNo = request.getParameter("pageNO");
		String search_type = request.getParameter("search_type");
		
		// UTF-8 줬는데 왜 안되지?? 
		
		Date start_date, end_date; 
		HttpSession session = request.getSession(); //서블릿에서 세션 사용 위해 메서드 호출.
 
		String start_date_s = request.getParameter("start_date")==null? "":request.getParameter("start_date");
		String end_date_s = request.getParameter("end_date")==null?"":request.getParameter("end_date");
		
		
		if(pageNo==null || pageNo.length()==0) {
			pageNo="1";
			System.out.println("no number");
		}
		if(search_keyword==null) search_keyword="";
		if(search_type==null) search_type=""; // 초기호출. 파라미터 없을 때
		
		if(search_keyword.length()>0) {
			URLDecoder.decode(request.getParameter("search_text"), "UTF-8");
		}
		

		java.util.Date util_start_date;
		java.util.Date util_end_date;
		SimpleDateFormat transFormat;
		

		session.setAttribute("start_date", null);
		session.setAttribute("end_date", null);     //Null값인 경우도 검색어 보존 위해 Session에 저장. 
		
		transFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		
		System.out.println("start_date"+start_date_s);
		System.out.println("end_date"+end_date_s);
		
		if(start_date_s.length() >= 1) {
			util_start_date = transFormat.parse( start_date_s );
			start_date = new java.sql.Date(util_start_date.getTime());
			
			model.addAttribute("start_date", start_date);
			session.setAttribute("start_date", start_date); //유효한 값일 경우 다시 set	 
		}

		if(end_date_s.length()>=1) {
			
			util_end_date =  transFormat.parse( end_date_s );
			end_date =   new java.sql.Date(util_end_date.getTime());
			model.addAttribute("end_date", end_date);
			session.setAttribute("end_date", end_date); 
		}

		model.addAttribute("search_keyword", search_keyword);
		model.addAttribute("search_type", search_type);
		
		session.setAttribute("search_text", search_keyword);
		session.setAttribute("search_type", search_type);

		
		// 가장 처음에 접속했을 땐 페이지 정보가 없다-> pageNO=1로 미리 셋팅 후 PageMaker 만든다.
		
		int pageNumber = Integer.parseInt(pageNo);
		
		/* 페이지 만들기 */
		
		paging_info = new Criteria(); //현재 페이지 표시 용.
		paging_info.setCurPage(pageNumber);
		PageMaker page_list_maker = new PageMaker();  //화면 하단 페이지 표시 용.
	 
		model.addAttribute("pageVO", paging_info); //Criteria형의 paging info를 model에 추가.
 
		command = new BListCommand();
		command.execute(model); 
		// 서비스 단 갔다가 돌아 오면서 model 객체에 total_count 가져 올 것임.
		
		Map<String, Object> model_map = model.asMap();
		
		int totalCount = (int) model_map.get("total_count");
		
		/* 하단에 페이지 메이커 생성  */
		page_list_maker.setPage_vo(paging_info);
		page_list_maker.setTotalCount(totalCount);
		page_list_maker.calcData();
		
		model.addAttribute("page_list_maker", page_list_maker); //page list maker를 넣음 
		session.setAttribute("page_list_maker", page_list_maker);
		
		
		/*  redirect 고려하지 않은 경우      */
		 
		return "list"; 
		// Controller가 지정한 view 파일명. ->
		// web.xml->WebMvcContextConfiguration
		// ->list.jsp 로 페이지 page listMaker 보낸다.	
	}


	//@RequestMapping("/list")
	public @ResponseBody ArrayList<BoardVO> print_list(HttpServletRequest request, Model model) throws ParseException, UnsupportedEncodingException { //필요한 데이터 넘기기 위해 model 객체를 파라미터로 넘겨준다.
		
		/* list item, curPage, total Count만 바뀜. 나머지는 다 같다. */ 
		
		/* form으로 넘어온 데이터를 request로 받음.*/
		request.setCharacterEncoding("UTF-8");
		
		String search_keyword = request.getParameter("search_text"); 
		
		String pageNo = request.getParameter("pageNO");
		String search_type = request.getParameter("search_type");
		
		// UTF-8 줬는데 왜 안되지?? 
		
		Date start_date, end_date; 
		HttpSession session = request.getSession(); //서블릿에서 세션 사용 위해 메서드 호출.
 
		String start_date_s = request.getParameter("start_date")==null? "":request.getParameter("start_date");
		String end_date_s = request.getParameter("end_date")==null?"":request.getParameter("end_date");
		
		
		if(pageNo==null || pageNo.length()==0) {
			pageNo="1";
			System.out.println("no number");
		}
		if(search_keyword==null) search_keyword="";
		if(search_type==null) search_type=""; // 초기호출. 파라미터 없을 때
		
		if(search_keyword.length()>0) {
			URLDecoder.decode(request.getParameter("search_text"), "UTF-8");
		}
		

		java.util.Date util_start_date;
		java.util.Date util_end_date;
		SimpleDateFormat transFormat;
		

		session.setAttribute("start_date", null);
		session.setAttribute("end_date", null);     //Null값인 경우도 검색어 보존 위해 Session에 저장. 
		
		transFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		
		System.out.println("start_date"+start_date_s);
		System.out.println("end_date"+end_date_s);
		
		if(start_date_s.length() >= 1) {
			util_start_date = transFormat.parse( start_date_s );
			start_date = new java.sql.Date(util_start_date.getTime());
			
			model.addAttribute("start_date", start_date);
			session.setAttribute("start_date", start_date); //유효한 값일 경우 다시 set	 
		}

		if(end_date_s.length()>=1) {
			
			util_end_date =  transFormat.parse( end_date_s );
			end_date =   new java.sql.Date(util_end_date.getTime());
			model.addAttribute("end_date", end_date);
			session.setAttribute("end_date", end_date); 
		}

		model.addAttribute("search_keyword", search_keyword);
		model.addAttribute("search_type", search_type);
		
		session.setAttribute("search_text", search_keyword);
		session.setAttribute("search_type", search_type);

		
		// 가장 처음에 접속했을 땐 페이지 정보가 없다-> pageNO=1로 미리 셋팅 후 PageMaker 만든다.
		
		int pageNumber = Integer.parseInt(pageNo);
		
		/* 페이지 만들기 */
		
		paging_info = new Criteria(); //현재 페이지 표시 용.
		paging_info.setCurPage(pageNumber);
		PageMaker page_list_maker = new PageMaker();  //화면 하단 페이지 표시 용.
	 
		model.addAttribute("pageVO", paging_info); //Criteria형의 paging info를 model에 추가.
 
		command = new BListCommand();
		command.execute(model); 
		// 서비스 단 갔다가 돌아 오면서 model 객체에 total_count 가져 올 것임.
		
		Map<String, Object> model_map = model.asMap();
		
		int totalCount = (int) model_map.get("total_count");
		
		/* 하단에 페이지 메이커 생성  */
		page_list_maker.setPage_vo(paging_info);
		page_list_maker.setTotalCount(totalCount);
		page_list_maker.calcData();
		
		model.addAttribute("page_list_maker", page_list_maker); //page list maker를 넣음 
		session.setAttribute("page_list_maker", page_list_maker);
		
		
		/*  redirect 고려하지 않은 경우      */
		// list command에서 model에서 list_item 추가 한 상태.
		
		ArrayList<BoardVO> vo_list = new ArrayList<BoardVO>(); 
		vo_list = (ArrayList<BoardVO>) model_map.get("list_item");
		
		return vo_list;
		
		// Controller가 지정한 view 파일명. ->
		// web.xml->WebMvcContextConfiguration
		// ->list.jsp 로 페이지 page listMaker 보낸다.	
	}
	
	//@RequestMapping("/view")
	public String content_view(HttpServletRequest request, Model model) {  
		
		System.out.println("content view");
		model.addAttribute("request", request); //모델 객체에 content id 정보를 가진 리퀘스트 객체 추가.
		 
		command = new BContentCommand(); 
		//BContentCommand에서 모델에 저장된 request를 통해 
		//request.getAttribute("content"); 로 컨텐츠 데이터 가져온다
		
		command.execute(model); //리퀘스트 값 가진(content_id) 모델을 커맨드에 전달한다.
		// Model을, Service 객체에 던짐.
		// 서비스에 넣을 모델에는 리퀘스트 객체가 이미 있다.(리퀘스트 객체에는 id값이 있다)
		
		System.out.println("view 호출");	
		return "view";
	} 
	
	//@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println();
		return "write_view";
	} 
	
	//@RequestMapping("/write")
	public String write(MultipartHttpServletRequest request, Model model) throws UnsupportedEncodingException {
		
		// HttpServletRequest의
		// request 파라미터에 form을 통해 전달 된 데이터가 name을 파라미터로 하여 전부 다 넘어 온다.
		// MultipartFile 객체 역시 jsp단에서 넘어온다.
		
		System.out.println("write()");
		
		request.setCharacterEncoding("UTF-8");
		//jsp에서 넘겨받은 request 객체를 utf-8로 받아와야 함.????
		
		
		model.addAttribute("write_request", request);
		command = new BWriteCommand();
		command.execute(model);
		 
		//model객체에 파일까지 들어가 있음.
		
		String return_url = make_search_url(request);
		return return_url; 
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		//content id 넘겨받기 위해 HttpServletRequest 객체 사용.
		System.out.println("--delete()--");
		
		model.addAttribute("delete_request", request);
		command = new BDeleteCommand();
		command.execute(model);
		
		/* redirect */
		
		String return_url = make_search_url(request);
		return return_url; 	
	}
	
	@RequestMapping("/modify_view")
	public String modify_view(HttpServletRequest request, Model model) {
		// 상세보기에서 content id 가져오기 위해  HttpServletRequest 객체사용
		
		System.out.println("modify view !");
		model.addAttribute("request", request); 
		// request 파라미터로  수정할 content id 가지고 있는 request를 가져 옴.
		// 모델에 해당 content id 가지고 있는 request 파라미터를 추가해준다.
		
		command = new BContentCommand();
		command.execute(model);

		// b content command로 넘어가면서 해당 content id 가진 모델 객체를 넘겨주고.
		// 서비스 객체는		model.addAttribute("content", vo); 을 통해 모델에  게시글 객체를 전달.

		return "modify_view"; // 게시글 객체를 modify view로 넘긴다.
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		System.out.println("  modify()  ");
		
		model.addAttribute("modify_request", request);
		command = new BModifyCommand();
		command.execute(model);
		
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(); 
		
		PageMaker page_maker;
		page_maker = (PageMaker) session.getAttribute("page_list_maker"); 

		
		String return_url = make_search_url(request);
		return return_url; 	
	}
}
