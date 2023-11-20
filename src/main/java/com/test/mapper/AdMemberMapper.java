package com.test.mapper;

import java.util.List;

import com.test.domain.MemberVO;

public interface AdMemberMapper {
	
	List<MemberVO> member_list();
	
	void modify(MemberVO vo);
}
