package com.test.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.domain.MemberVO;
import com.test.domain.ReviewVO;
import com.test.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequiredArgsConstructor
@Log4j
@RequestMapping("/user/review/*")
@RestController // jsp 파일을 사용하지 않는다.
public class ReviewController {

	private final ReviewService reviewService;
	
	/*
	 consumes : 사용자가 Request Body에 담는 타입을 제한할 수 있다.
	 application/json 일 경우의 요청만을 처리한다는 의미.
	  따라서 요청시 헤더에 꼭 application/json 이 존재해야함.
	 contentType() 을 이용하여 테스트 요청에 header를 추가할 수 있다.
	 */
	
	// @RequestBody : JSON 데이터를 ReviewVO 객체로 변환시켜주는 기능.
	// 반대의미는 @ResponseBody
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> review_insert(@RequestBody ReviewVO vo, HttpSession session) throws Exception {
		
		log.info("리뷰 : " + vo);
		
		// MemberController 에서 ID를 가져온 방식과 동일
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		  
		ResponseEntity<String> entity = null;
		
		// DB 저장
		reviewService.review_insert(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// @PathVariable : path 변수를 받는 어노테이션
	// 쿼리스트링 : list?pro_num=10&page=1 -> // Rest API 주소 : list/10/1
	@GetMapping("/list/{pro_num}/{page}")
	public ResponseEntity<Map<String, Object>> list(@PathVariable("pro_num") Integer pro_num, @PathVariable("page") Integer page) throws Exception {
		
		ResponseEntity<Map<String, Object>> entity = null;
		
		return entity;
	}
}

