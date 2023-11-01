package com.test.service;

import org.springframework.stereotype.Service;

import com.test.mapper.AdProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdProductServiceImpl implements AdProductService {

	private final AdProductMapper adProductMapper;
}
