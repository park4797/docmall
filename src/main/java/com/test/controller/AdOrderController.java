package com.test.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.domain.OrderDetailInfoVO;
import com.test.domain.OrderDetailProductVO;
import com.test.domain.OrderVO;
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
	
	// 주문상세 방법1. 주문상세정보가 클라이언트 json 형태로 변환되어 보내진다. (pom.xml에 jackson-databind 라이브러리 백그라운드로 작동)
	@GetMapping("/order_detail_info1/{ord_code}")
	public ResponseEntity<List<OrderDetailInfoVO>> order_detail_info1(@PathVariable("ord_code") Long ord_code) throws Exception {
		
		// 클래스명 = 주문상세 + 상품테이블을 조인한 결과를 담는 클래스
		ResponseEntity<List<OrderDetailInfoVO>> entity = null;
		
		List<OrderDetailInfoVO> OrderDetailList = adOrderService.orderDetailInfo1(ord_code);
		
		// 브라우저에서 상품이미지 출력시 역슬래시 사용이 문제가 된다.
		OrderDetailList.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		
		// 두번 호출되면 안된다 기억해라 무조건 기억해라 두번쨰다 정신차려라
		entity = new ResponseEntity<List<OrderDetailInfoVO>>(OrderDetailList, HttpStatus.OK);
		
		return entity;
	}
	
	// 위의 order_list와 유사?
	// 주문상세 내역 개별삭제(삭제 후 리스트로 돌아가기 위해 Criteria의 정보가 필요하다)
	@GetMapping("/order_product_delete")
	public String order_product_delete(Criteria cri, Long ord_code, Integer pro_num) throws Exception {
		
		adOrderService.order_product_delete(ord_code, pro_num);
		
		return "redirect:/admin/order/order_list" + cri.getListLink();
	}
	
	// 주문상세방법 2
	@GetMapping("/order_detail_info2/{ord_code}")
	public String order_detail_info2(@PathVariable("ord_code") Long ord_code, Model model) throws Exception {
		
		List<OrderDetailProductVO> orderProductList = adOrderService.orderDetailInfo2(ord_code);
		
		orderProductList.forEach(vo -> {
			vo.getProductVO().setPro_up_folder(vo.getProductVO().getPro_up_folder().replace("\\", "/"));
		});
		
		model.addAttribute("orderProductList", orderProductList);
		
		return "/admin/order/order_detail_product";
	}
	
	
	// 상품리스트에서 보여줄 이미지
	@ResponseBody // ajax로 요청을 받는다. <img src="매핑주소"> 형태로 받겠다
	@GetMapping("/imageDisplay") // "/admin/order/imageDisplay?dateFolderName=값1&fileName=값2"
	public ResponseEntity<byte[]> imageDisplay(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
}
