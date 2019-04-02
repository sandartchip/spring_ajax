package kr.or.connect.mvcexam.command;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import kr.or.connect.mvcexam.dao.BoardDAO;

public class BWriteCommand implements BCommand{

	@Override
	public void execute(Model model) {
		
		
		Map<String, Object> board_map = model.asMap();
		HttpServletRequest request_board = (HttpServletRequest) board_map.get("write_request");  //form에서 Request paramete로  title, content 
		
		// DB 한글 처리->OK
		// jsp단 한글처리->OK
		// get 파라미터로 한글 데이터 받아오기 전에  해당 리퀘스트 객체를 UTF-8로 설정해야 한다.
		
		try {
			request_board.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) { 
			e.printStackTrace();
		}
		
		String title = request_board.getParameter("title");
		String content = request_board.getParameter("content");
		
		BoardDAO dao = new BoardDAO();
		dao.write(title, content);
	}

}
