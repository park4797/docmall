package com.test.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.domain.CartVO;
import com.test.domain.MemberVO;
import com.test.dto.CartDTOList;
import com.test.service.CartService;
import com.test.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user/cart/*")
public class CartController {

	private final CartService cartService;
	
	@Resource(name="uploadPath") // servlet-context.xml 의 bean 이름 참조를 해야 한다.
	private String uploadPath;
	
	@ResponseBody
	@PostMapping("/cart_add")
	public ResponseEntity<String> cart_add(CartVO vo, HttpSession session) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		// 로그인 상태(MemberController - modify 부분 참고)
		// MemberVO db_vo = (MemberVO) session.getAttribute("loginStatus");
		// ajax 방식에서 상품코드와 수량 정보만 전송되어 로그인한 사용자와 아이디정보 추가 작업
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		
		cartService.cart_add(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	@GetMapping("/cart_list")
	public void cart_list(HttpSession session, Model model) throws Exception {
		
		// 로그인 정보를 가져온다(id정보)
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		
		// 목록데이터를 모델로 추가
		List<CartDTOList> cart_list = cartService.cart_list(mbsp_id);
		
		double cart_total_price = 0;
		
//		cart_list.forEach(vo -> {
//			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
//			
//			// 금액 = 판매가 - (판매가 * 할인율) * 수량
//			cart_total_price += ((double)vo.getPro_price() - (vo.getPro_price() * (vo.getPro_discount() * 1/100)) * (double)vo.getCart_amount());	
//		});
		
		for(int i=0; i<cart_list.size(); i++) {
			CartDTOList vo = cart_list.get(i);
			
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
			cart_total_price += ((double)vo.getPro_price() - (vo.getPro_price() * vo.getPro_discount() * 1/100)) * (double)vo.getCart_amount();
		}
		
		// 위에서 호출되어 다시 불러와서는 안된다.
//		model.addAttribute("cart_list", cartService.cart_list(mbsp_id));
		
		model.addAttribute("cart_list", cart_list);
		model.addAttribute("cart_total_price", cart_total_price);
	}
	
	@ResponseBody // ajax로 요청을 받는다. <img src="매핑주소"> 형태로 받겠다
	@GetMapping("/imageDisplay") // "/user/product/imageDisplay?dateFolderName=값1&fileName=값2"
	public ResponseEntity<byte[]> imageDisplay(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + dateFolderName, fileName);
	}
	
	@PostMapping("/cart_amount_change")
	public ResponseEntity<String> cart_amount_change(Long cart_code, int cart_amount) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		cartService.cart_amount_change(cart_code, cart_amount);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
}
