package kr.or.connect.mvcexam.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.or.connect.mvcexam.command.BCommand;
import kr.or.connect.mvcexam.command.BListCommand;
import kr.or.connect.mvcexam.vo.BoardVO;
import kr.or.connect.mvcexam.vo.Criteria;
import kr.or.connect.mvcexam.vo.PageMaker;


@Controller
public class BoardAjaxController  {
//java -> HTTP Body
		
	@RequestMapping(value="/list2", method=RequestMethod.GET)  
	public ModelAndView goto_jsp() {
		System.out.println("ohoho");
		ModelAndView view = new ModelAndView();
		
		view.setViewName("list2"); 
		return view;
	} 

	BCommand command; 
	Criteria paging_info; //현재 페이지 번호, 페이지 당 게시물 수를 가지고 하나의 페이지를 표시하기 위한 정보를 가진 page VO. 
	PageMaker page_list_maker;
	String redirect_with_search;  
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list2", method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> print_list(
			@RequestParam HashMap<String, String> paramMap, 
			HttpServletRequest request, Model model) 
			throws ParseException, UnsupportedEncodingException { //필요한 데이터 넘기기 위해 model 객체를 파라미터로 넘겨준다.
		
		/* list item, curPage, total Count만 바뀜. 나머지는 다 같다. */ 
		
		/* form으로 넘어온 데이터를 request로 받음.*/
		request.setCharacterEncoding("UTF-8");
		
		//String search_keyword = request.getParameter("search_text"); 
		String search_keyword = paramMap.get("search_text"); 
		
		//String pageNo = request.getParameter("pageNO");
		String pageNo = paramMap.get("pageNO"); 

		// page number를 .
		
		//String search_type = request.getParameter("search_type");
		String search_type = paramMap.get("search_type"); 

		System.out.println("search type" + search_type);
		System.out.println("search text" + search_keyword);
		// UTF-8 줬는데 왜 안되지?? 
		
		Date start_date, end_date; 
		HttpSession session = request.getSession(); //서블릿에서 세션 사용 위해 메서드 호출.
 
//		String start_date_s = request.getParameter("start_date")==null? "":request.getParameter("start_date");
		String start_date_s = paramMap.get("start_date")==null? "":paramMap.get("start_date");
		
//		String end_date_s = request.getParameter("end_date")==null?"":request.getParameter("end_date");
		String end_date_s = paramMap.get("end_date")==null? "":paramMap.get("end_date");
		
		
		if(pageNo==null || pageNo.length()==0) {
			pageNo="1";
			System.out.println("no number");
		}
		if(search_keyword==null) search_keyword="";
		else {
			if(search_keyword.length()>0) {
		//		URLDecoder.decode(request.getParameter("search_text"), "UTF-8");
				URLDecoder.decode(paramMap.get("search_text"), "UTF-8");
			}
		}
		if(search_type==null) search_type=""; // 초기호출. 파라미터 없을 때
		
		
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
		
		
		Map <String, Object> map = new HashMap<>();
		
		map.put("board_list", vo_list);
		map.put("page_maker", page_list_maker);
		
		return map; // 리스트와 페이지 정보 라는 다른 type의 데이터를 같이 넘겨야해서 map을 쓰는 수 밖에 
		// 없음.
		
		// Controller가 지정한 view 파일명. ->
		// web.xml->WebMvcContextConfiguration
		// ->list.jsp 로 페이지 page listMaker 보낸다.	
	}
	
}
