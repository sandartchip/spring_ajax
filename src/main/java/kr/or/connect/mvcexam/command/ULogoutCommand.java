package kr.or.connect.mvcexam.command;

import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import kr.or.connect.mvcexam.dao.UserDAO;

public class ULogoutCommand extends HttpServlet implements UCommand{
	
	
	@Override
	public void execute(HttpServletRequest request, Model model) {

		Map<String, Object> model_map = model.asMap();
		
		HttpSession session = request.getSession();
		
		UserDAO dao = new UserDAO();
		
		dao.logout();
		
		session.setAttribute("userId", null);
		session.setAttribute("userPasswd", null);
	}
}
