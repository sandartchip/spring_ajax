package kr.or.connect.mvcexam.command;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import kr.or.connect.mvcexam.dao.UserDAO;

public class ULoginCommand extends HttpServlet implements UCommand {

	@Override
	public void execute(HttpServletRequest request, Model model) {
		
		Map<String, Object> model_map = model.asMap();
		
		String userId = (String) model_map.get("userId");
		String userPasswd = (String) model_map.get("userPasswd");
		String loginResult;
		UserDAO dao = new UserDAO();
		HttpSession session = request.getSession();

		try {
			loginResult = dao.login(userId, userPasswd);
			System.out.println("login 결과 : "+loginResult);
			
			if(loginResult.equals("success")) {
				System.out.println("");
				
				session.setAttribute("userId", userId);
				session.setAttribute("userPasswd", userPasswd);
				session.setAttribute("loginStatus", "s");
			}
			else if(loginResult.equals("fail")) {
				session.setAttribute("userId", null);
				session.setAttribute("userPasswd", null);
				session.setAttribute("loginStatus", "f");

				String userIdddd = (String)session.getAttribute(userId);
				System.out.println("사용자 ID : "+userIdddd);
			}
			else if(loginResult.equals("noData")) {
				session.setAttribute("userId", null);
				session.setAttribute("userPasswd", null);
				session.setAttribute("loginStatus", "n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
