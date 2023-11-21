package com.test.service;

import java.util.List;

import com.test.domain.ProductVO;
import com.test.dto.Criteria;

public interface UserProductService {

	List<ProductVO> pro_list(Integer cg_code, Criteria cri);
	
	int getTotalCount(Integer cg_code);
	
	ProductVO pro_detail(Integer pro_num);
}
