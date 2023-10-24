package com.test.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/member/*")
@Controller
@RequiredArgsConstructor

public class MemberController {

	private final MemberService memberService;
	
	/*
	 @Autowired // 생성자가 하나일시 생략 가능
	 public MemberController(MemberService memberService) {
	        this.memberService = memberService; 
	 }
	*/
	
	@GetMapping("/join")
	public void join() {
		
		log.info("join() called..");
	}
	
	// 비동기방식. ajax문법으로 호출
	@GetMapping("idCheck")
	public ResponseEntity<String> idCheck(String mbsp_id) {
		
		log.info("아이디 : " + mbsp_id);
		
		ResponseEntity<String> entity = null;
		
		// 서비스 method 호출 작업
		
		memberService.idCheck(mbsp_id);
		
		return entity;
	}
}
