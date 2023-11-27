package com.test.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.test.domain.CategoryVO;
import com.test.service.AdCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

/*
 지정한 패키지(com.test.controller)에서 반복적으로 사용하는 Model 데이터작업을
 아래 클래스에서 한번만 정의를 해서 사용할 때 사용 
 **매번 요청이 발생될 때마다 클래스가 동작된다.
 */

@Log4j
@ControllerAdvice(basePackages = {"com.test.controller"})
@RequiredArgsConstructor
public class GlobalControllerAdvice {

	private final AdCategoryService adCategoryService;
	
	@ModelAttribute // Advice에서는 추가시 어노테이션도 추가해주어야 한다.
	public void getFirstCategoryList(Model model) {
		
		log.info("1차카테고리 리스트");
		
		List<CategoryVO> firstCategoryList = adCategoryService.getFirstCategoryList();
		model.addAttribute("firstCategoryList", firstCategoryList);
	}
}
