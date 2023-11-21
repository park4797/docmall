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
    <link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
    <link rel="stylesheet" href="https://jqueryui.com/resources/demos/style.css">
    <script src="https://code.jquery.com/jquery-3.6.0.js"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>



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
 <script>
        $( function() {
       $( "#tabs_pro_detail" ).tabs();
        } );
 </script>
  
  </head>
  <body>
    
<%@include file="/WEB-INF/views/comm/header.jsp" %>

<%@include file="/WEB-INF/views/comm/category_menu.jsp" %>

<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
  <p>2차 카테고리 : ${cg_name }</p>
</div>
  
<div class="container">
  <div class="card-deck mb-3 text-center row">
    <div class="col-md-6">
      상품 이미지
      <img class="btn_pro_img" data-pro_num="${productVO.pro_num}" width="100%" height="200" src="/user/product/imageDisplay?dateFolderName=${productVO.pro_up_folder}&fileName=${productVO.pro_img}" >
    </div>
    <div class="col-md-6">
      <div class="row text-left">
        <div class="col">
          상품이름 : ${productVO.pro_name}
        </div>
      </div>
      <div class="row text-left">
        <div class="col">
          가격 : <span id="unit_price">${productVO.pro_price}</span>
        </div>
      </div>
      <div class="row text-left">
        <div class="col">
          수량 : <input type="number" id="btn_quantity" value="1" style="width: 80px;">
        </div>
      </div>
      <div class="row text-left">
        <div class="col">
          총 상품금액 : <span id="tot_price">${productVO.pro_price}</span>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6">
          <button type="button" class="btn btn-outline-secondary" name="btn_order" data-pro_num="${productVO.pro_num }">구매하기</button>
        </div>
        <div class="col-md-6">
          <button type="button" class="btn btn-outline-secondary" name="btn_cart_add" data-pro_num="${productVO.pro_num }">장바구니</button> 
        </div>
      </div>
    </div>
  </div>
<div>
  <div id="tabs_pro_detail">
    <ul>
      <li><a href="#tabs_prodetail">상품 설명</a></li>
      <li><a href="#tabs_proreview">상품 후기</a></li>
    </ul>
    <div id="tabs_prodetail">
      <p>${productVO.pro_content}</p>
    </div>
    <div id="tabs_proreview">
      <p>상품후기 목록</p>
    </div>


</div>
</div>
<%@include file="/WEB-INF/views/comm/footer.jsp" %>
  <%-- <%@include file="/WEB-INF/views/comm/plugIn.jsp" %> --%>
  <!-- 카테고리 메뉴 자바 스크립트 작업. -->
  <script src="/js/category_menu.js"></script>
  

  <script>
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
        // console.log("장바구니 추가");
        $.ajax({
          url : '/user/cart/cart_add',
          type : 'post',
          dataType : 'text',
          data : {pro_num : $(this).data("pro_num"), cart_amount : $("#btn_quantity").val()}, // 동일한 변수명으로 보내주어야 한다.
          // $(this).data("pro_num") 위 data에 추가한 내용
          success : function(result) {
            if(result == 'success') {
              alert("장바구니에 추가되었습니다.");
              if(confirm("장바구니로 이동하시겠습니까?")) {
                location.href="/user/cart/cart_list";
              }
            }
          }
        });
      });

      // 구매하기(주문)
      $("button[name='btn_order']").on("click", function() {

        let url = "/user/order/order_ready?pro_num=" + $(this).data("pro_num") + "&cart_amount=" + $("#btn_quantity").val();
        location.href = url;
      });

      // 상품 이미지또는 상품명 클릭 시 상세로 보내는 작업.
      $(".btn_pro_img").on("click", function() {
        console.log("상품상세설명");
        // 상품 상세 주소 설정
        actionForm.attr("action", "/user/product/pro_detail");

        let pro_num = $(this).data("pro_num");

        actionForm.find("input[name='pro_num']").remove();
        // <input type='hidden' name='pro_num' value='상품코드'>
        actionForm.append("<input type='hidden' name='pro_num' value='"+ pro_num +"'>");
        actionForm.submit();
      });



      // 수량변경 작업
      $("#btn_quantity").on("change", function() {
        let tot_price = parseInt($("#unit_price").text()) * $("#btn_quantity").val();

        let unitprice = $("#unit_price").text();

        console.log("tot_price", tot_price);

        // 총상품금액 표시
        $("#tot_price").text(tot_price);
      });




  // ready end 
  }); 
  </script>

  </body>
</html>