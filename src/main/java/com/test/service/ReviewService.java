package com.test.service;

import java.util.List;

import com.test.domain.ReviewVO;
import com.test.dto.Criteria;

public interface ReviewService {

	void review_insert(ReviewVO vo);
	
	List<ReviewVO> list(Integer pro_num, Criteria cri);
	
	int listCount(Integer pro_num);
	
	void delete(Long rew_num);
	
	void modify(ReviewVO vo);
}
