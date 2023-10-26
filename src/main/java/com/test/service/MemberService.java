package com.test.service;

import com.test.domain.MemberVO;

public interface MemberService {

	String idCheck(String mbsp_id);
	
	void join(MemberVO vo);
	
	MemberVO login(String mbsp_id);

}
