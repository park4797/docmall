package com.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.domain.CategoryVO;
import com.test.mapper.UserCategoryMapper;

import lombok.RequiredArgsConstructor;

// <context:component-scan base-package="com.test.service" />	
@Service // root-context의 service package 동작, bean이 동작 userCategoryServiceImpl bean 생성
@RequiredArgsConstructor
public class UserCategoryServiceImpl implements UserCategoryService {

	private final UserCategoryMapper userCategoryMapper; // 주입을 정상적으로 받는다는 전제조건

	@Override
	public List<CategoryVO> getSecondCategoryList(Integer cg_parent_code) {
		return userCategoryMapper.getSecondCategoryList(cg_parent_code);
	}
}
