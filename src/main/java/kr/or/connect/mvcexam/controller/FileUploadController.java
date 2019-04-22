package kr.or.connect.mvcexam.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import kr.or.connect.mvcexam.vo.PageMaker;

@Controller
public class FileUploadController {

	
	
	//@RequestMapping(value="/write", method=RequestMethod.POST) 
	 
	public void uploadForm(MultipartFile file) throws Exception{
		
		System.out.println("파일 이름:"+file.getOriginalFilename());
		System.out.println("파일 크기:"+file.getSize());
		System.out.println("컨텐츠 타입:"+file.getContentType());
		
		if(file.getSize()==0) return;
		
		
		String savedName = file.getOriginalFilename();
		String fileSavePath = "C:\\spring_file";
		
		//String fileSavePath = file.getO; 
		File target = new File(fileSavePath, savedName);
		//File target = new File(uploadPath, savedName);
		
		//임시 디렉토리에 저장된 업로드된 파일을 지정된 디렑토리로 복사
		FileCopyUtils.copy(file.getBytes(), target);
		
		//String return_address = make_search_url();
		// mav.setViewName("redirect:list");
		// mav.addObject("savedName", savedName);
		//return mav;//list.jsp로....
	}
	
	//ModelAndView 로 리턴한 값 -> view Resolver에서  처리.
	
}
