package kr.or.connect.mvcexam.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import kr.or.connect.mvcexam.command.UCommand;
import kr.or.connect.mvcexam.command.ULoginCommand;
import kr.or.connect.mvcexam.command.ULogoutCommand;

@Controller
public class UserController {
	 
	UCommand command;
	@RequestMapping("/login")
	public String login(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		 
		request.setCharacterEncoding("UTF-8");
		
		//매개변수로 받아온 request에 바인딩된 
		String userId     		= request.getParameter("userId"); 
		String userPasswd = request.getParameter("userPasswd");

		//String userId="yachika";
		//String userPasswd="ohoho!";
		//현재 보안을 위해 URL인코딩 된 상태로 넘어온다.
 	
		// URL Decoding(userPasswd crypto)
		
		System.out.println("화면에서 넘어옴"+userId);
		
		model.addAttribute("userId", userId);
		model.addAttribute("userPasswd", userPasswd); 
		//model.addAttribute("request", request);
		// request 파라미터로  수정할 content id 가지고 있는 request를 가져 옴.
		// 모델에 해당 content id 가지고 있는 request 파라미터를 추가해준다.
		//로그인 파라미터 받아와서 세션에 넣는다.
		
		command = new ULoginCommand();
		command.execute(request, model);
		//model에 넣은게 왜 파라미터로 붙지????????????????????????????????????????????????????????
		
		return "redirect:list"; // 로 이동???????????? 파라미터 없이 갔는데 userId가 왜 붙지??
		//redirect 해야 하는 이유 : return 한걸 디스패처 서블릿에서 forward로 처리했었음.
		//list를 브라우저상에서 재 호출 해야 -> 해당 list의 값 
		//->맞나?
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, Model model) {
		
		command = new ULogoutCommand();
		command.execute(request, model);
		
		return "redirect:list";
	}
}
