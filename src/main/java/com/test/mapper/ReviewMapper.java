package com.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.test.domain.ReviewVO;
import com.test.dto.Criteria;

public interface ReviewMapper {

	void review_insert(ReviewVO vo);
	
	List<ReviewVO> list(@Param("pro_num") Integer pro_num, @Param("cri") Criteria cri); // 검색필드는 사용하지 않는다
	
	int listCount(Integer pro_num);
	
	// 리뷰 삭제
	void delete(Long rew_num);
	
	// 리뷰 수정
	void modify(ReviewVO vo);
}
