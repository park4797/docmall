package com.test.controller;

import org.springframework.http.HttpStatus;
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
	// id 중복체크 기능
	// ResponseEntity란, httpentity를 상속받는, 결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스이다.
	// 주로 ajax 기능과 함께 사용한다.
	// https://thalals.tistory.com/268
	// ResponseEntity 구조 : HttpStatus, HttpHeaders, HttpBody
	@GetMapping("idCheck")
	public ResponseEntity<String> idCheck(String mbsp_id) {
		
		log.info("아이디 : " + mbsp_id);
		
		ResponseEntity<String> entity = null;
		
		// 서비스 method 호출 작업
		String idUse = "";
		if(memberService.idCheck(mbsp_id) != null) {
			idUse = "no";
		} else {
			idUse = "yes";
		}
		
		entity = new ResponseEntity<String>(idUse, HttpStatus.OK);
		// HttpStatus : 열거형으로 상태코드를 지원해준다. OK는 200번으로 전송
		
		return entity;
	}
}
