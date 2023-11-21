package com.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.domain.ProductVO;
import com.test.dto.Criteria;
import com.test.mapper.UserProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {

	private final UserProductMapper userProductMapper;

	@Override
	public List<ProductVO> pro_list(Integer cg_code, Criteria cri) {
		return userProductMapper.pro_list(cg_code, cri);
	}

	@Override
	public int getTotalCount(Integer cg_code) {
		return userProductMapper.getTotalCount(cg_code);
	}

	@Override
	public ProductVO pro_detail(Integer pro_num) {
		return userProductMapper.pro_detail(pro_num);
	}
}
