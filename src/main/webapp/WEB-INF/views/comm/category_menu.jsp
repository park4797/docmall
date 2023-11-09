<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

  <div id="category_menu">
    <ul class="nav justify-content-center" id="first_category">
    <c:forEach items="${firstCategoryList}" var="category">
      <li class="nav-item">
      <!-- a 태그에 값을 숨기는 방법: href 또는 data- 속성 이용 -->
        <a class="nav-link active" href="#" data-cg_code="${category.cg_code}">${category.cg_name}</a>
      </li>
    </c:forEach>
    </ul>
  </div>

<!--
1) JS와 jsp문법이 혼합되어 있을 경우 views폴더 하위에 파일 확장자는 jsp로 사용
2) 순수하게 자바스크립트 코드로만 이루어져 있다면 resources 폴더 하위에 파일확장자 js로 저장
-->

