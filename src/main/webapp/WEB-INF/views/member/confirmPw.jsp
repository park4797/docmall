<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.101.0">
    <title>정보 수정용 비밀번호 확인 페이지</title>

    <!-- Bootstrap core CSS -->
  	<%@ include file="/WEB-INF/views/comm/plugIn2.jsp" %>

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
      let msg = '${msg}';
      if(msg !=="") {
        alert(msg);
      }
    </script>
  </head>
  <body>
    
<%@include file="/WEB-INF/views/comm/header.jsp" %>
<div class="container">
  <div class="text-left">
    <div class="box box-primary">
      <div class="box-header with-border">
  
      <h3 style="text-align: center;">회원수정 인증확인</h3>
        <form role="form" id="confirmPwForm" method="post" action="/member/confirmPw">
        <div class="box-body">
          <div class="form-group row">
            <label for="mbsp_id" class="col-2">아이디</label>
              <div class="col-10">
                <input type="text" class="form-control" name="mbsp_id" id="mbsp_id" placeholder="아이디 입력">
              </div>
          </div>
          <div class="form-group row">
            <label for="mbsp_id" class="col-2">비밀번호</label>
              <div class="col-10">
                <input type="password" class="form-control" name="mbsp_password" id="mbsp_password" placeholder="비밀번호 입력">
              </div>
          </div>
          <div class="box-footer">
            <button type="submit" class="btn btn-primary" id="loginForm">인증하기</button>

          </div>
        </div>
        </form>
      </div>
    </div>
  </div>

<%@include file="/WEB-INF/views/comm/footer.jsp" %>

</div>
  </body>
</html>