package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.OrderDetailInfoVO;
import com.test.domain.OrderDetailProductVO;
import com.test.domain.OrderVO;
import com.test.dto.Criteria;

public interface AdOrderMapper {

	List<OrderVO> order_list(Criteria cri);
	
	int getTotalCount(Criteria cri);
	
	// 주문상세 1
	List<OrderDetailInfoVO> orderDetailInfo1(Long ord_code);
	
	// 주문상세 2 ( 기존 클래스 OrderDetailVO, ProductVO 필드가 있는 클래스 ), mybatis의 rsultMap 사용
	List<OrderDetailProductVO> orderDetailInfo2(Long ord_code);
	
	// 개별삭제
	void order_product_delete(@Param("ord_code") Long ord_code, @Param("pro_num") Integer pro_num);
}
