package com.test.kakaopay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//1) 결제준비 요청정보 구성 후 보낸뒤 정보를 받아온다. https://kapi.kakao.com/v1/payment/ready

@Getter
@ToString
@Setter
public class ReadyResponse {

	private String tid; // 결제 고유 번호, 20자
	private String next_redirect_pc_url;
	/*
	요청한 클라이언트가 PC 웹일 경우
	카카오톡으로 결제 요청 메시지(TMS)를 보내기 위한 사용자 정보 입력 화면 Redirect URL
	 */
	
	// 추가
	private String partner_order_id; // 가맹점(쇼핑몰)의 주문번호
}
