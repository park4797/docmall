package com.test.domain;

import lombok.Data;

@Data
public class CartVO {

	// PK
	private Long cart_code;
	
	// FK
	private Integer pro_num;
	private String mbsp_id;
	
	private int cart_amount;
}


/*
 CREATE TABLE CART_TBL(
        CART_CODE        NUMBER,
        PRO_NUM         NUMBER          NOT NULL,
        MBSP_ID         VARCHAR2(15)    NOT NULL,
        CART_AMOUNT      NUMBER          NOT NULL,
        FOREIGN KEY(PRO_NUM) REFERENCES PRODUCT_TBL(PRO_NUM),
        FOREIGN KEY(MBSP_ID) REFERENCES MBSP_TBL(MBSP_ID),
        CONSTRAINT PK_CART_CODE PRIMARY KEY(CART_CODE) 
);
 */
