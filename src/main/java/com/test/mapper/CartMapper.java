package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.CartVO;
import com.test.dto.CartDTOList;

// DAO 의미와 동일
public interface CartMapper {

	void cart_add(CartVO vo);
	
	/*
	조인으로 인해 항목이 많아져 CartVO로 받을 수 없게 되었다.
	List<CartVO> cart_list(String mbsp_id);
	 따라서 별도로 클래스를 만들어 사용하였다.
	 */ 
	List<CartDTOList> cart_list(String mbsp_id);
	
	void cart_amount_change( @Param("cart_code") Long cart_code, @Param("cart_amount") int cart_amount);
}
