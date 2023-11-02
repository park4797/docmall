package com.test.service;

import java.util.List;

import com.test.domain.CategoryVO;

public interface AdCategoryService {

	List<CategoryVO> getFirstCategoryList();
	
	List<CategoryVO> getSecondCategoryList(Integer cg_parent_code);
}
