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
    <section class="content-header">
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
              <h3 class="box-title">회원목록</h3>
            </div>

            <div class="box-body">
              <table class="table table-bordered">
                <tbody>
                  <tr>
                    <th style="width : 25%">회원ID</th>
                    <th style="width : 25%">회원이름</th>
                    <th style="width : 20%">우편번호</th>
                    <th style="width : 30%">이메일</th>
                  </tr>
                  <!-- jstl 문법작업 -->
                  <!-- forEach문 내부에서는 id를 사용할 수 없다. -->
                  <c:forEach items="${member_list}" var="memberVO"> <!-- var = ProductVO class 성격이 된다. -->
                    <tr>
                      <td>${memberVO.mbsp_id}</td>
                      <td>${memberVO.mbsp_name}</td>
                      <td>${memberVO.mbsp_zipcode}</td>
                      <td>${memberVO.mbsp_email}</td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
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

    
  }); //

</script>
</body>
</html>