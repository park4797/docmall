package com.test.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.domain.CategoryVO;
import com.test.service.AdCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequestMapping("/admin/category/*")
@Log4j
@RequiredArgsConstructor
@Controller // ajax 호출 또는 일반호출이 함께 사용하는 경우
// @RestController (@Controller + @ResponseBody 2개가 합쳐진 의미) : 모든 매핑주소가 ajax호출로 사용하는 경우
public class AdCategoryController {

	private final AdCategoryService adCategoryService;
	
	// 1차카테고리 선택에 따른 2차 카테고리 정보를 클라이언트에게 제공
	// 일반주소 secondCategory?cg_parent_code=1
	// Restful 개발론 주소 /admin/category/secondCategory/1.json
	// 주소의 일부분을 값으로 사용하고자 할 경우 {} 사용
	@ResponseBody // @Controller 사용시 ajax를 사용하고자 할 때 사용
	@GetMapping("/secondCategory/{cg_parent_code}")
	public ResponseEntity<List<CategoryVO>> secondCategory(@PathVariable("cg_parent_code") Integer cg_parent_code) throws Exception {
		
		log.info("1차 카테고리 코드 : " + cg_parent_code);
		
		ResponseEntity<List<CategoryVO>> entity = null;
		
		entity = new ResponseEntity<List<CategoryVO>>(adCategoryService.getSecondCategoryList(cg_parent_code), HttpStatus.OK);
		
		// List<CategoryVO> list = adCategoryService.getSecondCategoryList(cg_parent_code)
		// 반환받아서 써도 되지만 위의 getSecondCategoryList를 직접적으로 사용했다.
		// list 객체 -> JSON 로 변환하는 라이브러리 기능 사용. (jackson-databind 라이브러리)
		
		return entity;
		
		
	}
}
