package kr.or.connect.mvcexam.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.ui.Model;

import kr.or.connect.mvcexam.dao.BoardDAO;
import kr.or.connect.mvcexam.vo.BoardVO;
import kr.or.connect.mvcexam.vo.Criteria;
import kr.or.connect.mvcexam.vo.PageMaker;

public class BSearchCommand implements BCommand {
	
	@Override
	public void execute(Model model) {
		
		Map<String, Object> model_map = model.asMap(); //모델에서 데이터 가져오기 위해 map형으로 바꾼다.
		
		Criteria cur_page_info = (Criteria) model_map.get("pageVO"); //모델에서 Criteria형의 pageVO 객체 가져 온다.
    	//System.out.println("-----PageMaker 생성---------------");
    	
		BoardDAO dao = new BoardDAO();
    	ArrayList<BoardVO> search_vo_list;
  
    	String search_keyword = (String) model_map.get("search_keyword");
    	
    	int total_row_count=0;

    	try {
			search_vo_list = dao.search_page(search_keyword, cur_page_info);
			model.addAttribute("list_item", search_vo_list);   
			//DAO를 통해 DB처리한 데이터 받아와서 모델에 넣음. 

			int search_row_count = search_vo_list.size();
			
	    	total_row_count = dao.search_totalCount(search_keyword); //전체 게시글이 아니라. 검색된 게시물만 가져 와야.
	    	
			model.addAttribute("total_count", search_row_count);//DAO를 통해 얻어온 총 개수를 모델에 넣음.
			
			System.out.println("search total count: " +search_row_count);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
