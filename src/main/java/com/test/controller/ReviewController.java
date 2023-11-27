package com.test.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.domain.MemberVO;
import com.test.domain.ReviewVO;
import com.test.dto.Criteria;
import com.test.dto.PageDTO;
import com.test.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequiredArgsConstructor
@Log4j
@RequestMapping("/user/review/*")
@RestController // jsp 파일을 사용하지 않는다.
public class ReviewController {

	private final ReviewService reviewService;
	
	/*
	 consumes : 사용자가 Request Body에 담는 타입을 제한할 수 있다.
	 application/json 일 경우의 요청만을 처리한다는 의미.
	  따라서 요청시 헤더에 꼭 application/json 이 존재해야함.
	 contentType() 을 이용하여 테스트 요청에 header를 추가할 수 있다.
	 */
	
	// @RequestBody : JSON 데이터를 ReviewVO 객체로 변환시켜주는 기능.
	// 반대의미는 @ResponseBody
	@PostMapping(value = "/new", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> review_insert(@RequestBody ReviewVO vo, HttpSession session) throws Exception {
		// ResponseEntity<String>는 대부분 insert update delete 에 사용, 'seccess'를 보내주기 위함
		/*
		 ResponseEntity 사용 이유
		  - ajax 호출 목적
		  - 서버에서 실행된 값을 클라이언트에 보낼때 header 작업(데이터의 부연설명)을보내기 위해
		  - HttpStatus를 보내주기 위해
		 
		 */
		log.info("리뷰 : " + vo);
		
		// MemberController 에서 ID를 가져온 방식과 동일
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		  
		ResponseEntity<String> entity = null;
		
		// DB 저장
		reviewService.review_insert(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
	
	// @PathVariable : path 변수를 받는 어노테이션
	// 쿼리스트링 : list?pro_num=10&page=1 -> // Rest API 주소 : list/10/1
	@GetMapping("/list/{pro_num}/{page}")
	public ResponseEntity<Map<String, Object>> list(@PathVariable("pro_num") Integer pro_num, @PathVariable("page") Integer page) throws Exception {
		// ResponseEntity<Map<String, Object>> 는 대게 select 에 사용
		/*
		  리턴타입에 따른 구문
			ResponseEntity<Map<String, Object>> : 1)상품후기 목록 데이터 List<ReviewVO>, 2)페이징 데이터 PageDTO
			상품후기 목록 데이터 - List<ReviewVO> 만 보내주고 싶다면 ResponseEntity<ReviewVO>
			페이징 데이터 - PageDTO ResponseEntity<PageDTO>
			서로 다른 Datatype을 받기위해 Object를 사용했다.
		 */
		// 각 구문에 return은 하나만 할 수 있기 때문에 map으로 묶어 한 번에 리턴. 또한 map에는 제네릭 문법도 사용 가능하기 때문
		
		ResponseEntity<Map<String, Object>> entity = null;
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 1)상품후기 목록 데이터
		Criteria cri = new Criteria();
		cri.setAmount(2);
		cri.setPageNum(page);
		
		List<ReviewVO> list = reviewService.list(pro_num, cri);
		
		// 2)페이징 데이터
		int listCount = reviewService.listCount(pro_num);
		PageDTO pageMaker = new PageDTO(cri, listCount);

		map.put("list", list); // 상품후기 목록 데이터 List<ReviewVO>
		map.put("pageMaker", pageMaker); // 페이징데이터 PageDTO
		
		// jackson-databind 라이브러리에 의하여 map -> json으로 변환되어 ajax 호출한 쪽으로 return값이 전송된다.
		entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
		
		return entity;
	}
	
	// 상품후기 삭제
	@DeleteMapping("/delete/{rew_num}") // /user/review/delete/삭제하고자하는 후기 번호
	public ResponseEntity<String> delete(@PathVariable("rew_num") Long rew_num) throws Exception {
		
		ResponseEntity<String> entity = null;
		
		// DB 연동
		reviewService.delete(rew_num);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
				
		return entity;
	}
	
	// 상품후기 수정
	@PutMapping(value = "/modify", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> review_modify(@RequestBody ReviewVO vo, HttpSession session) throws Exception {
		// ResponseEntity<String>는 대부분 insert update delete 에 사용, 'seccess'를 보내주기 위함
		/*
		 ResponseEntity 사용 이유
		  - ajax 호출 목적
		  - 서버에서 실행된 값을 클라이언트에 보낼때 header 작업(데이터의 부연설명)을보내기 위해
		  - HttpStatus를 보내주기 위해
		 
		 */
		log.info("리뷰 : " + vo);
		
		// MemberController 에서 ID를 가져온 방식과 동일
		String mbsp_id = ((MemberVO) session.getAttribute("loginStatus")).getMbsp_id();
		vo.setMbsp_id(mbsp_id);
		  
		ResponseEntity<String> entity = null;
		
		// DB 저장
		reviewService.modify(vo);
		
		entity = new ResponseEntity<String>("success", HttpStatus.OK);
		
		return entity;
	}
}

