package kr.or.connect.mvcexam.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.or.connect.mvcexam.command.UCommand;
import kr.or.connect.mvcexam.command.ULoginCommand;
import kr.or.connect.mvcexam.command.ULogoutCommand;

@Controller
public class UserController {
	 
	UCommand command;
	
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) throws Exception {		
		
		request.setCharacterEncoding("UTF-8");
		
		//매개변수로 받아온 request에 바인딩된 
		String userId     		= request.getParameter("userId"); 
		String userPasswd = request.getParameter("userPasswd");

		
		model.addAttribute("userId", userId);
		model.addAttribute("userPasswd", userPasswd);
		
		// request 파라미터로  수정할 content id 가지고 있는 request를 가져 옴.
		// 모델에 해당 content id 가지고 있는 request 파라미터를 추가해준다.
		// 로그인 파라미터 받아와서 세션에 넣는다.

		command = new ULoginCommand();
		command.execute(request, model);
		
		return "redirect:list2"; 
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, Model model) {
		
		command = new ULogoutCommand();
		command.execute(request, model);
		
		return "redirect:list2";
	}
}
