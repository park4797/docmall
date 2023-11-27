package com.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.domain.OrderVO;
import com.test.domain.PaymentVO;
import com.test.mapper.OrderMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderMapper orderMapper;

	@Override
	public int getOrderSeq() {
		return orderMapper.getOrderSeq();
	}

	@Transactional // 트랜잭션 적용 : 하나라도 실패하면 롤백, root-context.xml에서 트랜잭션 설정이 되어 있어야 기능이 동작
	@Override
	public void order_insert(OrderVO o_vo, PaymentVO p_vo) {
		
		orderMapper.order_insert(o_vo);
		orderMapper.order_detail_insert(o_vo.getOrd_code(), o_vo.getMbsp_id());
		orderMapper.cart_del(o_vo.getMbsp_id());
		orderMapper.payment_insert(p_vo);
	}

}
