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
    	
    	int currentPageNum, numPerPage;
    	
    	Map<String, Object> model_map = model.asMap(); //모델에서 데이터 가져오기 위해 map형으로 바꾼다.
    	Criteria cur_page_info = (Criteria) model_map.get("pageVO"); //모델에서 Criteria형의 pageVO 객체 가져 온다.
    	PageMaker pageMaker = (PageMaker) model_map.get("page_maker"); //페이지메이커 객체에 게시글 갯수 넣기 위해 가져옴.
    	System.out.println("-----PageMaker 생성---------------");
    	BoardDAO dao = new BoardDAO();
    	ArrayList<BoardVO> vo_list;
    	
    	int total_row_count;

    	try {
			vo_list = dao.list(cur_page_info);
			model.addAttribute("list_item", vo_list);   //DAO를 통해 DB처리한 데이터 받아와서 모델에 넣음.

	    	total_row_count = dao.totalCount();
			model.addAttribute("total_count", total_row_count);//DAO를 통해 얻어온 총 개수를 모델에 넣음.
		} catch (SQLException e) {
			e.printStackTrace();
		}
    } 
}
    