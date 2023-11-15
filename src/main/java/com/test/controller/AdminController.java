package com.test.controller;

import javax.servlet.http.HttpSession;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.domain.AdminVO;
import com.test.service.AdminService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller // bean으로 생성 및 등록 : adminController
@RequestMapping("/admin/*")
@RequiredArgsConstructor
public class AdminController {

	private final AdminService adminService;
	
	private final PasswordEncoder passwordEncoder;
	
	// 관리자 로그인폼 페이지
	@GetMapping("/intro") // /admin/intro
	public String adminLogin() {
		log.info("관리자 로그인 페이지");
		
		return "/admin/adLogin";
	}
	
	// 관리자 로그인 인증
	@PostMapping("/admin_ok")
	public String admin_ok(AdminVO vo, HttpSession session, RedirectAttributes rttr) throws Exception {
		
		log.info("관리자로그인 : " + vo);
		
		AdminVO db_vo = adminService.admin_ok(vo.getAdmin_id());
		
		String url = "";
		String msg = "";
		
		// 아이디가 존재하면 true, 존재하지 않으면 false로 db_vo가 null
		// 아이디가 일치하면
		if(db_vo != null) {
			// 사용자가 입력한 비밀번호(평문텍스트)와 db에서 가져온 비밀번호를 일치여부 검사
			if(passwordEncoder.matches(vo.getAdmin_pw(), db_vo.getAdmin_pw())) {
				
				// 로그인 성공결과로 서버측의 메모리를 사용하는 세션형태 작업
				session.setAttribute("adminStatus", db_vo);

				// 로그인 시간 업데이트 작업
				log.info("로그인 시간" + db_vo);
				adminService.loginTime(vo.getAdmin_id());
				
				url = "/admin/admin_menu"; // 관리자 메뉴 주소
				
			} else {
				url = "/admin/intro";
				msg = "failPW";
				rttr.addFlashAttribute("msg", msg); // login.jsp에서 쓰고자하는 목적
			}
		} else {
			// 아이디가 일치하지 않으면
			url = "/admin/intro";
			msg = "failID";
			rttr.addFlashAttribute("msg", msg); // login.jsp에서 쓰고자하는 목적
		}
		
		return "redirect:" + url;
	}
	
	// 관리자 메뉴 페이지
	@GetMapping("/admin_menu")
	public void admin_menu() {
		
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		
		return "redirect:/admin/intro"; // 로그인 페이지 주소
	}
}
