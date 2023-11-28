package com.test.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.domain.OrderVO;
import com.test.dto.Criteria;
import com.test.mapper.AdOrderMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdOrderServiceImpl implements AdOrderService {

	private final AdOrderMapper adOrderMapper;

	@Override
	public List<OrderVO> order_list(Criteria cri) {
		return adOrderMapper.order_list(cri);
	}

	@Override
	public int getTotalCount(Criteria cri) {
		return adOrderMapper.getTotalCount(cri);
	}

	

}
