package com.test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.domain.ProductVO;
import com.test.dto.Criteria;
import com.test.dto.PageDTO;
import com.test.service.UserProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/user/product/*")
@RequiredArgsConstructor
public class UserProductController {

	private final UserProductService userProductService;
	
	// 매핑주소 1 : /user/product/pro_list?cg_code=2차카테고리코드
	/*
	@GetMapping("/pro_list")
	public void pro_list(Criteria cri, @RequestParam("cg_code") Integer cg_code) throws Exception {
		
	}
	*/
	
	// 매핑주소 2 : /user/product/pro_list/2차카테고리코드 (REST API 개발형태)
	@GetMapping("/pro_list/{cg_code}")
	public void pro_list(Criteria cri, @PathVariable("pro_list") Integer cg_code, Model model) throws Exception {
		
		cri.setAmount(2);
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
	}
}