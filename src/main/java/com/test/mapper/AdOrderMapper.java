package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.OrderDetailInfoVO;
import com.test.domain.OrderDetailProductVO;
import com.test.domain.OrderVO;
import com.test.dto.Criteria;

public interface AdOrderMapper {

	// 주문목록과 getTotalCount는 모든 조건(parameter)을 같이 가지고 있어야 한다.
	List<OrderVO> order_list(@Param("cri") Criteria cri, @Param("start_date") String start_date, @Param("end_date") String end_date);
	
	int getTotalCount(@Param("cri") Criteria cri, @Param("start_date") String start_date, @Param("end_date") String end_date);
	
	// 주문상세 1
	List<OrderDetailInfoVO> orderDetailInfo1(Long ord_code);
	
	// 주문상세 2 ( 기존 클래스 OrderDetailVO, ProductVO 필드가 있는 클래스 ), mybatis의 rsultMap 사용
	List<OrderDetailProductVO> orderDetailInfo2(Long ord_code);
	
	// 개별삭제
	void order_product_delete(@Param("ord_code") Long ord_code, @Param("pro_num") Integer pro_num);
}
