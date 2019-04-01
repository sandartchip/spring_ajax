package kr.or.connect.mvcexam.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import kr.or.connect.mvcexam.dao.BoardDAO;

public class BWriteCommand implements BCommand{

	@Override
	public void execute(Model model) {
		 
		Map<String, Object> board_map = model.asMap();
		HttpServletRequest request_board = (HttpServletRequest) board_map.get("write_request");  //form에서 Request paramete로  title, content 
		
		String title = request_board.getParameter("title");
		String content = request_board.getParameter("content");
		
		BoardDAO dao = new BoardDAO();
		dao.write(title, content);
	}

}
