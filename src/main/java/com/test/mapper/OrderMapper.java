package com.test.mapper;

import org.apache.ibatis.annotations.Param;

import com.test.domain.OrderVO;
import com.test.domain.PaymentVO;

public interface OrderMapper {

	int getOrderSeq(); // 주문번호에 사용할 시퀀스
	
	// 주문하기 타이틀 안에서 한꺼번에 이루어져야할 작업(Service에서도 한개의 method로 묶어야 한다.)
	// 1) 주문테이블 저장
	void order_insert(OrderVO o_vo); // 주문테이블 저장시킬 정보
	
	// 2) 주문상세테이블 저장(장바구니테이블 참조)
	// void order_insert(OrderVO o_vo); 사용 안함
	void order_detail_insert(@Param("ord_code") Long ord_code, @Param("mbsp_id") String mbsp_id);
	
	// 결제테이블
	
	// 3) 장바구니 테이블 삭제
	void cart_del(String mbsp_id);
	
	// 4) 결제테이블 저장
	void payment_insert(PaymentVO vo);
	
}
