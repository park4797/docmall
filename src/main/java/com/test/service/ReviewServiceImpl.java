package com.test.service;

import org.springframework.stereotype.Service;

import com.test.domain.ReviewVO;
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
}
