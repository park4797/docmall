package com.test.mapper;

import com.test.domain.MemberVO;

public interface MemberMapper {

	// interface는 제한자를 public으로 자동으로 컴파일과정에서 생성해준다.
	String idCheck(String mbsp_id);
	
	void join(MemberVO vo);
	
	MemberVO login(String mbsp_id);
	
	void modify(MemberVO vo);
	
	void loginTimeUpdate(String mbsp_id);
	
	// 삭제
	void delete(String mbsp_id);
}
