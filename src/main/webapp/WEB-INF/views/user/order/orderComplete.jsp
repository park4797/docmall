<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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

<div class="container">
  <div class="box box-primary">
    <div class="box-body">
      <h3>주문이 완료되었습니다.</h3>
    </div>
  </div>
</div>

<%@include file="/WEB-INF/views/comm/footer.jsp" %>

</div>

  <%@include file="/WEB-INF/views/comm/plugIn.jsp" %>
  
  </body>
</html>