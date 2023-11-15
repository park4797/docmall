package com.test.service;

import org.springframework.stereotype.Service;

import com.test.mapper.OrderMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderMapper orderMapper;
}
