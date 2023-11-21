package com.test.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.domain.ProductVO;
import com.test.dto.Criteria;
import com.test.dto.PageDTO;
import com.test.service.UserProductService;
import com.test.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

// jsp상에서 console.log를 찍어보고, 전달작업 후 스프링까지 전송이 되는디 log.info로 확인 후 Mapper 작업 시작

@Log4j
@Controller
@RequestMapping("/user/product/*")
@RequiredArgsConstructor
public class UserProductController {

	private final UserProductService userProductService;
	
	// 메인, 썸네일 업로드 폴더경로 주입작업
	@Resource(name="uploadPath") // servlet-context.xml 의 bean 이름 참조를 해야 한다.
	private String uploadPath;
	
	// 매핑주소 1 : /user/product/pro_list?cg_code=2차카테고리코드
	/*
	@GetMapping("/pro_list")
	public void pro_list(Criteria cri, @RequestParam("cg_code") Integer cg_code) throws Exception {
		
	}
	*/
	
	// 매핑주소 2 : /user/product/pro_list/2차카테고리코드 (REST API 개발형태)
	// ex) "/pro_list"/${cg_code}/${cg_name}"
	@GetMapping("/pro_list")
	public String pro_list(Criteria cri, @ModelAttribute("cg_code") Integer cg_code, @ModelAttribute("cg_name") String cg_name, Model model) throws Exception {
		
		cri.setAmount(8);
		
		// 목록데이터를 모델로 추가
		List<ProductVO> pro_list = userProductService.pro_list(cg_code, cri);
		
		// 날짜폴더의 역슬래시를 슬래시로 바꾸는 작업
		// (브라우저에서 역슬래시로 되어있는 정보가 스프링으로 전달되면 과정에서 에러발생)
		// 자바스크립트에서도 처리할 수 있다.
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		model.addAttribute("pro_list", pro_list); // jsp에서 사용하기위해 Model을 추가
		
		int totalCount = userProductService.getTotalCount(cg_code);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
		
		return "/user/product/pro_list";
	}
	
	@ResponseBody // ajax로 요청을 받는다. <img src="매핑주소"> 형태로 받겠다
	@GetMapping("/imageDisplay") // "/admin/product/imageDisplay?dateFolderName=값1&fileName=값2"
	public ResponseEntity<byte[]> imageDisplay(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	// 상품상세. 하단에 상품후기 포함
	@GetMapping("/pro_detail")
	public void pro_detail(Criteria cri, Integer cg_code, @ModelAttribute("cg_name") String cg_name, Integer pro_num, Model model) throws Exception {
		
		log.info("페이징 정보 : " + cri);
		log.info("상품코드 : " + pro_num);
		
		ProductVO productVO = userProductService.pro_detail(pro_num);
		
		// 클라이언트에서 이미지 출력작업. \ 역슬래시가 서버로 보낼때 문제가 되어 / 슬래시로 변경
		productVO.setPro_up_folder(productVO.getPro_up_folder().replace("\\", "/"));
		
		model.addAttribute("productVO", productVO);
		
		// DB연동작업
	}
}