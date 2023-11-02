package com.test.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {

	private Integer pro_num; // 시퀀스 사용 예정
	
	// 사용자로부터 넘어오는 코드
	private Integer cg_code; // 2차 카테고리코드
	private String 	pro_name;
	private int 	pro_price;
	private int 	pro_discount;
	private String 	pro_publisher;
	private String 	pro_content;
	private int		pro_amount;
	private String	pro_buy;
	
	private String	pro_up_folder; // 스프링에서 별도로 처리
	private String	pro_img; // 스프링에서 별도로 처리
	
	private Date	pro_date;
	private Date	pro_updatedate;
	
//	private MultipartFile uploadFile;
}