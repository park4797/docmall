package com.test.service;

import java.util.List;

import com.test.domain.OrderDetailInfoVO;
import com.test.domain.OrderDetailProductVO;
import com.test.domain.OrderVO;
import com.test.dto.Criteria;

public interface AdOrderService {

	List<OrderVO> order_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
	
	List<OrderDetailInfoVO> orderDetailInfo1(Long ord_code);
	
	List<OrderDetailProductVO> orderDetailInfo2(Long ord_code);
	
	void order_product_delete(Long ord_code, Integer pro_num);
}
