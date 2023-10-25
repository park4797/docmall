package com.test.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.EmailDTO;
import com.test.service.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RestController // 컨트롤러 클래스가 ajax 용도로만 사용할 때 annotation 적용
@RequestMapping("/email/*") // 현재는 jsp는 사용하지 않을 예정
@RequiredArgsConstructor
public class EmailController {
	
	private final EmailService emailService;
	
	// 메일인증코드 요청주소
	@GetMapping("/authcode")
	public ResponseEntity<String> authSend(EmailDTO dto, HttpSession session) {
		
		log.info("전자우편정보 : " + dto);
		
		ResponseEntity<String> entity = null;
		
		// 인증코드 6자리 랜덤생성 로직
		String authCode = "";
		for(int i=0; i<6; i++) {
			authCode = String.valueOf((int)(Math.random() * 10));
		}
		
		log.info("인증코드 : " + authCode);
		
		// 사용자에게 메일로 발급해준 인증코드를 입력시 비교목적으로 세션형태로 미리 저장
		session.setAttribute("authCode", authCode);
		
		try {
			emailService.sendMail(dto, authCode); // 인증코드 메일 보내기
			entity = new ResponseEntity<String>("success", HttpStatus.OK); // http 상태코드 200
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR); // http 상태코드 500
		} 
			
		
		return entity;
	}
}
