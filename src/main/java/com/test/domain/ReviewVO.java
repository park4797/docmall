package com.test.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class ReviewVO {

	private Long rew_num; // primary key
	private String mbsp_id;
	private Integer pro_num;
	
	// 사용자로부터 입력받을 내용
	private String rew_content;
	private int rew_score;
	
	private Date rew_regdate;
}


/*
 create table review_tbl(
        rew_num         number,
        mbsp_id         varchar2(15)                not null,
        pro_num         number                      not null,
        rew_content     varchar2(200)               not null,
        rew_score       number                      not null,
        rew_regdate     date default sysdate        not null,
        foreign key(mbsp_id) references mbsp_tbl(mbsp_id),
        foreign key(pro_num) references product_tbl(pro_num)
);

	ALTER TABLE REVIEW_TBL
	ADD CONSTRAINT PK_REVIEW_TBL PRIMARY KEY(REW_NUM);
 */
