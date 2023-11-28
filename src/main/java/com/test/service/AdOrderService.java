package com.test.service;

import java.util.List;

import com.test.domain.OrderVO;
import com.test.dto.Criteria;

public interface AdOrderService {

	List<OrderVO> order_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
}
