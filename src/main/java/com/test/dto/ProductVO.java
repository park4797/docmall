package com.test.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProductVO {

	private Integer pro_num; // 시퀀스 사용 예정
	private Integer cg_code; // 2차 카테고리코드
	private String 	pro_name; //
	private int 	pro_price; //
	private int 	pro_discount; //
	private String 	pro_publisher; //
	private String 	pro_content; //
	private String	pro_up_folder;
	private String	pro_img; //
	private int		pro_amount; //
	private String	pro_buy;
	private Date	pro_date;
	private Date	pro_updatedate;
}