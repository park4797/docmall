package com.test.mapper;

import java.util.List;

import com.test.domain.ProductVO;
import com.test.dto.Criteria;

public interface AdProductMapper {
	
	void pro_insert(ProductVO vo);
	
	List<ProductVO> pro_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
}
