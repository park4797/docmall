package com.test.service;

import java.util.List;

import com.test.domain.CartVO;
import com.test.dto.CartDTOList;

public interface CartService {

	void cart_add(CartVO vo);
	
	List<CartDTOList> cart_list(String mbsp_id);
	
	void cart_amount_change(Long cart_code, int cart_amount);
	
	void cart_list_del(Long cart_code);
	
	void cart_checked_del(List<Long> cart_code_arr);
}
