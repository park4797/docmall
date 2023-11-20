package com.test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.domain.MemberVO;
import com.test.service.AdMemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/admin/member/*")
@RequiredArgsConstructor
public class AdMemberController {

	private final AdMemberService adMemberService;
	
	@GetMapping("/member_list")
	public void member_list(Model model) {
		
		List<MemberVO> member_list = adMemberService.member_list();
		model.addAttribute("member_list", member_list);
	}
	
	@GetMapping("/member_modify")
	public void member_modify() {
		
	}
	
}
