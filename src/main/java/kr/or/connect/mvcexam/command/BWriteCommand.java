package kr.or.connect.mvcexam.command;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.or.connect.mvcexam.dao.BoardDAO;

public class BWriteCommand implements BCommand{
 
	@Override
	public void execute(Model model) { 
		
		Map<String, Object> board_map =  model.asMap();
		
		//Multipart~ 실체 없고 HttpServletRequest와 Multipart를 상속받기만 하므로
		// HttpServlet리퀘스트에서 사용한 모든 함수를 동일 사용 가능
		
		// 파일 첨부 부분만 바뀐다.
		
		/*try {
			request_board.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {  
		}
		
		String title = request_board.getParameter("title");
		String content = request_board.getParameter("content");
		String writer =  request_board.getParameter("writer");
 
		
		MultipartFile file1 = request_board.getFile("file1");
		System.out.println("객체"+file1);
		*/
		String title = (String)board_map.get("title");
		String content = (String)board_map.get("content");
		String writer = (String)board_map.get("writer");
		MultipartFile file1 =  (MultipartFile) board_map.get("file1");
		
		System.out.println(file1.getOriginalFilename());
		
		BoardDAO dao = new BoardDAO();
	
		if(file1==null) { //첨부 파일 없는 경우
			dao.text_write(title, content, writer);
		}
		else { //첨부 파일 있는 경우
			dao.file_write(file1);
		}
		
		System.out.println("writer:"+writer);
		
	}
}
