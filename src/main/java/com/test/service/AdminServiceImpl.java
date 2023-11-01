package com.test.service;

import org.springframework.stereotype.Service;

import com.test.domain.AdminVO;
import com.test.mapper.AdminMapper;

import lombok.RequiredArgsConstructor;

@Service // bean 생성 adminServiceImpl
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminMapper adminMapper;

	@Override
	public AdminVO admin_ok(String admin_id) {
		return adminMapper.admin_ok(admin_id);
	}

	@Override
	public void loginTime(String admin_id) {
		adminMapper.loginTime(admin_id);
	}
	
}
