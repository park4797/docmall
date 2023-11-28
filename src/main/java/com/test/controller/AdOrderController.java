package com.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.domain.OrderDetailVO;
import com.test.domain.OrderVO;
import com.test.domain.ReviewVO;
import com.test.dto.Criteria;
import com.test.dto.PageDTO;
import com.test.service.AdOrderService;
import com.test.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/admin/order/*")
@RequiredArgsConstructor
public class AdOrderController {

	private final AdOrderService adOrderService;
	
	// 메인, 썸네일 업로드 폴더경로 주입작업
	@Resource(name="uploadPath") // servlet-context.xml 의 bean 이름 참조를 해야 한다.
	private String uploadPath;
	
	@GetMapping("/order_list")
	public void order_list(Criteria cri, Model model) throws Exception {
		
		cri.setAmount(2);
		// 목록데이터를 모델로 추가
		List<OrderVO> order_list = adOrderService.order_list(cri);
		
		model.addAttribute("order_list", order_list); // jsp에서 사용하기위해 Model을 추가
		
		int totalCount = adOrderService.getTotalCount(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	@GetMapping("/order_detail_info/{ord_code}")
	public ResponseEntity<List<OrderDetailVO>> order_detail_info(@PathVariable("ord_code") Long ord_code) throws Exception {
		
		// 클래스명 = 주문상세 + 상품테이블을 조인한 결과를 담는 클래스
		ResponseEntity<List<OrderDetailVO>> entity = null;
		
		return entity;
	}
	
	
	// 상품리스트에서 보여줄 이미지
	@ResponseBody // ajax로 요청을 받는다. <img src="매핑주소"> 형태로 받겠다
	@GetMapping("/imageDisplay") // "/admin/order/imageDisplay?dateFolderName=값1&fileName=값2"
	public ResponseEntity<byte[]> imageDisplay(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + dateFolderName, fileName);
	}
}
