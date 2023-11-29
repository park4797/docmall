package com.test.domain;

import lombok.Data;

@Data
public class OrderDetailProductVO {

	// 기존 클래스를 필드로 사용. mybatis에서는 resultMap 사용
	private OrderDetailVO orderDetailVO; // collection 으로 표현
	private ProductVO productVO; // collection 으로 표현
}
