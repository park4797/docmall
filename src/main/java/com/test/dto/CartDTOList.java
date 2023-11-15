package com.test.dto;

import lombok.Data;

@Data
public class CartDTOList {

	private Long cart_code;
	private Integer pro_num;
	private int cart_amount;
	
	private String	pro_up_folder;
	private String	pro_img;
	private String 	pro_name;
	private String 	pro_publisher;
	private int 	pro_discount;
	private int 	pro_price;
	
}


// c.cart_code, c.pro_num, c.cart_amount, p.pro_up_folder, p.pro_img, p.pro_publisher, p.pro_name, p.pro_price, p.pro_discount