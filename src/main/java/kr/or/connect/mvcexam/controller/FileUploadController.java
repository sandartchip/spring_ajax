package kr.or.connect.mvcexam.controller;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FileUploadController {


	// xml에 설정된 리소스 참조
	// bean의 id가 uploadPath인 태그를 참조
	
	
//	@Resource(name="uploadPath")
//	String uploadPath;
	
	
	//업로드 흐름 : write_view에서 save 버튼 클릭 -> 
	// write 서블릿으로 넘어감 -> write 서블릿 호출 시 파일 첨부 기능도 같이 처리한다.
	// 디렉토리에 업로드 -> 지정된 디렉토리에 저장 -> 파일 정보가 file에 저장
	@RequestMapping(value="/write", method=RequestMethod.POST) //write_view.jsp에서 post방식으로 처리했으니
	//public ModelAndView uploadForm(MultipartFile file, ModelAndView mav) throws Exception{
	
	public ModelAndView uploadForm(MultipartFile file, ModelAndView mav) throws Exception{
		System.out.println("파일 이름:"+file.getOriginalFilename());
		System.out.println("파일 크기:"+file.getSize());
		System.out.println("컨텐츠 타입:"+file.getContentType());
		
		String savedName = file.getOriginalFilename();
		String fileSavePath = "C:\\spring_file";
		File target = new File(fileSavePath, savedName);
		
		//임시 디렉토리에 저장된 업로드된 파일을 지정된 디렑토리로 복사
		FileCopyUtils.copy(file.getBytes(), target);
		
		mav.setViewName("redirect:list");
		mav.addObject("savedName", savedName);
		return mav;//list.jsp로....
	}
}
