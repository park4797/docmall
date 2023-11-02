package com.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.domain.CategoryVO;
import com.test.mapper.AdCategoryMapper;

import lombok.extern.log4j.Log4j;

@Log4j
@Service
public class AdCategoryServiceImpl implements AdCategoryService {

	private final AdCategoryMapper adCategoryMapper;
	
	public AdCategoryServiceImpl(AdCategoryMapper adCategoryMapper) {
		this.adCategoryMapper = adCategoryMapper;
	}

	@Override
	public List<CategoryVO> getFirstCategoryList() {
		return adCategoryMapper.getFirstCategoryList();
	}

	@Override
	public List<CategoryVO> getSecondCategoryList(Integer cg_parent_code) {
		return adCategoryMapper.getSecondCategoryList(cg_parent_code);
	}
}
