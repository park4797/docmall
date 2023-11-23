package com.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.domain.ReviewVO;
import com.test.dto.Criteria;
import com.test.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	private final ReviewMapper reviewMapper;

	@Override
	public void review_insert(ReviewVO vo) {
		reviewMapper.review_insert(vo);
	}

	@Override
	public List<ReviewVO> list(Integer pro_num, Criteria cri) {
		return reviewMapper.list(pro_num, cri);
	}

	@Override
	public int listCount(Integer pro_num) {
		return reviewMapper.listCount(pro_num);
	}
}
