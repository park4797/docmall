<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>

<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | Starter</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
  <%@ include file="/WEB-INF/views/admin/include/plugin1.jsp" %>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
  
  <script id="orderDetailTemplate" type="text/x-handlebars-template">
    <tr class="tr_detail_info">
      <td colspan="9" style="text-align: center;"> <!-- 주문리스트쪽 td가 9개이기 때문 -->
        <table class="table table-sm">
          <caption style="display: table-caption; text-align: center; color: red; font-weight: bold;">[주문상세정보]</caption>
          <thead>
            <tr>
              <th scope="col">주문번호</th>
              <th scope="col">상품코드</th>
              <th scope="col">상품이미지</th>
              <th scope="col">상품명</th>
              <th scope="col">주문수량</th>
              <th scope="col">주문금액</th>
              <th scope="col">비고</th>
            </tr>
          </thead>
          <tbody>
            {{#each .}}
            <tr>
              <th scope="row">{{ord_code}}</th>
              <td>{{pro_num}}</td>
              <td><img src='/admin/order/imageDisplay?dateFolderName={{pro_up_folder}}&fileName={{pro_img}}'></td>
              <td>{{pro_name}}</td>
              <td>{{dt_amount}}</td>
              <td>{{ord_price}}</td>
              <td><button type="button" name="btn_order_delete" class="btn btn-danger" data-ord_code="{{ord_code}}" data-pro_num="{{pro_num}}">delete</button></td>
              <!-- 테이블상 복합키로 설정하여 개별삭제가 가능하게 했다. -->
            </tr>
            {{/each}}
          </tbody>
        </table>
      </td>
    </tr>
    
  </script>

</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
  <%@ include file="/WEB-INF/views/admin/include/header.jsp" %>
  
  <!-- Left side column. contains the logo and sidebar -->
  <%@ include file="/WEB-INF/views/admin/include/nav.jsp" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->

    <!-- Main content -->
    <section class="content container-fluid">

      <div class="row"> <!-- <tr>과 같은 느낌. row 안에는 <td>를 합이 12까지 사용 가능하다. -->
        <div class="col-md-12"> <!-- <tr> 하나에 <td>를 하나만 쓰겠다는 의미 -->
          <div class="box">
            <div class="box-header with-border">
              <h3 class="box-title">Order List</h3>
            </div>

            <div class="box-body">
              <div> <!-- 검색어 작업 -->
                <form action="/admin/order/ord_list" method="get">
                  <select name="type">
                    <option selected>검색종류선택</option>
                    <option value="N" ${pageMaker.cri.type == 'N' ? 'selected' : ''}>주문코드</option>
                    <option value="C" ${pageMaker.cri.type == 'C' ? 'selected' : ''}>주문명</option>
                    <option value="NC" ${pageMaker.cri.type == 'NC' ? 'selected' : ''}>주문명 or 주문코드</option>
                  </select>
                  <input type="text" name="keyword" value="${pageMaker.cri.keyword}" />
                  <!-- 검색한 데이터 출력을 위해 pageNum과 amount 값을 필요로 한다. -->
                  <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}" />
                  <input type="hidden" name="amount" value="${pageMaker.cri.amount}" />
                  <button type="submit" class="btn btn-primary">검색</button>
                </form>
              </div>
              <table class="table table-bordered" id="order_info_tbl">
                <tbody>
                  <tr>
                    <th style="width : 10%">번호</th>
                    <th style="width : 10%">주문일시</th>
                    <th style="width : 10%">주문번호</th>
                    <th style="width : 15%">배송비</th>
                    <th style="width : 15%">주문상태</th>
                    <th style="width : 10%">주문자</th>
                    <th style="width : 10%">총주문액</th>
                    <th style="width : 10%">결제방법</th>
                    <th style="width : 10%">비고</th>
                  </tr>

                  <!-- pro_num, cg_code, pro_name, pro_price, pro_discount, pro_publisher, pro_content, pro_up_folder, pro_img, pro_amount, pro_buy, pro_date, pro_updatedate -->

                  <!-- jstl 문법작업 -->
                  <!-- forEach문 내부에서는 id를 사용할 수 없다. -->
                  <c:forEach items="${order_list}" var="orderVO"> <!-- var = ProductVO class 성격이 된다. -->
                    <tr>
                      <td>상품번호</td>

                      <td>
                        <fmt:formatDate value="${orderVO.ord_regdate}" pattern="yyyy-MM-dd hh:mm:ss" />
                      </td>

                      <td>
                        <span class="btn_order_detail">${orderVO.ord_code}</span>
                      </td>

                      <td>
                        배송비0
                      </td>

                      <td>
                        주문상태
                      </td>

                      <td>
                        ${orderVO.ord_name}
                      </td>

                      <td>
                        ${orderVO.ord_price}
                      </td>

                      <td>
                        ${orderVO.payment_status}
                      </td>

                      <td>
                        <button type="button" class="btn btn-info btn_order_detail1" data-ord_code="${orderVO.ord_code}">주문상세1</button>
                      </td>
                      <td>
                        <button type="button" class="btn btn-info btn_order_detail2" data-ord_code="${orderVO.ord_code}">주문상세2</button>
                      </td>

                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
            <div class="box-footer clearfix">
              <div class="row">
                <div class="col-md-4">
                  <!-- 1) 페이지번호 클릭시 사용 [이전] 1 2 3 4 5 [다음], action="/admin/order/list"-->
                  <!-- 2) 목록에서 제목 클릭시 사용, actionForm.setAttribute("action", "/admin/order/get");-->
                  <form id="actionForm" action="" method="get">
                    <input type="hidden" name="pageNum" id="pageNum" value="${pageMaker.cri.pageNum}" />
                    <input type="hidden" name="amount" id="amount" value="${pageMaker.cri.amount}" />
                    <input type="hidden" name="type" id="type" value="${pageMaker.cri.type}" />
                    <input type="hidden" name="keyword" id="keyword" value="${pageMaker.cri.keyword}" />
                  </form>
                </div>
                <div class="col-md-8 text-center">
                  <nav aria-label="...">
                    <ul class="pagination">
                      <c:if test="${pageMaker.prev}">
                        <li class="page-item">
                          <a href="${pageMaker.startPage - 1}" class="page-link movepage">Previous</a> <!-- page-link movepage를 주어 페이징에 사용한 태그들을 관리한다 -->
                        </li>
                      </c:if>
                      <!--
                      begin과 end 속성은 반복 범위를 정의합니다.
                      pageMaker.startPage와 pageMaker.endPage 의 범위내에서 반복문을 수행한다.
                      num 은 반복되는 페이지 번호를 나타낸다.
                      -->
                      <c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="num">
                        <li class='page-item ${pageMaker.cri.pageNum == num ? "active":"" }'
                          aria-current="page">
                          <a class="page-link movepage" href="${num}" data-page="${num}">${num}</a>
                        </li>
                      </c:forEach>

                      <c:if test="${pageMaker.next}">
                        <li class="page-item">
                          <a href="${pageMaker.endPage + 1}" class="page-link movepage" href="#">Next</a>
                        </li>
                      </c:if>
                    </ul>
                  </nav>
                </div>
                  
              </div>
                
            </div>
              

            </div>
          </div>

        </div>
      </div>

    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->

	<!-- Main Footer -->
  <%@ include file="/WEB-INF/views/admin/include/footer.jsp" %>

  <!-- Control Sidebar -->
  <aside class="control-sidebar control-sidebar-dark">
    <!-- Create the tabs -->
    <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
      <li class="active"><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
      <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
      <!-- Home tab content -->
      <div class="tab-pane active" id="control-sidebar-home-tab">
        <h3 class="control-sidebar-heading">Recent Activity</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:;">
              <i class="menu-icon fa fa-birthday-cake bg-red"></i>

              <div class="menu-info">
                <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

                <p>Will be 23 on April 24th</p>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

        <h3 class="control-sidebar-heading">Tasks Progress</h3>
        <ul class="control-sidebar-menu">
          <li>
            <a href="javascript:;">
              <h4 class="control-sidebar-subheading">
                Custom Template Design
                <span class="pull-right-container">
                    <span class="label label-danger pull-right">70%</span>
                  </span>
              </h4>

              <div class="progress progress-xxs">
                <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
              </div>
            </a>
          </li>
        </ul>
        <!-- /.control-sidebar-menu -->

      </div>
      <!-- /.tab-pane -->
      <!-- Stats tab content -->
      <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div>
      <!-- /.tab-pane -->
      <!-- Settings tab content -->
      <div class="tab-pane" id="control-sidebar-settings-tab">
        <form method="post">
          <h3 class="control-sidebar-heading">General Settings</h3>

          <div class="form-group">
            <label class="control-sidebar-subheading">
              Report panel usage
              <input type="checkbox" class="pull-right" checked>
            </label>

            <p>
              Some information about this general settings option
            </p>
          </div>
          <!-- /.form-group -->
        </form>
      </div>
      <!-- /.tab-pane -->
    </div>
  </aside>
  <!-- /.control-sidebar -->
  <!-- Add the sidebar's background. This div must be placed
  immediately after the control sidebar -->
  <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<%@ include file="/WEB-INF/views/admin/include/plugin2.jsp" %>

