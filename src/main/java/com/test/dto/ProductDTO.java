package com.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor // 모든 필드를 대상으로 매개변수가 있는 생성자 method를 생성해준다.
@Data
public class ProductDTO {

	private Integer pro_num;
	
	private Integer pro_price;
	private String pro_buy;
	
	/**
	 * @param pro_num
	 * @param pro_price
	 * @param pro_buy
	 */
	/*
	public ProductDTO(Integer pro_num, Integer pro_price, String pro_buy) {
		super();
		this.pro_num = pro_num;
		this.pro_price = pro_price;
		this.pro_buy = pro_buy;
	}
	*/
	
	
}
