package com.test.service;

import com.test.domain.OrderVO;
import com.test.domain.PaymentVO;

public interface OrderService {

	int getOrderSeq();
	
	// 주문하기
	void order_insert(OrderVO o_vo, PaymentVO p_vo);
	
}
