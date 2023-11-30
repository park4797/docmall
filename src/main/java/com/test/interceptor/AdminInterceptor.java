package com.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.test.domain.AdminVO;

public class AdminInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// HttpServletRequest request 현재 사용자의 session을 확보할 수 있다.
		// HttpServletResponse response 응답을 주기 위해
		
		boolean result = false; // return 타입으로 사용할 변수 선언
		
		// 현재 클라이언트의 세션을 통한 인증상태를 확인작업을 할 수가 있다.
		HttpSession session = request.getSession();
		AdminVO user = (AdminVO) session.getAttribute("adminStatus"); // 현재 로그인의 판단여부
		
		if(user == null) { // session에 인증정보가 없을 경우
			result = false; // Controller로 진행 하지 못한다.
			
			if(isAjaxRequest(request)) {
				
				response.sendError(400); // 클라이언트의 ajax구문에서 error : 동작됨
				
			} else {
				getTargetUrl(request);
				response.sendRedirect("/admin/intro");
			}
			
		} else { // loginStatus session 정보가 존재할 경우
			result = true; // 요청한 Controller로 진행
		}
		
		return result;
	}

	// 인증되지 않은 상태에서 사용자가 요청한 URL을 저장하고, 로그인 후 URL로 redirect 작업
	private void getTargetUrl(HttpServletRequest request) {
		
		// 요청주소 localhost:9090/member/modify?userid=doccomsa일시
		String uri = request.getRequestURI(); // /member/modify
		String query = request.getQueryString(); // userid=doccomsa
		
		if(query == null || query.equals("null")) {
			query = "";
		} else {
			query = "?" + query;
		}
		
		String targetUrl = uri + query;
		
		if(request.getMethod().equals("GET")) {
			request.getSession().setAttribute("targetUrl", targetUrl);
		}
	}
	
	// 사용자 요청이 ajax 요청인지 체크
	private boolean isAjaxRequest(HttpServletRequest request) {
		
		boolean isAjax = false;
		
		// 사용자에게서 넘어오는 정보의 헤더에 AJAX가 포함되어있는지
		String header = request.getHeader("AJAX");
		if(header != null && header.equals("true")) {
			isAjax = true;
		}
		
		return isAjax;
	}
}
