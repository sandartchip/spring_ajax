package kr.or.connect.mvcexam.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import kr.or.connect.mvcexam.dao.BoardDAO;

public class BDeleteCommand implements BCommand{

	@Override
	public void execute(Model model) {
		String content_id;

		Map<String, Object> board_map = model.asMap();
		//모델 객체를 map형태로 받아 온다.

		HttpServletRequest delete_request = (HttpServletRequest) board_map.get("delete_request");
		//map형태로 받아 온 model 객체에서 delete request형만 따로 받아온다.
		//content id 값 받아오기 위해
		//http servlet request 객체 사용
		
		content_id = delete_request.getParameter("content_id");
		
		BoardDAO dao = new BoardDAO();
		dao.delete(content_id);
	} 
}
