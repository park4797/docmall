package com.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.domain.CartVO;
import com.test.dto.CartDTOList;
import com.test.mapper.CartMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

	private final CartMapper cartMapper;

	@Override
	public void cart_add(CartVO vo) {
		cartMapper.cart_add(vo);
	}

	@Override
	public List<CartDTOList> cart_list(String mbsp_id) {
		return cartMapper.cart_list(mbsp_id);
	}

	@Override
	public void cart_amount_change(Long cart_code, int cart_amount) {
		cartMapper.cart_amount_change(cart_code, cart_amount);
	}

	@Override
	public void cart_list_del(Long cart_code) {
		cartMapper.cart_list_del(cart_code);
	}

	@Override
	public void cart_checked_del(List<Long> cart_code_arr) {
		cartMapper.cart_checked_del(cart_code_arr);
	}

	

	


	
}
