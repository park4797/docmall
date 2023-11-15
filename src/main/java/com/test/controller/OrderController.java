package com.test.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.domain.MemberVO;
import com.test.dto.CartDTOList;
import com.test.service.CartService;
import com.test.service.OrderService;
import com.test.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@RequestMapping("/user/order/*")
@RequiredArgsConstructor
public class OrderController {

	@Resource(name="uploadPath") // servlet-context.xml 의 bean 이름 참조를 해야 한다.
	private String uploadPath;
	
	private final CartService cartService;
	
	private final OrderService orderService;

	// 주문정보페이지
	@GetMapping("/order_info")
	public void order_info(HttpSession session, Model model) throws Exception {
	
		// 로그인 정보를 가져온다(주문정보(장바구니) 목록을 가져온다)
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
				
		// 목록데이터를 모델로 추가
		List<CartDTOList> order_info = cartService.cart_list(mbsp_id);
				
		double order_total_price = 0;
				
//		cart_list.forEach(vo -> {
//			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
//					
//			// 금액 = 판매가 - (판매가 * 할인율) * 수량
//			cart_total_price += ((double)vo.getPro_price() - (vo.getPro_price() * (vo.getPro_discount() * 1/100)) * (double)vo.getCart_amount());	
//		});
				
		for(int i=0; i<order_info.size(); i++) {
					CartDTOList vo = order_info.get(i);
					
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
					
//					vo.setPro_discount(vo.getPro_discount() * 1/100);
			order_total_price += ((double)vo.getPro_price() - (vo.getPro_price() * vo.getPro_discount() * 1/100)) * (double)vo.getCart_amount();
		}
				
				// 위에서 호출되어 다시 불러와서는 안된다.
//				model.addAttribute("cart_list", cartService.cart_list(mbsp_id));
				
		model.addAttribute("order_info", order_info);
		model.addAttribute("order_price", order_total_price);
	}
	
	@ResponseBody // ajax로 요청을 받는다. <img src="매핑주소"> 형태로 받겠다
	@GetMapping("/imageDisplay") // "/user/product/imageDisplay?dateFolderName=값1&fileName=값2"
	public ResponseEntity<byte[]> imageDisplay(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + dateFolderName, fileName);
	}
}
