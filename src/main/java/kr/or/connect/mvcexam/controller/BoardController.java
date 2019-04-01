package kr.or.connect.mvcexam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.connect.mvcexam.command.BCommand;
import kr.or.connect.mvcexam.command.BContentCommand;
import kr.or.connect.mvcexam.command.BListCommand;

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
}
