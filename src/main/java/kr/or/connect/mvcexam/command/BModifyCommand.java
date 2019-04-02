package kr.or.connect.mvcexam.command;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import kr.or.connect.mvcexam.dao.BoardDAO;

public class BModifyCommand implements BCommand{

	@Override
	public void execute(Model model) {
		
		Map<String, Object> map = model.asMap();
		HttpServletRequest request = (HttpServletRequest) map.get("modify_request");
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) { 
		}
		
		String content_id = request.getParameter("content_id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		
		BoardDAO dao = new BoardDAO();
		System.out.println(content_id +" "+ title + " "+ content);
		dao.modify(content_id, title, content);
	}

}
