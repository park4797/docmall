package com.test.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.domain.ProductVO;
import com.test.service.AdProductService;
import com.test.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/admin/product/*")
@Controller
@RequiredArgsConstructor
public class AdProductController {
	
	private final AdProductService adProductService;
	
	// 업로드 폴더경로 주입작업
	@Resource(name="uploadPath") // servlet-context.xml 의 bean 이름 참조를 해야 한다.
	private String uploadPath;
	
	// 상품등록 폼
	@GetMapping("/pro_insert")
	public void pro_insert() {
		log.info("상품등록 폼");
	}
	
	// 1차카테고리 데이터를 Model로 작업
	/*
	@GetMapping("이름")
	public void aaa(Model model) {
		
		model.addAttribute("cg_code", "1차카테고리정보");
	}
	*/
	
	// 상품정보 저장
	// 파일업로드 기능 : 1) 스프링에서 내장된 기본라이브러리 사용,
	//				2) 외부 라이브러리 사용(commons-fileupload, pom.xml 참고)
	// 넘어오는 파일이 여러개면 MultipartFile을 List로 감싸주어야 한다.
	// MultipartFile uploadFile을 ProductVO에 추가하여 사용하여도 된다.
	
	/* MultipartFile uploadFile 파라미터명은 jsp name 속성과 일치해야한다.
	  <input type="file" class="form-control" name="uploadFile" id="uploadFile">
	 */
	@PostMapping("/pro_insert")
	public String pro_insert(ProductVO vo, MultipartFile uploadFile, RedirectAttributes rttr) {
		
		log.info("상품정보 : " + vo);
		
		// 1) 파일 업로드 작업. 선수작업 : FileUtils 클래스작업
		String dateFolder = FileUtils.getDateFolder();
		String savedFileName = FileUtils.uploadFile(uploadPath, dateFolder, uploadFile);
		
		vo.setPro_img(savedFileName);
		vo.setPro_up_folder(dateFolder);
		
		log.info("상품정보 : " + vo);
		
		// 2) 상품정보 저장
		adProductService.pro_insert(vo);
		
		
		return "redirect:/";
	}
}
