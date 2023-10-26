package com.test.service;

import org.springframework.stereotype.Service;

import com.test.domain.MemberVO;
import com.test.mapper.MemberMapper;

@Service
//@RequiredArgsConstructor // final 일시만 사용 가능
public class MemberServiceImpl implements MemberService{

	// RequiredArgsConstructor : memberMapper를 필드로 만들어준다.
	private final MemberMapper memberMapper;
	
	private MemberServiceImpl(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}

	@Override
	public String idCheck(String mbsp_id) {
		return memberMapper.idCheck(mbsp_id);
	}

	@Override
	public void join(MemberVO vo) {
		memberMapper.join(vo);
	}

	@Override
	public MemberVO login(String mbsp_id) {
		return memberMapper.login(mbsp_id);
	}
}