<script src="/bower_components/ckeditor/ckeditor.js"></script>
<script>
  // 동적코딩을 할 때 값을 변경하는 작업을 할때는 절차적으로 작업해야 한다.
  $(document).ready(function() {

    let actionForm = $("#actionForm");

    $(".movepage").on("click", function(event) {
      event.preventDefault(); // a태그의 href속성에 페이지번호를 숨겨두었다.

      actionForm.attr("action", "/admin/order/order_list");

      // actionForm 태그를 가지고 있는 하위요소중 input 태그의 name 이 pageNum인 것을 찾는 작업
      actionForm.find("input[name='pageNum']").val($(this).attr("href"));

      actionForm.submit();
    });
  
    // 주문상세 클릭 1 이벤트
    $(".btn_order_detail1").on("click", function() {

      let cur_tr = $(this).parent().parent();
      let ord_code = $(this).data("ord_code");

      console.log("주문코드", ord_code);

      let url ="/admin/order/order_detail_info1/" + ord_code; // 주소를 갖고

      getOrderDetailInfo(url, cur_tr); // 함수를 호출
    });

    function getOrderDetailInfo(url, cur_tr) {
      $.getJSON(url, function(data){
        // data : 주문상세정보

        console.log("상세정보", data[0].ord_code);

        // printOrderDetailList(data.list, $("정적태그 삽입위치"), $("호출할 폼"));
        printOrderDetailList(data, cur_tr, $("#orderDetailTemplate"));

      });
    }

    let printOrderDetailList = function(orderDetailData, target, template) {
      let templateObj = Handlebars.compile(template.html());
      let orderDetailHtml = templateObj(orderDetailData);
      // 완성된 html 코드가 들어온다.

      // 상품후기목록 위치를 참조하여, 추가
      // table 태그에서 추가된 주문상세 tr을 모두 제거
      target.parent().find(".tr_detail_info").remove();

      // 선택된 주문상세 tr이 바로 아래 추가된다.
      target.after(orderDetailHtml); // target을 주문상세 버튼에 존재하는 tr로
    }

    // 주문상세 개별삭제 클릭 이벤트
    $("table#order_info_tbl").on("click", "button[name='btn_order_delete']", function() {
      
      // console.log("개별삭제");

      // 주문상세테이블은 primary key가 2개컬럼을 대상으로 복합키 설정이 되어있다.
      let ord_code = $(this).data("ord_code");
      let pro_num = $(this).data("pro_num");

      if(!confirm("상품코드" + pro_num + " 을 삭제하시겠습니까?")) return;

      // console.log("주문코드", ord_code);
      // console.log("상품코드", pro_num);

      // <input type='hidden' name='ord_code' value="?">
      actionForm.append("<input type='hidden' name='ord_code' value='" + ord_code + "'>");
      actionForm.append("<input type='hidden' name='pro_num' value='" + pro_num + "'>");

      actionForm.attr("action", "/admin/order/order_product_delete");
      actionForm.submit();
    });

    // 주문상세 방법 2 이벤트
    $(".btn_order_detail2").on("click", function() {

    // let cur_tr = $(this).parent().parent();
    let ord_code = $(this).data("ord_code");

    console.log("주문코드", ord_code);

    let url ="/admin/order/order_detail_info2/" + ord_code; // 주소를 가지고

      $("#order_detail_content").load(url); // 주소 요청이 일어나면 <html> 없이도 order_detail_modal 위치에 들어간다.
    // modal() : 부트스트랩 4.6에서 지원하는  메소드
      $("#order_detail_modal").modal('show');
    });
    
  }); //

</script>

  <div class="modal fade" id="order_detail_modal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable modal-lg">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true"></span>
          </button>
          <div class="modal-body" id="order_detail_content">
          
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button type="button" class="btn btn-primary">Save changes</button>
        </div>
      </div>
    </div>
  </div>

</body>
</html>