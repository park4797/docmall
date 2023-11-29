<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
        <!-- OrderDetailProductVO 클래스의 OrderDetailVO, ProductVO 클래스들이 items에 담긴다-->
        <c:forEach items="${orderProductList}" var="orderProductVO">
            <tr>
                <th scope="row">${orderProductVO.orderDetailVO.ord_code}</th>
                <td>${orderProductVO.productVO.pro_num}</td>
                <td><img src="/admin/order/imageDisplay?dateFolderName=${orderProductVO.productVO.pro_up_folder}&fileName=${orderProductVO.productVO.pro_img}"></td>
                <td>${orderProductVO.productVO.pro_name}</td>
                <td>${orderProductVO.orderDetailVO.dt_amount}</td>
                <td>${orderProductVO.productVO.pro_price * orderProductVO.orderDetailVO.dt_amount}</td>
                <td>
                    <button type="button" name="btn_order_delete" class="btn btn-danger"
                    data-ord_code="${orderProductVO.orderDetailVO.ord_code}" data-pro_num="${orderProductVO.productVO.pro_num}">delete</button>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>