package com.test.controller;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.domain.MemberVO;
import com.test.dto.LoginDTO;
import com.test.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

/*
 MemberVO의 사용
 - 회원가입, 회원수정 폼, 회원수정하기 데이터를 받기 위해
 - DB에 회원테이블에서 정보를 읽어올 때
 */

@Log4j
@RequestMapping("/member/*")
@Controller
@RequiredArgsConstructor // final field만 매개변수가 있는 생성자를 만들어주고 스프링에 의해 생성자 주입을 받게 해준다. 
public class MemberController {

	// 생성자로 통하여 주입받는 필드에 인터페이스를 사용하는 이유 : 유지보수, 다형성
	
	private final MemberService memberService;
	
	private final PasswordEncoder passwordEncoder;
	
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
	
	// 회원정보 저장 -> 다른주소이동(redirect:/ 사용시 returnType은 String)
	@PostMapping("/join")
	public String join(MemberVO vo, RedirectAttributes attr) {
		
		log.info("회원정보 : " + vo);
		
		vo.setMbsp_password(passwordEncoder.encode(vo.getMbsp_password()));
		
		log.info("암호화 비밀번호 : " + vo.getMbsp_password());
		
		// db 저장
		memberService.join(vo);
		
		return "redirect:/member/login"; // redirect는 주소로 인식하게 해준다.(jsp명이 되지 않는다.)
	}
	
	// login form page
	@GetMapping("/login")
	public void login() {
		log.info("로그인폼");
	}
	
	// 1)로그인 인증성공 -> 메인페이지(/) 주소이동. 2) 로그인 인증실패 -> 로그인 폼주소로 이동
	// String mbsp_id, String mbsp_password
	@PostMapping("/login")
	public String login(LoginDTO dto, HttpSession session, RedirectAttributes rttr) {
		
		log.info("로그인 : " + dto);
		
		MemberVO db_vo = memberService.login(dto.getMbsp_id());
		
		String url = "";
		String msg = "";
		
		// 아이디가 존재하면 true, 존재하지 않으면 false로 db_vo가 null
		// 아이디가 일치하면
		if(db_vo != null) {
			// 사용자가 입력한 비밀번호(평문텍스트)와 db에서 가져온 비밀번호를 일치여부 검사
			if(passwordEncoder.matches(dto.getMbsp_password(), db_vo.getMbsp_password())) {
				
				// 로그인 성공결과로 서버측의 메모리를 사용하는 세션형태 작업
				session.setAttribute("loginStatus", db_vo);
				url = "/"; // 메인페이지 주소
				
			} else {
				url = "/member/login";
				msg = "비밀번호가 일치하지 않습니다";
				rttr.addFlashAttribute("msg", msg); // login.jsp에서 쓰고자하는 목적
			}
		} else {
			// 아이디가 일치하지 않으면
			url = "/member/login";
			msg = "아이디가 일치하지 않습니다";
			rttr.addFlashAttribute("msg", msg); // login.jsp에서 쓰고자하는 목적
		}
		
		return "redirect:" + url;
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate(); // 서버측의 session 메모리 삭제
		
		return "redirect:/";
	}
}
