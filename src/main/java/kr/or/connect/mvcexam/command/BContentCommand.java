package kr.or.connect.mvcexam.command;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import kr.or.connect.mvcexam.dao.BoardDAO;
import kr.or.connect.mvcexam.vo.BoardVO;

public class BContentCommand implements BCommand{ 
	
	@Override
	public void execute(Model model) {
		
		Map<String, Object> map = model.asMap(); //map으로 모델을 바꿈
		
		HttpServletRequest request = (HttpServletRequest) map.get("request"); 
		//  모델 객체에 이미 "request"가 셋 되어 있고 그 리퀘스트 객체를 가져온다.
		// 그 리퀘스트 객체에는 content id가 있다.
		
		String content_id = request.getParameter("content_id"); //list에서 GET방식으로 넘어온 content id
		
		System.out.println("상세 보기 페이지! content id = "+ content_id);
		
		BoardDAO dao = new BoardDAO(); 
		BoardVO vo = dao.contentView(content_id);
		
		model.addAttribute("content", vo);
	}
}
