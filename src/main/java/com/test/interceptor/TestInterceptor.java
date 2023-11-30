package com.test.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.log4j.Log4j;

/* 인터셉터기능을 적용하기 위한 준비
 인터셉터 클래스를 생성, HandlerInterceptorAdapter 추상클래스를 상속받는다.
 주요 3가지 메소드를 재정의
*/

// 인터셉터 클래스를 사용하기 위해 servlet-context.xml 에서 설정해야 한다.

//@Log4j 이 환경에서는 lombok이 안된다.
public class TestInterceptor extends HandlerInterceptorAdapter {

	// 세 가지 메소드는 이벤트적인 성격으로 동작
	// https://popo015.tistory.com/115
	// https://velog.io/@dnflekf2748/%EC%8A%A4%ED%94%84%EB%A7%81-%EC%9D%B8%ED%84%B0%EC%85%89%ED%84%B0Interceptor
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 컨트롤러에서 공통적으로 필요한 작업(인증, 권한, 로깅)
//		log.info("인터셉터 : preHandle(호출)");
		// Controller가 실행되기전에 intercept가 먼저 호출된다
		System.out.println("인터셉터 : preHandle(호출)");
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		// Controller 실행후에 호출된다
		System.out.println("인터셉터 : postHandle(호출)");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		// postHandle 실행후에 호출된다
		System.out.println("인터셉터 : afterCompletion(호출)");
		super.afterCompletion(request, response, handler, ex);
	}

}
