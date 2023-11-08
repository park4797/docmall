package com.test.mapper;

import java.util.List;

import com.test.domain.CategoryVO;

public interface AdCategoryMapper {

	List<CategoryVO> getFirstCategoryList();
	
	// 선택했던 1차 카테고리 정보가 들어와야 한다.
	List<CategoryVO> getSecondCategoryList(Integer cg_parent_code);
	
	CategoryVO get(Integer cg_code);
}
