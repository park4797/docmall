package com.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/test/*")
@Controller
public class TestController {

	@GetMapping("/testA")
	public void testA() {
		log.info("called testA..");
	}
	
	@GetMapping("/testB")
	public void testB() {
		log.info("called testB..");
	}
}
