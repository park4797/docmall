<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.101.0">
    <title>Pricing example · Bootstrap v4.6</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.6/examples/pricing/">

    

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
    
	<%@ include file="/WEB-INF/views/comm/header.jsp"%>

<div class="container">
  <div class="text-center">

    <div class="box box-primary">
      <div class="box-header with-border">
        <h3 class="box-title">로그인</h3>
      </div>


      <form role="form" method="post" action="/member/findId">
        <div class="box-body">
          <div class="form-group row">
            <label for="mbsp_name" class="col-2">이름</label>
            <div class="col-10">
              <input type="text" class="form-control" name="mbsp_name" id="mbsp_name" placeholder="이름을 입력">
            </div>
          </div>

          <div class="form-group row">
            <label for="mbsp_email" class="col-2">이메일</label>
            <div class="col-8">
              <input type="email" class="form-control" name="c_mbsp_email" id="c_mbsp_email" placeholder="이메일 입력">
            </div>
            <div class="col-2">
              <button type="button" class="btn btn-outline-info" id="c_mailAuth">메일인증</button>
            </div>
          </div>

          <div class="form-group row">
            <label for="c_authCode" class="col-2">메일인증</label> 
            <div class="col-8">
              <input type="text" class="form-control" name="c_authCode" id="c_authCode" placeholder="인증코드 입력">
            </div>
            <div class="col-2">
              <button type="button" class="btn btn-outline-info" id="c_btnConfirmAuth">인증확인</button>
            </div>
          </div>
        </div>
        
        <div class="box-footer">
          <button type="submit" class="btn btn-primary" id="btn_complete_find">확인</button>
        </div>
      </form>
    </div>

  </div>

  <%@ include file="/WEB-INF/views/comm/footer.jsp"%>
</div>

  <%@ include file="/WEB-INF/views/comm/plugIn.jsp" %>

  <script>
    $(document).ready(function() {
      // 메일인증 요청
      $("#c_mailAuth").click(function() {
      // alert("메일인증 요청");
      if($("#c_mbsp_email").val() == "") {
        alert("이메일을 입력하세요");
        $("#c_mbsp_email").focus();
        return;
      }

        $.ajax({
          url : '/email/authcode',
          dataType : 'text', // 스프링에서 보내는 데이터의 타입. 'success'
          type : 'get',
          data : {receiverMail : $("#c_mbsp_email").val()}, // 파라미터명이 스프링이 보내는 데이터와 일치해야한다.
          success : (result) => {
            if(result == "success") {
              alert("인증메일이 발송되었습니다.");
            }
          }
        })
      });

      // 인증확인

      let isConfirmAuth = false; // 메일인증을 안한 상태

      $("#c_btnConfirmAuth").click(()=> {
        if($("#c_authCode").val() == "") {
          alert("인증코드를 입력하세요");
          $("#c_authCode").focus();
          return;
        }
        
        // 인증확인요청
        $.ajax({
          url : '/email/confirmAuthcode',
          dataType : 'text',
          type : 'get',
          data : {authCode : $("#c_authCode").val()},
          success : (result) => {
            if(result == "success"){
              alert("일치합니다.");
              isConfirmAuth = true;
            } else if(result == "fail") {
              alert("일치하지 않습니다");
              $("#c_authCode").val("");
              $("#c_authCode").focus();
              isConfirmAuth = false;
            } else if(result == "request") {
              alert("세션이 만료되었습니다");
              $("#c_authCode").val("");
              $("#c_authCode").focus();
              isConfirmAuth = false;
            }
              
          }
        })
      })

      let correctName = false;
      // 아이디 일치여부
      $("btn_complete_find").on("click", function() {
        if($("#mbsp_name").val() == "") {
          alert("이름을 입력하세요")
          $("#mbsp_name").focus();
          return;
        }
        $.ajax({

          url : "/member/findId",
          type : 'get',
          dataType : 'text',
          data : {mbsp_name : $("#mbsp_name").val()},
          success : function(result) {

          }
        });
      });

    });

  </script>
</body>
</html>
    