package com.test.service;

import java.util.List;

import com.test.domain.ProductVO;
import com.test.dto.Criteria;

public interface AdProductService {

	void pro_insert(ProductVO vo);
	
	List<ProductVO> pro_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
}
