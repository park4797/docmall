package com.test.domain;

import java.util.Date;

import lombok.Data;

@Data
public class AdminVO {

	private String admin_id;
	private String admin_pw;
	private Date admin_visit_date;
}


/*
  	ADMIN_ID    VARCHAR2(15)    PRIMARY KEY,
    ADMIN_PW    CHAR(60)    NOT NULL,
    ADMIN_VISIT_DATE    DATE
 */
