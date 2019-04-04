package kr.or.connect.mvcexam.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.or.connect.mvcexam.command.BCommand;
import kr.or.connect.mvcexam.command.BContentCommand;
import kr.or.connect.mvcexam.command.BDeleteCommand;
import kr.or.connect.mvcexam.command.BListCommand;
import kr.or.connect.mvcexam.command.BModifyCommand;
import kr.or.connect.mvcexam.command.BWriteCommand;
import kr.or.connect.mvcexam.vo.Criteria;
import kr.or.connect.mvcexam.vo.PageMaker;

@Controller
public class BoardController {
	
	/* 브라우저의 요청을 받아서  데이터-모델을 서비스(command)로 넘겨준다. */ 
	
	BCommand command; 
	Criteria paging_info; //현재 페이지 번호, 페이지 당 게시물 수를 가지고 하나의 페이지를 표시하기 위한 정보를 가진 page VO. 
	PageMaker page_list_maker;
	
	@RequestMapping("/list")  //브라우저의 요청을 받을 때
	public String list(HttpServletRequest request, Model model) { //필요한 데이터 넘기기 위해 model 객체를 파라미터로 넘겨준다.
		 
		String pageNo = request.getParameter("pageNO");
		if(pageNo==null) pageNo="1";
		// 가장 처음에 접속했을 땐 pageNO=1로 미리 셋팅 후 PageMaker 만든다.
		
		int pageNumber = Integer.parseInt(pageNo);

		System.out.println("페이지번호 = "+pageNumber);
//		int pageNumber = (int) request.getAttribute("pageNO");
		
		paging_info = new Criteria(); //현재 페이지 표시 용.
		paging_info.setCurPage(pageNumber);
		PageMaker page_list_maker = new PageMaker();  //화면 하단 페이지 표시 용.
	
		// pageMaker에  setter로 정보 넣어야 함.
		// 초기화 값
		
		model.addAttribute("pageVO", paging_info); //Criteria형의 paging info를 model에 추가.
		//모델 객체에 페이지 정보 가진 Criteria 를 추가.
		
		//아니다. 모델에 pageVO 객체  추가하면 되지 왜 굳이 request를 쓰지?
		
		
		System.out.println("list 호출");
		
		command = new BListCommand();
		command.execute(model); 
		// 서비스 단 갔다가 돌아 오면서 model 객체에 total_count 가져 올 것임.
		
		Map<String, Object> model_map = model.asMap(); 
		
		int totalCount = (int) model_map.get("total_count");
		
		page_list_maker.setPage_vo(paging_info);
		page_list_maker.setTotalCount(totalCount);
		page_list_maker.calcData();
		model.addAttribute("page_list_maker", page_list_maker); //page list maker를 넣음 
		
		return "list"; //list.jsp 로 페이지 page listMaker 보낸다.
	}
	
	@RequestMapping("/view")
	public String content_view(HttpServletRequest request, Model model) { 
		
		//HttpServletRequest의 getParameter() 메서드를 이용하여 파라미터값 가져올 수 있다.
		
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
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		return "write_view";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {
		System.out.println("write()");
		
		model.addAttribute("write_request", request);   
		command = new BWriteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		//content id 넘겨받기 위해 HttpServletRequest 객체 사용.
		System.out.println("--delete()--");
		
		model.addAttribute("delete_request", request);
		command = new BDeleteCommand();
		command.execute(model);
		return "redirect:list"; 
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
	public String modify(HttpServletRequest request, Model model) {
		System.out.println("  modify()  ");
		
		model.addAttribute("modify_request", request);
		command = new BModifyCommand();
		command.execute(model);
		return "redirect:list";
	}
}
