package com.test.domain;

import java.util.Date;

import lombok.Data;

@Data
public class OrderVO {

	private Long ord_code;
	private String mbsp_id;
	private String ord_name;
	private String ord_zipcode;
	private String ord_addr_basic;
	private String ord_addr_detail;
	private String ord_tel;
	private int ord_price;
	private Date ord_regdate;
	
	private String ord_status;
	private String paryment_status;
}


/* ord_status paryment_status
 * ord_code, mbsp_id, ord_name, ord_zipcode, ord_addr_basic, ord_addr_detail, ord_tel, ord_price, ord_regdate
 * 
CREATE TABLE ORDER_TBL(
        ORD_CODE            NUMBER                  PRIMARY KEY,
        MBSP_ID             VARCHAR2(15)            NOT NULL,
        ORD_NAME            VARCHAR2(30)            NOT NULL,
        ORD_ZIPCODE         CHAR(5)                 NOT NULL,
        ORD_ADDR_BASIC      VARCHAR2(50)            NOT NULL,
        ORD_ADDR_DETAIL     VARCHAR2(50)            NOT NULL,
        ORD_TEL             VARCHAR2(20)            NOT NULL,
        ORD_PRICE           NUMBER                  NOT NULL,  -- 총주문금액. 선택
        ORD_REGDATE         DATE DEFAULT SYSDATE    NOT NULL,
        ORD_STATUS          VARCHAR2(20)            NOT NULL,
        PARYMENT_STATUS     VARCHAR2(20)            NOT NULL,
        FOREIGN KEY(MBSP_ID) REFERENCES MBSP_TBL(MBSP_ID)
);
*/
