package kr.or.connect.mvcexam.command; 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.springframework.ui.Model;

import kr.or.connect.mvcexam.dao.BoardDAO;
import kr.or.connect.mvcexam.vo.BoardVO;
import kr.or.connect.mvcexam.vo.Criteria;
import kr.or.connect.mvcexam.vo.PageMaker;
 
public class BListCommand extends HttpServlet implements BCommand  {
	private static final long serialVersionUID = 1L;
	ArrayList<BoardVO> vo_list; 
    
    @Override
    public void execute(Model model) {
    	
    	// list 부르면 -> 하단에 페이지 리스트 까지 동시에 계산한다. 
    	
    	//리스트는 전달받을 파라미터 없으므로 HttpServletRequest 안써도 됨.
    	// -> 페이지 기능 추가하면서 PageVO 가져와서
    	// page_num, int num_per_page 가져옴. 
    	// 모델 에 이미 pageVO가 추가되있어야 함.
    	// 페이지 당 리스트 출력.
    	
    	//currentPageNum;
    	Map<String, Object> model_map = model.asMap();

    	Criteria cur_page_info = (Criteria) model_map.get("pageVO"); //모델에서 Criteria형의 pageVO 객체 가져 온다.
    	String search_keyword = (String) model_map.get("search_keyword"); 
    	if(search_keyword==null) search_keyword="";//넘어온 파라미터 없는 경우
    	System.out.println("---List Command ---: "+search_keyword);

    	System.out.println("-----PageMaker 생성---------------");
    	
    	BoardDAO dao = new BoardDAO();
    	ArrayList<BoardVO> vo_list;
    	
    	int total_row_count;

    	try {
			vo_list = dao.search_page(search_keyword, cur_page_info);
			model.addAttribute("list_item", vo_list);   //DAO를 통해 DB처리한 데이터 받아와서 모델에 넣음.
			
			if(search_keyword.length()==0) 	{ //검색 키워드 없는 경우.
				total_row_count = dao.totalCount();
			}
			else {
				total_row_count = dao.search_totalCount(search_keyword);
			}
	    	// 전체, 일부 처리 ok
			//total_row_count = vo_list.size();
			System.out.println("total count: " +total_row_count);
			model.addAttribute("total_count", total_row_count);//DAO를 통해 얻어온 총 개수를 모델에 넣음.
		} catch (SQLException e) {
			e.printStackTrace();
		}
    } 
}
    