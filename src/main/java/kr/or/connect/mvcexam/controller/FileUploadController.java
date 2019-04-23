package kr.or.connect.mvcexam.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.or.connect.mvcexam.command.BCommand;
import kr.or.connect.mvcexam.command.BWriteCommand;
import kr.or.connect.mvcexam.dao.BoardDAO;
import kr.or.connect.mvcexam.vo.Criteria;
import kr.or.connect.mvcexam.vo.PageMaker;

@Controller
public class FileUploadController {

	//@RequestMapping(value="/write", method=RequestMethod.POST) 

	BCommand command; 
	Criteria paging_info; //현재 페이지 번호, 페이지 당 게시물 수를 가지고 하나의 페이지를 표시하기 위한 정보를 가진 page VO. 
	PageMaker page_list_maker;
	String redirect_with_search; 

	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println();
		return "write_view";
	}
	
	@GetMapping("/write")
	public String uploadForm() {
		return "file_upload_ok"; //뷰로 리턴.
	}
	
	@PostMapping("/write")
	public String uploadForm(@RequestParam(value = "file", defaultValue="false") MultipartFile file1, 
			@RequestParam("title") String title,
			@RequestParam("content") String content, 
			@RequestParam("writer") String writer, 
			Model model) throws Exception{ // jsp(뷰)에서 Controller로 넘어오면서 자동으로 매개변수에 Model 객체에 설정된다.

		//request.setCharacterEncoding("UTF-8");
		//jsp에서 넘겨받은 request 객체를 utf-8로 받아와야 함.????
		
		BoardDAO dao = new BoardDAO();
		dao.text_write(title,  content,  writer);
		
		Map<String, Object> map= new HashMap<String, Object>();
		
		map.put("title", title);
		map.put("content", content);
		map.put("writer", writer);
		
		System.out.println("file1 = "+file1);
		long file_size = file1.getSize();
		
		if(file_size >0)	{ //첨부된 파일 있는 경우.
			map.put("file1", file1);

			dao.file_write(file1);

			System.out.println("파일 이름:"+file1.getOriginalFilename());
			System.out.println("파일 크기:"+file1.getSize());
			System.out.println("컨텐츠 타입:"+file1.getContentType());
			

			String savedName = file1.getOriginalFilename();
			String fileSavePath = "C:\\spring_file";

			//String fileSavePath = file.getO; 
			File target = new File(fileSavePath, savedName);
			FileCopyUtils.copy(file1.getBytes(), target);			
		}
		
		model.addAllAttributes(map);
		command = new BWriteCommand();
		command.execute(model);

	 
		return "redirect:list2";
	}
	
	//ModelAndView 로 리턴한 값 -> view Resolver에서  처리.
	
}
