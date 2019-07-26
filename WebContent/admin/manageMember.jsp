<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>전체 회원 목록</h1>
	
	<table border='1'>
	<tr>
		<th>사용자 이미지</th>
		<th>사용자 아이디</th>
		<th>포인트</th>
	</tr>
	<c:forEach items="${requestScope.members}" var="member">
	<tr>
	<td><a href='admin/manageMember.jsp?uid=${member.userid}'><img src="${member.userimg}"/></a></td>
		<td>${member.userid}</td>
		<td>${member.point}</td>
	</tr>
	</c:forEach>
	</table>
	${requestScope.members }
</body>
</html>