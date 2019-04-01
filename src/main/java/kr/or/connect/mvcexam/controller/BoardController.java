package kr.or.connect.mvcexam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.connect.mvcexam.command.BCommand;
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
}
