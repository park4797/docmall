package com.test.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.domain.CategoryVO;
import com.test.service.UserCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

// bean : userCategoryController 생성 및 등록(servlet-context)자동생성된다.
@Log4j
@RequestMapping("/category/*")
@RestController // ajax 사용만을 위한 컨트롤러 일 때, jsp파일에서 사용하기 위한 데이터 작업탭
// RestController는 jsp파일을 필요로 하지 않는다(ajax 사용에 jsp는 필요가 없기 때문에)

@RequiredArgsConstructor
public class UserCategoryController {

	private final UserCategoryService userCategoryService;
	
	@GetMapping("/secondCategory/{cg_parent_code}")
	public ResponseEntity<List<CategoryVO>> secondCategory(@PathVariable("cg_parent_code") Integer cg_parent_code) throws Exception {
		
		log.info("1차 카테고리 코드 : " + cg_parent_code);
		
		ResponseEntity<List<CategoryVO>> entity = null;
		
		entity = new ResponseEntity<List<CategoryVO>>(userCategoryService.getSecondCategoryList(cg_parent_code), HttpStatus.OK);
		
		// List<CategoryVO> list = adCategoryService.getSecondCategoryList(cg_parent_code)
		// 반환받아서 써도 되지만 위의 getSecondCategoryList를 직접적으로 사용했다.
		// list 객체 -> JSON 로 변환하는 라이브러리 기능 사용. (jackson-databind 라이브러리)
		
		return entity;
		
		
	}
}
