package com.test.domain;

import lombok.Data;

@Data
public class CateVO {

	private Integer cate_code;
	private Integer cate_parent_code;
	private String cate_name;
}
