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

<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
  <h2>장바구니</h2>
</div>
   
<div class="container-lg">
  <table class="table">
    <thead class="thead-dark">
      <tr style="text-align: center;">
        <th scope="col"><input type="checkbox"></th>
        <th scope="col">번호</th>
        <th scope="col">상품</th>
        <th scope="col">상품명</th>
        <th scope="col">판매가</th>
        <th scope="col">수량</th>
        <th scope="col">할인</th>
        <th scope="col">합계</th>
        <th scope="col">비고</th>
      </tr>
      <!--<img width="100%" height="200" src="/user/cart/imageDisplay?dateFolderName=${productVO.pro_up_folder}&fileName=${productVO.pro_img}">-->
    </thead>
    <tbody>
    <c:forEach items="${cart_list}" var="cartDTO">
      <tr style="text-align: center;">
        <td>
          <input type="checkbox" name="cart_code" value="${cartDTO.cart_code}">
        </td>
        <th scope="row">${cartDTO.cart_code}</th>
        <td><img width="80" height="80" src="/user/cart/imageDisplay?dateFolderName=${cartDTO.pro_up_folder}&fileName=${cartDTO.pro_img}"></td>
        <td>${cartDTO.pro_name}</td>
        <td><span id="unitPrice">${cartDTO.pro_price}</span></td>
        <td>
          <input type="number" value="${cartDTO.cart_amount}" style="width: 35px;" name="cart_amount">
          <button type="button" class="btn btn-danger" name="btn_cart_amount_change">변경</button>
        </td>
        <td><span id="unitDiscount">${cartDTO.pro_discount * 1/100}</span></td>
        <td><span class="unitTotalPrice" id="unitTotalPrice">${(cartDTO.pro_price - (cartDTO.pro_price * (cartDTO.pro_discount * 1/100))) * cartDTO.cart_amount}</span></td>
        <td>
          <button type="button" class="btn btn-danger" name="btn_ajax_cart_del">삭제(ajax)</button>
          <button type="button" class="btn btn-danger" name="btn_non_ajax_cart_del">삭제(non_ajax)</button>
        </td>
      </tr>
	  </c:forEach>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="12"><button type="button" class="btn btn-danger">선택삭제</button></td>
      </tr>
      <tr>
        <td colspan="12" style="text-align : right;">
          최종결제금액 : <span id="cart_total_price">${cart_total_price}</span>
        </td>
      </tr>
      <tr>
        <td colspan="12" style="text-align: center;">
          <button type="button" class="btn btn-primary" id="btn_order">주문하기</button>
        </td>
      </tr>
    </tfoot>
  </table>



<%@include file="/WEB-INF/views/comm/footer.jsp" %>

</div>


  <%@include file="/WEB-INF/views/comm/plugIn.jsp" %>
  <!-- 카테고리 메뉴 자바 스크립트 작업. -->
  <script src="/js/category_menu.js"></script>

  <script>
    // 전체주문금액
    function fn_cart_sum_price() {
      let sumPrice = 0;

      $(".unitTotalPrice").each(function() {
        sumPrice += Number($(this).text());
      });
      $("#cart_total_price").text(sumPrice)
    }

    // 동적코딩을 할 때 값을 변경하는 작업을 할때는 절차적으로 작업해야 한다.
    $(document).ready(function() {
      
      $("button[name='btn_cart_amount_change']").on("click", function() {

        let cur_btn_change = $(this); // 현재 선택된 버튼 태그의 위치를 참조(ajax 작업전에 참조해야 한다.)

        let cart_amount = $(this).parent().find("input[name='cart_amount']").val();
        // console.log("수량", cart_amount);
        
        let cart_code = $(this).parent().parent().find("input[name='cart_code']").val()
        // console.log("장바구니번호", cart_code);

        $.ajax({
          url : '/user/cart/cart_amount_change',
          type : 'post',
          data : {cart_code : cart_code, cart_amount : cart_amount},
          dataType : 'text',
          success : function(result) {
            if(result == 'success') {
              alert("수량이 변경되었습니다.");

              // 합계금액 계산작업
              // 금액 = 판매가 - (판매가 * 할인율) * 수량
              let unitPrice = cur_btn_change.parent().parent().find("span#unitPrice").text();
              let unitDiscount = cur_btn_change.parent().parent().find("span#unitDiscount").text();

              // 장바구니 코드별 단위금액
              let unitTotalPrice = cur_btn_change.parent().parent().find("span#unitTotalPrice");
              unitTotalPrice.text((unitPrice - (unitPrice * unitDiscount)) * cart_amount);

              fn_cart_sum_price()
            }
          }
        })
      });

      // 장바구니 삭제(ajax 사용)
      $("button[name='btn_ajax_cart_del']").on("click", function() {

        if(!confirm("상품을 삭제하시겠습니까?")) return;

        let cart_code = $(this).parent().parent().find("input[name='cart_code']").val() // 체크박스에 숨겨진 값
        // console.log("카트코드", cart_code);

        let cur_btn_delete = $(this);

        $.ajax({
          url : '/user/cart/cart_list_del',
          type : 'post',
          dataType : 'text', // success라는 값을 받기 위함. (스프링의 return type)
          data : {cart_code : cart_code},
          success : function(result) {
            if(result == "success") {
              alert("삭제되었습니다.");

              cur_btn_delete.parent().parent().remove(); // 삭제된 장바구니 데이터행 제거

              fn_cart_sum_price()
            }
          }
        });
      });

      // 장바구니 삭제(non_ajax)
      $("button[name='btn_non_ajax_cart_del']").on("click", function() {

        if(!confirm("삭제하시겠습니까?")) return;

        let cart_code = $(this).parent().parent().find("input[name='cart_code']").val()
        location.href="/user/cart/cart_list_del?cart_code=" + cart_code;
      });

      $("button#btn_order").on("click", function() {
        location.href = "/user/order/orderListInfo";
      })
    });//
  </script>
  </body>
</html>