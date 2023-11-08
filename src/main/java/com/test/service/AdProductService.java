package com.test.service;

import java.util.List;

import com.test.domain.CategoryVO;
import com.test.domain.ProductVO;
import com.test.dto.Criteria;

public interface AdProductService {

	void pro_insert(ProductVO vo);
	
	List<ProductVO> pro_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
	
	void pro_checked_modify1(
			List<Integer> pro_num_arr,
			List<Integer> pro_price_arr,
			List<String> pro_buy_arr
		);
	
	void pro_checked_modify2(
			List<Integer> pro_num_arr,
			List<Integer> pro_price_arr,
			List<String> pro_buy_arr
		);
	
	ProductVO pro_edit(Integer pro_num);
	
	void pro_edit_ok(ProductVO vo);
	
	void pro_delete(Integer pro_num);
}
