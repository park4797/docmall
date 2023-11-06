package com.test.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.domain.ProductVO;
import com.test.dto.Criteria;
import com.test.dto.PageDTO;
import com.test.service.AdProductService;
import com.test.util.FileUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/admin/product/*")
@Controller
@RequiredArgsConstructor
public class AdProductController {
	
	private final AdProductService adProductService;
	
	// 메인, 썸네일 업로드 폴더경로 주입작업
	@Resource(name="uploadPath") // servlet-context.xml 의 bean 이름 참조를 해야 한다.
	private String uploadPath;
	
	// CkEditor 에서 사용되는 업로드 폴더경로 주입작업
	@Resource(name="uploadCkPath") // servlet-context.xml 의 bean 이름 참조를 해야 한다.
	private String uploadCkPath;
	
	// 상품등록 폼
	@GetMapping("/pro_insert")
	public void pro_insert() {
		log.info("상품등록 폼");
	}
	
	// 1차카테고리 데이터를 Model로 작업
	/*
	@GetMapping("이름")
	public void aaa(Model model) {
		
		model.addAttribute("cg_code", "1차카테고리정보");
	}
	*/
	
	// 상품정보 저장
	// 파일업로드 기능 : 1) 스프링에서 내장된 기본라이브러리 사용,
	//				2) 외부 라이브러리 사용(commons-fileupload, pom.xml 참고)
	// 넘어오는 파일이 여러개면 MultipartFile을 List로 감싸주어야 한다.
	// MultipartFile uploadFile을 ProductVO에 추가하여 사용하여도 된다.
	
	/* MultipartFile uploadFile 파라미터명은 jsp name 속성과 일치해야한다.
	  <input type="file" class="form-control" name="uploadFile" id="uploadFile">
	 */
	@PostMapping("/pro_insert")
	public String pro_insert(ProductVO vo, MultipartFile uploadFile, RedirectAttributes rttr) {
		
		log.info("상품정보 : " + vo);
		
		// 1) 파일 업로드 작업. 선수작업 : FileUtils 클래스작업
		String dateFolder = FileUtils.getDateFolder();
		
		// FileUtils의 uploadFile의 매개변수로 들어가게된다.
		String savedFileName = FileUtils.uploadFile(uploadPath, dateFolder, uploadFile);
		
		vo.setPro_img(savedFileName);
		vo.setPro_up_folder(dateFolder);
		
		log.info("상품정보 : " + vo);
		
		// 2) 상품정보 저장
		adProductService.pro_insert(vo);
		
		
		return "redirect:/admin/product/pro_list"; // 상품리스트 주소로 이동
	}
	
	// CkEditor 업로드 탭에서 파일업로드시 동작하는 매핑주소
	// MultipartFile upload : CkEditor에 업로드된 파일을 참조하는 객체
	// MultipartFile upload 변수와 CkEditor의 태그의 name과 일치 되어야한다.
	// <input type="file" name="upload" size="38">
	// HttpServletRequest 클래스 : jsp의 request 객체 클래스(클라이언트의 요청을 담고 있는 객체)
	// HttpServletResponse 클래스 : jsp의 response객체 클래스(클라이언트로 보낼 서버측의 응답정보를 가지고 있는 객체)
	@PostMapping("/imageUpload")
	public void imageUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) {
		
		// try 코드 외에도 사용하기 위해 전역변수로 선언
		OutputStream out = null;
		PrintWriter printWriter = null; // 클라이언트에게 서버의 응답정보를 보낼때 사용
		
		/* jsp 파일의 기본 세팅
		 <%@ page language="java" contentType="text/html; charset=UTF-8"
    		pageEncoding="UTF-8"%>
		 */
		
		// 클라이언트에게 보내는 응답설정
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		try {
			
			// 1) 파일업로드 작업
			String fileName = upload.getOriginalFilename(); // 클라이언트에서 전송한 파일이름 
			byte[] bytes = upload.getBytes(); // 업로드한 파일을 byte 배열로 읽어들인다.
			
			// uploadCkPath에 완전한 경로가 존재하게 된다.
			String ckUploadPath =  uploadCkPath + fileName;
			
			log.info("CkEditor파일 경로" + ckUploadPath);
			
			out = new FileOutputStream(new File(ckUploadPath)); // 0 kb 파일 생성
			
			out.write(bytes); // 출력스트림작업
			out.flush();
			
			// 2) 파일업로드 작업후 파일 정보를 CkEditor로 보내는 작업
			printWriter = response.getWriter();
			
			// 브라우저의 CkEditor에서 사용할 업로드한 파일정보를 참조하는 경로 설정.
			/*
			 1) 톰캣 Context Path 설정 - add External Web Module 작업
			 	Document Base : C:\\dev\\upload\\ckeditor
			 	Path : /ckupload
			 2) server.xml에서 <Context docBase="업로드경로" path="/매핑주소" reloadable="true"/>	
			 	<Context docBase="test_project" path="/" reloadable="true" source="org.eclipse.jst.jee.server:test_project"/>
      			<Context docBase="C:\\dev\\upload\\ckeditor" path="/ckupload" reloadable="true"/>
			 */
			
			// CkEditor에서 업로드된 파일경로를 보내준다.(매핑주소와 파일명이 포함)
	         String fileUrl = "/ckupload/" + fileName;
	         // {"filename":"abc.gif", "uploaded":1, "url":"/upload/abc.gif"}
	         printWriter.println("{\"filename\":\"" +  fileName + "\", \"uploaded\":1,\"url\":\"" + fileUrl + "\"}");
	         printWriter.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			if(printWriter != null) printWriter.close();
		}
	}
	
	// 상품리스트(목록과 페이징 작업)
	@GetMapping("/pro_list")
	public void pro_list(Criteria cri, Model model) throws Exception {
		
		cri.setAmount(2);
		// 목록데이터를 모델로 추가
		List<ProductVO> pro_list = adProductService.pro_list(cri);
		
		// 날짜폴더의 역슬래시를 슬래시로 바꾸는 작업
		// (브라우저에서 역슬래시로 되어있는 정보가 스프링으로 전달되면 과정에서 에러발생)
		// 자바스크립트에서도 처리할 수 있다.
		pro_list.forEach(vo -> {
			vo.setPro_up_folder(vo.getPro_up_folder().replace("\\", "/"));
		});
		model.addAttribute("pro_list", pro_list); // jsp에서 사용하기위해 Model을 추가
		
		int totalCount = adProductService.getTotalCount(cri);
		model.addAttribute("pageMaker", new PageDTO(cri, totalCount));
	}
	
	// 상품리스트에서 보여줄 이미지
	@ResponseBody // ajax로 요청을 받는다. <img src="매핑주소"> 형태로 받겠다
	@GetMapping("/imageDisplay") // "/admin/product/imageDisplay?dateFolderName=값1&fileName=값2"
	public ResponseEntity<byte[]> imageDisplay(String dateFolderName, String fileName) throws Exception {
		
		return FileUtils.getFile(uploadPath + dateFolderName, fileName);
	}
}
