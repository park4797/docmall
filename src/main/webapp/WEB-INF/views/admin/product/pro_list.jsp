<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>AdminLTE 2 | Starter</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  
  <%@ include file="/WEB-INF/views/admin/include/plugin1.jsp" %>
  
</head>
<!--
BODY TAG OPTIONS:
=================
Apply one or more of the following classes to get the
desired effect
|---------------------------------------------------------|
| SKINS         | skin-blue                               |
|               | skin-black                              |
|               | skin-purple                             |
|               | skin-yellow                             |
|               | skin-red                                |
|               | skin-green                              |
|---------------------------------------------------------|
|LAYOUT OPTIONS | fixed                                   |
|               | layout-boxed                            |
|               | layout-top-nav                          |
|               | sidebar-collapse                        |
|               | sidebar-mini                            |
|---------------------------------------------------------|
-->
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <!-- Main Header -->
  <%@ include file="/WEB-INF/views/admin/include/header.jsp" %>
  
  <!-- Left side column. contains the logo and sidebar -->
  <%@ include file="/WEB-INF/views/admin/include/nav.jsp" %>

  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>한글</h1>
      <ol class="breadcrumb">
        <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
        <li class="active">Here</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content container-fluid">

      <div class="row"> <!-- <tr>과 같은 느낌. row 안에는 <td>를 합이 12까지 사용 가능하다. -->
        <div class="col-md-12"> <!-- <tr> 하나에 <td>를 하나만 쓰겠다는 의미 -->
          <div class="box">
            <div class="box-header with-border">
              <h3 class="box-title">Product List</h3>
            </div>

            <div class="box-body">
              <table class="table table-bordered">
                <tbody>
                  <tr>
                    <th style="width : 2%"><input type="checkbox" id="checkAll"></th>
                    <th style="width : 8%">상품코드</th>
                    <th style="width : 25%">상품명</th>
                    <th style="width : 10%">상품가격</th>
                    <th style="width : 20%">상품등록일</th>
                    <th style="width : 15%">판매여부</th>
                    <th style="width : 10%">수정</th>
                    <th style="width : 10%">삭제</th>
                  </tr>

                  <!-- pro_num, cg_code, pro_name, pro_price, pro_discount, pro_publisher, pro_content, pro_up_folder, pro_img, pro_amount, pro_buy, pro_date, pro_updatedate -->

                  <!-- jstl 문법작업 -->
                  <c:forEach items="${pro_list}" var="productVO"> <!-- var = ProductVO class 성격이 된다. -->
                    <tr>
                      <td><input type="checkbox"></td>
                      <td>${productVO.pro_num} </td>
                      <td>
                          <a class="move" href="#" data-bno="${productVO.pro_num}"><img src="">${productVO.pro_up_folder}${productVO.pro_img}</a>
                          <a class="move" href="#" data-bno="${productVO.pro_num}">${productVO.pro_name}</a>
                      </td>
                      <td>${productVO.pro_price}</td>
                      <td><fmt:formatDate value="${productVO.pro_date}" pattern="yyyy-MM-dd" /></td>
                      <td>${productVO.pro_buy}</td>
                      <td><button type="button" class="btn btn-link">수정</button></td>
                      <td><button type="button" class="btn btn-danger">삭제</button></td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>

            <div class="box-footer clearfix">
              <div class="row">
                <div class="col-6">
                  <nav aria-label="...">
                    <ul class="pagination">

                      <c:if test="${pageMaker.prev}">
                        <li class="page-item">
                          <a href="/board/list?pageNum=${pageMaker.startPage - 1}" class="page-link">Previous</a>
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
                          <a class="page-link movepage" href="#" data-page="${num}">${num}</a>
                        </li>
                      </c:forEach>

                      <c:if test="${pageMaker.next}">
                        <li class="page-item">
                          <a href="/board/list?pageNum=${pageMaker.endPage + 1}" class="page-link" href="#">Next</a>
                        </li>
                      </c:if>
                    </ul>
                  </nav>
                </div>
                <div class="col-6">
                  <form action="/board/list" method="get">
                    <select name="type">
                      <option selected>검색종류선택</option>
                      <option value="T" ${pageMaker.cri.type == 'T' ? 'selected' : ''}>제목</option>
                      <option value="C" ${pageMaker.cri.type == 'C' ? 'selected' : ''}>내용</option>
                      <option value="W" ${pageMaker.cri.type == 'W' ? 'selected' : ''}>작성자</option>
                      <option value="TC" ${pageMaker.cri.type == 'TC' ? 'selected' : ''}>제목 or 내용</option>
                      <option value="TW" ${pageMaker.cri.type == 'TW' ? 'selected' : ''}>제목 or 작성자</option>
                      <option value="TWC" ${pageMaker.cri.type == 'TWC' ? 'selected' : ''}>제목 or 작성자 or 내용</option>
                    </select>
                    <input type="text" name="keyword" value="${pageMaker.cri.keyword}" />
                    <input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}" />
                    <input type="hidden" name="amount" value="${pageMaker.cri.amount}" />
                    <button type="submit" class="btn btn-primary">검색</button>
                  </form>
                  <!-- 1) 페이지번호 클릭시 사용 [이전] 1 2 3 4 5 [다음], action="/board/list"-->
                  <!-- 2) 목록에서 제목 클릭시 사용, actionForm.setAttribute("action", "/board/get");-->
                  <form id="actionForm" action="/board/list" method="get">
                    <input type="hidden" name="pageNum" id="pageNum" value="${pageMaker.cri.pageNum}" />
                    <input type="hidden" name="amount" id="amount" value="${pageMaker.cri.amount}" />
                    <input type="hidden" name="type" id="type" value="${pageMaker.cri.type}" />
                    <input type="hidden" name="keyword" id="keyword" value="${pageMaker.cri.keyword}" />
                    <input type="hidden" name="bno" id="bno" />
                  </form>
                </div>
              </div>
              <a class="btn btn-primary" href="/board/register" role="button">글쓰기</a>
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
  $(document).ready(function() {
    // ckeditor 환경설정. 자바스크립트 Ojbect문법
    var ckeditor_config = {
        resize_enabled : false,
        enterMode : CKEDITOR.ENTER_BR,
        shiftEnterMode : CKEDITOR.ENTER_P,
        toolbarCanCollapse : true,
        removePlugins : "elementspath", 
        
        //업로드 탭기능추가 속성. CKEditor에서 파일업로드해서 서버로 전송클릭하면 , 이 주소가 동작된다.
        filebrowserUploadUrl: '/admin/product/imageUpload' 
      }

      CKEDITOR.replace("pro_content", ckeditor_config);

      console.log("ckeditor 버전 : ", CKEDITOR.version);

      // 1차카테고리 선택했을 때
      // document.getElementById("firstCategory") 와 동일한 맥락
      $("#firstCategory").change(function() {

        // $(this) : option태그중 선택한 option의 내용
        let cg_parent_code = $(this).val();

        // console.log("1차카테고리 코드", cg_parent_code);

        // 1차카테고리 선택에 의한 2차카테고리 정보가져오는 url
        let url = "/admin/category/secondCategory/" + cg_parent_code + ".json";

        // $.getJSON() : 스프링에 요청시 데이터를 json으로 받는 기능. ajax기능 제공
        // 익명함수의 매개변수에는 위 url에 들어오는 정보가 들어온다.
        $.getJSON(url, function(secondCategoryList) {
          // console.log("2차카테고리 정보", secondCategoryList)
          // console.log("2차카테고리 개수", secondCategoryList.length + 1);

          // 2차카테고리 참조
          let secondCategory = $("#secondCategory");
          let optionStr = "";
          // <option value='10'>바지</option>

          // find("css 선택자") : 태그명, id 속성이름, class 속성이름
          secondCategory.find("option").remove(); // 2차카테고리의 option 제거(계속 추가되는것을 막기위함, 다시 선택시 기존것을 제거)
          secondCategory.append("<option value=''>2차 카테고리 선택</option>");

          for(let i=0; i<secondCategoryList.length; i++) {
            optionStr += "<option value='" + secondCategoryList[i].cg_code + "'>" + secondCategoryList[i].cg_name + "</option>";
          }

          // console.log(optionStr);
          secondCategory.append(optionStr); // 2차카테고리 <option>태그들이 추가된다.

        })
      });
        // 파일첨부시 이미지 미리보기
        // 파일첨부에 따른 이벤트관련 정보가 event 매개변수를 통해 참조한다.
        $("#uploadFile").change(function(event) {
          // 선택한 파일
          let file = event.target.files[0]; // 선택한 파일중 첫번째 파일을 가리킨다.

          let reader = new FileReader(); // 첨부된 파일을 이용하여, File 객체를 생성하는 용도
          reader.readAsDataURL(file); // reader 객체에 file 객체가(정보가) 할당된다.

          reader.onload = function(event) {
            // <img id="img_preview" style="width:200px; height:200px;">
            // 위 코드는 src가 없는 상태로 아래코드로 src 속성을 추가
            // event.target.result : reader 객체의 이미지파일정보
            $("#img_preview").attr("src", event.target.result);
          }
        });
  });
</script>
</body>
</html>