package com.test.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.domain.CartVO;
import com.test.domain.MemberVO;
import com.test.domain.OrderVO;
import com.test.domain.PaymentVO;
import com.test.dto.CartDTOList;
import com.test.kakaopay.ApproveResponse;
import com.test.kakaopay.ReadyResponse;
import com.test.service.CartService;
import com.test.service.KakaoPayServiceImpl;
import com.test.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/user/order/*")
@RequiredArgsConstructor
public class OrderController {

	private final CartService cartService;
	
	private final OrderService orderService;

	private final KakaoPayServiceImpl kakaoPayServiceImpl;
	
	// 주문정보페이지
	@GetMapping("/order_info")
	public void order_info(HttpSession session, Model model) throws Exception {
	
		// 로그인 정보를 가져온다(주문정보(장바구니) 목록을 가져온다)
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
				
		// 목록데이터를 모델로 추가
		List<CartDTOList> order_info = cartService.cart_list(mbsp_id);
				
		int order_price = 0;
				
//		cart_list.forEach(vo -> {
//			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
//					
//			// 금액 = 판매가 - (판매가 * 할인율) * 수량
//			cart_total_price += ((double)vo.getPro_price() - (vo.getPro_price() * (vo.getPro_discount() * 1/100)) * (double)vo.getCart_amount());	
//		});
		
		String itemName = "";
				
		for(int i=0; i<order_info.size(); i++) {
					CartDTOList vo = order_info.get(i);
			
			if(i==0) {
				itemName = vo.getPro_name();
			}
					
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
					
//					vo.setPro_discount(vo.getPro_discount() * 1/100);
			order_price += (vo.getPro_price() * vo.getCart_amount());
		}
				
				// 위에서 호출되어 다시 불러와서는 안된다.
//				model.addAttribute("cart_list", cartService.cart_list(mbsp_id));
				
		model.addAttribute("order_info", order_info);
		model.addAttribute("order_price", order_price);
		model.addAttribute("proVO", itemName + " 외 " + String.valueOf(order_info.size() - 1) + "건");
	}
	
	// 상품상세에서 주문하기(CartController의 cart_add 작업과 유사)
	@GetMapping("/order_ready")
	public String order_ready(CartVO vo, HttpSession session) throws Exception {
		
		log.info("상세주문하기 : " + vo);
		
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		cartService.cart_add(vo);
		
		return "redirect:/user/order/order_info"; // 주문정보 페이지
	}
	
	// 결제선택 : 카카오페이
	// 1) 결제준비요청
	@GetMapping(value = "/orderPay", produces = "application/json")
	public @ResponseBody ReadyResponse payReady(
			String paymethod, OrderVO o_vo, PaymentVO p_vo, int totalprice, HttpSession session
			) throws Exception {
		/*
		1) 주문정보구성
		- 주문테이블(OrderVO) : ord_status, payment_status 정보 존재 안함
		- 주문상세테이블(OrderDetailVO) : 
			- 장바구니 테이블에서 데이터를 참조
		- 결제테이블 : 보류
		주문테이블, 주문상세테이블, 결제테이블에 저장에 필요한 정보구성(OrderVO o_vo, OrderDetailVO od_vo, PaymentVO p_vo)
     	2) 카카오페이결제에 필요한 정보구성
		스프링에서 처리할 수 있는 부분
		 */

		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		o_vo.setMbsp_id(mbsp_id); // 아이디 저장
		
		// 시퀀스를 사용하여 주문테이블과 상세테이블의 주문번호를 동일한 주문번호값이 사용되게 처리
		Long ord_code = (long) orderService.getOrderSeq();
		o_vo.setOrd_code(ord_code); // 주문번호 저장
		
		o_vo.setOrd_status("주문");
		o_vo.setPayment_status("주문");
		
		p_vo.setOrd_code(ord_code);
		p_vo.setMbsp_id(mbsp_id);
		p_vo.setPay_method("카카오페이");
		p_vo.setPay_tot_price(totalprice);
		
		log.info("결제 방법 : " + paymethod);
		log.info("주문정보 : " + o_vo);
		log.info("결제 정보 : " + p_vo);
		
		// 1) 주문테이블 정보 저장.
		// 2) 주문상세테이블 정보 저장
		// 3) 장바구니 삭제
		List<CartDTOList> cart_list = cartService.cart_list(mbsp_id);
		
		String itemName = cart_list.get(0).getPro_name() + " 외 " + String.valueOf(cart_list.size() - 1) + "건";
		
		orderService.order_insert(o_vo, p_vo);
		
		// Kakaopay 호출작업(결제준비요청)
		ReadyResponse readyResponse =  kakaoPayServiceImpl.payReady(o_vo.getOrd_code(), itemName, cart_list.size(), mbsp_id, totalprice);
		
		log.info("결제고유번호 : " + readyResponse.getTid());
		log.info("결제요청URL : " + readyResponse.getNext_redirect_pc_url());
		
		// 카카오페이 결제승인요청작업에 필요한 정보준비 
		session.setAttribute("tid", readyResponse.getTid());
		session.setAttribute("ord_code", o_vo.getOrd_code());
		
		return readyResponse;
	}
	
	// 결제승인 요청 작업
	// http://localhost:9090/user/order/orderApproval
	@GetMapping("/orderApproval")
	public String orderApproval(@RequestParam("pg_token") String pg_token, HttpSession session) {
		
		// 2) Kakao pay 결제승인 요청작업
		Long ord_code = (Long) session.getAttribute("ord_code");
		String tid = (String) session.getAttribute("tid");
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		
		
		ApproveResponse approveResponse = kakaoPayServiceImpl.payApprove(ord_code, tid, pg_token, mbsp_id);
		
		session.removeAttribute("ord_code");
		session.removeAttribute("tid");
		
		return "redirect:/user/order/orderComplete";
	}
	
	// 결제 완료페이지. 
	@GetMapping("/orderComplete")
	public void orderComplete() {
		
	}
	
	// 결제 취소시 http://localhost:9090/user/order/orderCancel
	@GetMapping("/orderCancel")
	public void orderCancel() {
		
	}
	
	// 결제 실패시 http://localhost:9090/user/order/orderFail
	@GetMapping("/orderFail")
	public void orderFail() {
		
	}
	
	// 결제선택 : 무통장입금
	@GetMapping("/nobank")
	public ResponseEntity<String> nobank(String paymethod, OrderVO o_vo, PaymentVO p_vo, int totalprice, HttpSession session) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		o_vo.setMbsp_id(mbsp_id); // 아이디 저장
		
		// 시퀀스를 사용하여 주문테이블과 상세테이블의 주문번호를 동일한 주문번호값이 사용되게 처리
		Long ord_code = (long) orderService.getOrderSeq();
		o_vo.setOrd_code(ord_code); // 주문번호 저장
		
		o_vo.setOrd_status("주문완료");
		o_vo.setPayment_status("주문");
		
		p_vo.setPay_method("무통장입금");
		p_vo.setOrd_code(ord_code);
		p_vo.setMbsp_id(mbsp_id);
		p_vo.setPay_tot_price(totalprice);
		p_vo.setPay_nobank_price(totalprice);
		
		log.info("결제방법 : " + paymethod);
		log.info("주문정보 : " + o_vo);
		log.info("결제정보 : " + p_vo);
		
		orderService.order_insert(o_vo, p_vo); // 주문, 주문상세 정보 저장, 장바구니 삭제, 결제정보 저장
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
}
