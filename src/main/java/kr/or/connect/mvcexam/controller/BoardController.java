package kr.or.connect.mvcexam.controller;

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

@Controller
public class BoardController {
	BCommand command;  
	
	@RequestMapping("/list")
	public String list(Model model) {

		System.out.println("list 호출");
		
		command = new BListCommand();
		command.execute(model);
		
		return "list"; //list.jsp
	}
	
	@RequestMapping("/view")
	public String content_view(HttpServletRequest request, Model model) { 
		
		//HttpServletRequest의 getParameter() 메서드를 이용하여 파라미터값 가져올 수 있다.
		
		System.out.println("content view");
		model.addAttribute("request", request);
		 
		command = new BContentCommand(); 
		//BContentCommand에서 모델에 저장된 request를 통해 
		//request.getAttribute("content"); 로 컨텐츠 데이터 가져온다
		
		command.execute(model); //리퀘스트 값 가진(content_id) 모델을 커맨드에 넣음. 
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
		System.out.println("--delete()--");
		
		model.addAttribute("delete_request", request);
		command = new BDeleteCommand();
		command.execute(model);
		return "redirect:list"; 
	}
	
	@RequestMapping("/modify_view")
	public String modify_view(HttpServletRequest request, Model model) {
		// 상세보기에서 content id 가져오기 위해  HttpServletRequest 객체사용
		
		System.out.println("modify view!");
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
