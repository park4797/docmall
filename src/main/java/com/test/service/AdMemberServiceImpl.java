package com.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.domain.MemberVO;
import com.test.mapper.AdMemberMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdMemberServiceImpl implements AdMemberService {

	private final AdMemberMapper adMemberMapper;

	@Override
	public List<MemberVO> member_list() {
		return adMemberMapper.member_list();
	}

	@Override
	public void modify(MemberVO vo) {
		adMemberMapper.modify(vo);
	}

	

}
