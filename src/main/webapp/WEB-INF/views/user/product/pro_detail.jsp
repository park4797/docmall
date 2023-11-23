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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
    <!-- https://handlebarsjs.com/guide/#what-is-handlebars
      Handlebar : 자바스크립트 템플릿 엔진
      - 서버에서 보내온 JSON 형태의 데이터를 사용하여 작업을 편하게 할 수 있는 특징이 있다.
    -->

    <script id="reviewTemplate" type="text/x-handlebars-template">
      <table class="table table-sm">
        <thead>
          <tr>
            <th scope="col">번호</th>
            <th scope="col">리뷰내용</th>
            <th scope="col">별평점</th>
            <th scope="col">날짜</th>
          </tr>
        </thead>
        <tbody>
          {{#each .}}
          <tr>
            <th scope="row">{{rew_num}}</th>
            <td>{{rew_content}}</td>
            <td style="color: royalblue;">{{starRating rew_score}}</td>
            <td>{{convertDate rew_regdate}}</td> <!-- Handlebars 함수가 들어가게 되고 rew_regdate가 파라미터가 된다. -->
          </tr>
          {{/each}}
        </tbody>
      </table>
    </script>

    <script>
      $(function() {
        $( "#tabs_pro_detail" ).tabs();
      });
    </script>

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

      .ui-draggable, .ui-droppable {
        background-position: top;
      }

      /* 별평점 기본선택자 */
      p#star_rv_score a.rv_score {
        font-size: 22px;
        text-decoration: none;
        color: black;
      }

      /* 별평점에 mouseover 했을 경우 사용 */
      p#star_rv_score a.rv_score.on {
        color: green;
      }
    </style>

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
      <img class="btn_pro_img" data-pro_num="${productVO.pro_num}" width="100%" height="300" src="/user/product/imageDisplay?dateFolderName=${productVO.pro_up_folder}&fileName=${productVO.pro_img}" >
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
      <div class="row">
        <div class="col-md-12" id="review_list">

        </div>
        <div class="col-md-12 text-right">
          <button type="button" id="btn_review_write" class="btn btn-primary" data-pro_num="${productVO.pro_num }">상품후기등록</button>
        </div>
      </div>
    </div>


</div>
</div>
<!--JSP 주석 <%-- --%> -->
<%@include file="/WEB-INF/views/comm/footer.jsp" %>
<%-- <%@include file="/WEB-INF/views/comm/plugIn.jsp" %> --%>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>

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

      // 상품후기 작성
      $("#btn_review_write").on("click", function() {
        $('#review_modal').modal('show');
      });

      // 별평점 클릭시(5개로 작성됨)
      $("p#star_rv_score a.rv_score").on("click", function(e) {
        e.preventDefault();
        
        // $(this) = 클릭한 <a> 태그
        $(this).parent().children().removeClass("on");
        $(this).addClass("on").prevAll("a").addClass("on");
      });

      // 상품평 목록을 불러오는 작업(이벤트 사용 없이 직접 호출)
      
      let reviewPage = 1; // 목록의 첫번째 페이지를 보여주기 위함
      let url = "/user/review/list/" + "${productVO.pro_num}" + "/" + reviewPage; // 스프링의 @GetMapping("/list/{pro_num}/{page}") 를 가리킨다.

      getReviewInfo(url);

      function getReviewInfo(url) {
        $.getJSON(url, function(data){
          // data = 상품후기와 페이징등 스프링에서 보내는 정보가 담겨있다.

          // console.log("상품후기", data.list[0].rew_content);
          // console.log("상품후기", data.pageMaker.total);

          printReviewList(data.list, $("#review_list"), $("#reviewTemplate"));
        });
      }

      // 상품후기작업
      let printReviewList = function(reviewData, target, template) {
        let templateObj = Handlebars.compile(template.html());
        let reviewHtml = templateObj(reviewData);
        // 완성된 html 코드가 들어온다.

        // 상품후기목록 위치를 참조하여, 추가
        $("#review_list").children().remove();
        target.append(reviewHtml);
      }

      // 사용자 정의 Helper (handlebars의 함수정의)
      // 상품후기 등록일 millisecond 단위를 자바스크립트의 Date 객체로 변환
      Handlebars.registerHelper("convertDate", function(reviewTime) {

        const dateObj = new Date(reviewTime);
        let year = dateObj.getFullYear();
        let month = dateObj.getMonth() + 1;
        let date = dateObj.getDate();
        let hour = dateObj.getHours();
        let minute = dateObj.getMinutes();

        return year + "/" + month + "/" + date + " " + hour + ":" + minute;
      });

      // 사용자 정의(별평점 숫자를 별로 출력)
      Handlebars.registerHelper("starRating", function(rating) {

        let starStr = "";

        switch(rating) {
          case 1 :
          starStr = "★☆☆☆☆";
          break;
          case 2 :
          starStr = "★★☆☆☆";
          break;
          case 3 :
          starStr = "★★★☆☆";
          break;
          case 4 :
          starStr = "★★★★☆";
          break;
          case 5 :
          starStr = "★★★★★";
          break;
        }
        return starStr;
      });


      // 페이징 작업

      // 상품후기 저장
      $("#btn_review_save").on("click", function() {
        // 별평점 값
        let rew_score = 0;
        let rew_content = $("#rew_content").val();

        $("p#star_rv_score a.rv_score").each(function(index, item) {
          if($(this).attr("class") == "rv_score on") {
            rew_score += 1;
          }
        });

        // 별을 선택하지 않았을 경우
        if(rew_score == 0) {
          alert("별 평점을 선택해주세요");
          return;
        }

        // 후기 유효성검사
        if(rew_content == "") {
          alert("상품평을 작성하세요");
          return;
        }

        // ajax로 리뷰데이터 전송작업
        let review_data = {pro_num : $(this).data("pro_num"), rew_content : rew_content, rew_score : rew_score};

        $.ajax({
          url : '/user/review/new',
          headers: {
            "Content-Type" : "application/json", "X-HTTP-Method-Override" : "POST"
          },
          type : 'post',
          data : JSON.stringify(review_data), // 데이터포맷을 object -> json으로 변환
          dataType : 'text',
          success : function(result) {
            if(result == 'success') {
              alert("상품평이 등록되었습니다.");
              $('#review_modal').modal('hide'); // 부트스트랩 4.6 version 자바스크립트 명령어
              // 상품평 목록을 불러오는 작업
              getReviewInfo(url);
            }
          }
        });
      });


  // ready end 
  }); 
  </script>

  <!-- 상품후기 Modal-->
    <div class="modal fade" id="review_modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">상품후기</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <form>
              <div class="form-group">
                <label for="recipient-name" class="col-form-label">평점 :</label>
                <p id="star_rv_score">
                  <a class="rv_score" href="#">☆</a>
                  <a class="rv_score" href="#">☆</a>
                  <a class="rv_score" href="#">☆</a>
                  <a class="rv_score" href="#">☆</a>
                  <a class="rv_score" href="#">☆</a>
                </p>
              </div>
              <div class="form-group">
                <label for="message-text" class="col-form-label">내용 :</label>
                <textarea class="form-control" id="rew_content"></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
            <button type="button" id="btn_review_save" class="btn btn-primary" data-pro_num="${productVO.pro_num}">작성</button>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>