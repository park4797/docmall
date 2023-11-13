<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.101.0">
    <title>Pricing example · Bootstrap v4.6</title>
  
  <!-- Bootstrap Core CSS -->
    <%@include file="/WEB-INF/views/comm/plugIn2.jsp" %>



    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>

  
  </head>
  <body>
    
<%@include file="/WEB-INF/views/comm/header.jsp" %>

<%@include file="/WEB-INF/views/comm/category_menu.jsp" %>

<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
  <p>2차카테고리 : ${cg_name}</p>
</div>
   
<div class="container">
  <div class="card-deck mb-3 text-center row">
  <c:forEach items="${pro_list}" var="productVO">
    <div class="col-md-3">
      <div class="col-md-4">
          <div class="card mb-4 shadow-sm">
            <img width="100%" height="200" src="/user/product/imageDisplay?dateFolderName=${productVO.pro_up_folder}&fileName=${productVO.pro_img}">
            <div class="card-body">
              <p class="card-text">${productVO.pro_name}</p>
              <div class="d-flex justify-content-between align-items-center">
                <div class="btn-group">
                  <button type="button" name="btn_cart_add" class="btn btn-sm btn-outline-secondary">Cart</button>
                  <button type="button" name="btn_buy" class="btn btn-sm btn-outline-secondary">Buy</button>
                </div>
                <small class="text-muted">
                	<fmt:formatNumber type="currencyt" pattern="￦#,###" value="${productVO.pro_price}"></fmt:formatNumber>
                </small>
              </div>
            </div>
          </div>
        </div>
    </div>
    </c:forEach>
  </div>
  <div class="row">
    <div class="col-md-4">
      <!--1)페이지번호 클릭할 때 사용  [이전]  1   2   3   4   5 [다음]  -->
      <!--2)목록에서 상품이미지 또는 상품명 클릭할 때 사용   -->
      <form id="actionForm" action="" method="get">
        <input type="hidden" name="pageNum" id="pageNum" value="${pageMaker.cri.pageNum}" />
        <input type="hidden" name="amount"  id="amount" value="${pageMaker.cri.amount}" />
        <input type="hidden" name="type" id="type" value="${pageMaker.cri.type}" />
        <input type="hidden" name="keyword" id="keyword" value="${pageMaker.cri.keyword}" />

        <input type="hidden" name="cg_code" id="cg_code" value="${cg_code}" />
        <input type="hidden" name="cg_name" id="cg_name" value="${cg_name}" />
      </form>
    </div>
    <div class="col-md-6 text-center">
        <nav aria-label="...">
        <ul class="pagination">
          <!-- 이전 표시여부 -->
          <c:if test="${pageMaker.prev}">
              <li class="page-item">
                <a href="${pageMaker.startPage - 1 }" class="page-link movepage">Previous</a>
              </li>
          </c:if>
          <!-- 페이지번호 출력 -->
          <!-- 1   2   3   4   5 6   7   8   9   10  [다음] -->
          <!-- [이전] 11   12   13   14   15 16   17   18   19   20   -->
          <c:forEach begin="${pageMaker.startPage }" end="${pageMaker.endPage }" var="num">
              <li class='page-item ${pageMaker.cri.pageNum ==  num ? "active":"" }'aria-current="page">
                <a class="page-link movepage" href="${num }" data-page="${num }">${num }</a>
              </li>
          </c:forEach>
          
          <!--  다음 표시여부 -->
          <c:if test="${pageMaker.next }">
              <li class="page-item">
              <a href="${pageMaker.endPage + 1 }" class="page-link movepage" href="#">Next</a>
              </li>
          </c:if>
          
        </ul>
        </nav>
    </div>
</div>

<%@include file="/WEB-INF/views/comm/footer.jsp" %>

</div>


  <%@include file="/WEB-INF/views/comm/plugIn.jsp" %>
  <!-- 카테고리 메뉴 자바 스크립트 작업. -->
  <script src="/js/category_menu.js"></script>

  <script>
    // 동적코딩을 할 때 값을 변경하는 작업을 할때는 절차적으로 작업해야 한다.
    $(document).ready(function() {
  
      let actionForm = $("#actionForm");
  
      $(".movepage").on("click", function(event) {
        event.preventDefault(); // a태그의 href속성에 페이지번호를 숨겨두었다.
  
        actionForm.attr("action", "/user/product/pro_list");
  
        // actionForm 태그를 가지고 있는 하위요소중 input 태그의 name 이 pageNum인 것을 찾는 작업
        actionForm.find("input[name='pageNum']").val($(this).attr("href"));
  
        actionForm.submit();
      });

      // 장바구니 추가
      $("button[name='btn_cart_add']").on("click", function() {
        console.log("장바구니 추가");
      });

      // 상품 구매
      $("button[name='btn_buy']").on("click", function() {
        console.log("상품 구매");
      });

    }); //
  </script>
  </body>
</html>