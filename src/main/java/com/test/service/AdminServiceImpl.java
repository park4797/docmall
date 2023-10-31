package com.test.service;

import org.springframework.stereotype.Service;

import com.test.mapper.AdminMapper;

import lombok.RequiredArgsConstructor;

@Service // bean 생성 adminServiceImpl
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

	private final AdminMapper adminMapper;
	
}
