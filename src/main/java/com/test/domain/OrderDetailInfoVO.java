package com.test.domain;

import lombok.Data;

// 관리자기능
// 주문상세 정보 저장목적(주문상세테이블과 상품테이블 조인 결과)

@Data
public class OrderDetailInfoVO {

	// OT.ORD_CODE, OT.PRO_NUM, OT.DT_AMOUNT, P.PRO_NUM, P.PRO_NAME, P.PRO_PRICE, P.PRO_CONTENT, P.PRO_UP_FOLDER, P.PRO_IMG
	private Long ord_code;
	private Integer pro_num;
	private String 	pro_name;
	private int 	pro_price;
	private int dt_amount;
	private int ord_price; // 주문금액 (pro_price*dt_amount)
	private String	pro_up_folder; 
	private String	pro_img; 
	
}
