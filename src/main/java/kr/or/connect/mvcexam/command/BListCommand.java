package kr.or.connect.mvcexam.command; 
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;

import org.springframework.ui.Model;

import kr.or.connect.mvcexam.dao.BoardDAO;
import kr.or.connect.mvcexam.vo.BoardVO;
 
public class BListCommand extends HttpServlet implements BCommand  {
	private static final long serialVersionUID = 1L;
	ArrayList<BoardVO> vo_list; 
    
    @Override
    public void execute(Model model) {
    	
    	BoardDAO dao = new BoardDAO();
    	ArrayList<BoardVO> vo_list = dao.list();
    	
    	model.addAttribute("list_item", vo_list);   
    } 
}
    