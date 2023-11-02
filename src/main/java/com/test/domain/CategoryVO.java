package com.test.domain;

public class CategoryVO {
	
	private Integer cg_code; // 1, 2차 모든 카테고리 코드가 들어간다.
	private Integer cg_parent_code; // 1차 카테고리 코드가 들어간다.(부모 코드)
	private String cg_name;
	
	@Override
	public String toString() {
		return "CategoryVO [cg_code=" + cg_code + ", cg_parent_code=" + cg_parent_code + ", cg_name=" + cg_name + "]";
	}
	
	public Integer getCg_code() {
		return cg_code;
	}
	
	public void setCg_code(Integer cg_code) {
		this.cg_code = cg_code;
	}
	
	public Integer getCg_parent_code() {
		return cg_parent_code;
	}
	
	public void setCg_parent_code(Integer cg_parent_code) {
		this.cg_parent_code = cg_parent_code;
	}
	
	public String getCg_name() {
		return cg_name;
	}
	
	public void setCg_name(String cg_name) {
		this.cg_name = cg_name;
	}
	

}
