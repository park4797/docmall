package com.test.mapper;

import java.util.List;

import com.test.domain.CategoryVO;

public interface UserCategoryMapper {

	List<CategoryVO> getSecondCategoryList(Integer cg_parent_code);
}
