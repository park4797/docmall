package com.test.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnailator;

// file upload 관련 필요한 method를 구성
public class FileUtils {
	
	// 날짜폴더 생성을 위한 날짜정보
	public static String getDateFolder() {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		String str = sdf.format(date); // ex ) 2023-11-02
		
		// File.separator : 각 OS별로 경로 구분자를 반환
		// 유닉스, 맥, 리눅스 구분자 : / ex ) 2023/11/02
		// 윈도우즈 구분자 : \, / ex ) 2023\11\02
		return str.replace("-", File.separator); 
	}
	/*
	 String uploadFolder : 서버측에 업로드될 폴더(value="C:\\dev\\upload\\product\\") - servlet-context.xml 참고
	 String dateFolder : 이미지파일을 저장할 날짜 폴더명 2023\11\03
	 MultipartFile uploadFile : upload된 파일을 참조하는 객체
	 */
	// 서버에 파일업로드 기능 및 실제업로드 한 파일명을 반환값으로 사용
	public static String uploadFile(String uploadFolder, String dateFolder, MultipartFile uploadFile) {
		
		String realUploadFileName = ""; // 실제 업로드한 파일이름(파일이름 중복 방지)
		
//		File클래스 : 파일과 폴더관련 작업하는 기능 제공
		// File 객체 생성
		File file = new File(uploadFolder, dateFolder); // 예> "C:/dev/upload"  "2023/11/02"
		
		// 예> "C:/dev/upload"  "2023/11/02" 폴더 경로가 없으면 폴더명을 생성
		if(file.exists() == false) {
			file.mkdirs(); // mkdir()은 경로의 마지막 폴더만 생성
		}
		
		// 클라이언트에서 전송한 원본파일명
		String clientFileName = uploadFile.getOriginalFilename();
		
		// UUID : 파일명을 중복방지를 위하여 고유한 문자열을 생성해주는 클래스
		UUID uuid = UUID.randomUUID();
		// 904e1906-ed6f-49d2-867a-7648f07628d4_Hello.PNG
		// 게시판 다운로드 기능 구현시 원본 파일명 ex) Hello.PNG 를 컬럼을 생성해 받아두는 것이 좋다.
		realUploadFileName = uuid.toString() + "_" + clientFileName;
 		
		try {
			// file : "C:/dev/upload/2023/11/02" + realUploadFileName(실제 업로드할 파일명)
			File saveFile = new File(file, realUploadFileName);
			
			// 파일업로드(파일복사) 
			uploadFile.transferTo(saveFile); // 파일업로드의 핵심 메소드
			
			// Thumbnail 작업
			// 원본이미지를 파일크기와 해상도를 낮추어 작은 형태의 이미지로 만드는 것
			if(checkImageType(saveFile)) { // 첨부된 파일이 이미지 일 경우라면
				
				// 썸네일 작업기능 라이브러리에서 제공하는 클래스, pom.xml 참고
				// 파일 출력 스트림
				// 밑에줄만 실행이 되면, 파일이 생성. 0 KB
				FileOutputStream thumbnail = new FileOutputStream(new File(file, "s_" + realUploadFileName));
				
				// 원본이미지파일의 내용을 스트림방식으로 읽어서, 썸네일이미지파일에 쓰는 작업
				/*
				 uploadFile.getInputStream() : 원본이미지파일의 입력스트림
				 thumbnail : 사본파일(thumbnail) 생성
				 */
				Thumbnailator.createThumbnail(uploadFile.getInputStream(), thumbnail, 100, 100);
				
				thumbnail.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace(); // 파일업로드시 예외가 발생되면, 예외정보를 출력
		}
		
		return realUploadFileName; // 상품테이블에 파일명 이름이 저장된다.(상품 이미지명)
	}
	
	// 자바스크립트로 업로드되는 파일의 확장자를 이용하여, 이미지파일만 파일첨부가능하도록 작업할 수 있다.
	// 업로드되는 파일구분(이미지파일 또는 일반파일 구분)
	private static boolean checkImageType(File saveFile) {
		
		boolean isImageType = false; // 변수의 값이 true면 이미지파일이고, false면 일반파일이다.
		
		try {
			// MIME 정보가 들어온다. : text/html, text/plain, image/jpg, ...
			String contentType = Files.probeContentType(saveFile.toPath());
			
			// contentType 변수의 값이 image 문자열로 시작하고 있는지 여부를 true, false 값으로 반환해준다
			isImageType = contentType.startsWith("image");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isImageType;
	}
	
	/* 프로젝트 외부폴더에서 관리되고 있는 상품이미지를 브라우저의 <img src="매핑주소"> 태그로부터 요청이 들어왔을때, 바이트 배열로 보내주는 작업
	   <img src="test.jpg"> 는 외부폴더에서 관리되고 있기 때문에 사용하지 않는다. -> 상품 등록시 DB에 filename이 저장되기 때문에 경로에서 불러오면 되기때문
	 */
	// <TypeParameter> 는 실제 return 값
	// String uploadPath : 업로드 폴더 경로, String fileName : 날짜폴더경로를 포함한 파일명(DB)
	// ResponseEntity class : 1) header, 2) body : 데이터에 대한 설명 등, 3) StatusCode 으로 구성
	public static ResponseEntity<byte[]> getFile(String uploadPath, String fileName) throws Exception { // DB 관련 작업이므로 예외처리
		
		ResponseEntity<byte[]> entity = null;
		
		// 이미지 파일을 참조하는 객체 생성
		File file = new File(uploadPath, fileName);
		
		// 파일이 해당경로에 존재하지 않으면
		if(!file.exists()) {
			return entity; // null로 리턴된다.
		}
		
		// 1) Header
		// Files.probeContentType(file.toPath()); : 위 file 객체를 통해 참조
		// 만약 파일이 jpg 파일일경우 image/jpeg로 생성된다.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", Files.probeContentType(file.toPath())); // jsp의 header처럼 Content-type MIME 정보를 맞춰준다. 
		
		// FileCopyUtils.copyToByteArray : 마임타입을 확인하지 못하면 null을 반환합니다.
		entity = new ResponseEntity<byte[]>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
		
		return entity;
	}
	
	// 파일삭제
	/*
	 String uploadPath : 업로드된 탐색기 경로 sevlet-context.xml의 uploadPath bean 정보를 사용
	 String folderName : 날자 폴더
	 String fileName : 실제 파일명
	 */
	public static void deleteFile(String uploadPath, String folderName, String fileName) {
		
		// File.separatorChar : 배포된 서버의 운영체제에서 사용하는 파일의 경로구분자
		// ex) 윈도우즈 : \ 리눅스 : / 
		
		// 1) 원본이미지 삭제 (DB부분의 날짜데이터의 뒤에 /가 없기때문에 / 추가)
		new File((uploadPath + folderName + "\\" + fileName).replace('\\', File.separatorChar)).delete();
		
		// 2) 썸네일이미지 삭제
		new File((uploadPath + folderName + "\\" + "s_" + fileName).replace('\\', File.separatorChar)).delete();
	}
	
}
