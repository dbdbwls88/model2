<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<link href="../css/bootstrap.min.css" type="text/css" rel="stylesheet">
<script src="../js/bootstrap.min.js"></script>
<script src="../js/jquery.min.js"></script>
<script>
function check(){
	//회원가입시 수행하는 유효성 검사
	
	var result = false;
	var uid =$('#uid').val();
	var pwd1 =$('#pwd1').val();
	var pwd2 =$('#pwd2').val();
	if (uid.length < 6 || uid.length > 10){
		$('#errorUID').html('아이디는 6~10자로 기입하세요..');
		return result;
	}
	if(pwd1.length < 6 || pwd1.length > 12){
		$('#errorPWD').html('비밀번호는 6~12자리로 기입하세요..');
		return result;
	}
	
	if(pwd1 !== pwd2){
		$('#errorPWD2').html('비밀번호가 불일치 합니다..');
		$('pwd1').focus();
		return result;
	}
}

$(document).ready(function() {
	$('#uid').keyup(function() { //아이디 텍스트박스에 키가 눌러졌을때
		var uid = $('#uid').val();
	    $.ajax({//서블릿에 전송하여 아이디가 중복인지 아닌지 결과를 가져와야한다.(백엔드 와의 비동기 통신)
	          type : "GET",
	          url : "../controll.do?uid=" + uid,
	          dataType : "json", //서버에서 반환되는 데이터 타입
	          success : function(data){
	             console.log(data);
	             if(data)
	          },
	          error : function(res) {
	             console.log(res.responseText);
	          },
	          complete : function() {
	                         
	          },
	       });
	});
 });
</script>
<stlye>
	.error{
	color :red;
	}
</stlye>
</head>
<body>

<nav class="navbar navbar-expand-sm bg-dark navbar-dark">
  <!-- Brand/logo -->
  <a class="navbar-brand" href="../index.jsp">Home</a>
  
  <!-- Links -->
  <ul class="navbar-nav">
    <li class="nav-item">
      <a class="nav-link" href="#">회원가입</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">로그인</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">게시판</a>
    </li>
  </ul>
</nav>
<div class="container-fluid">

  <h3>회원가입 페이지</h3>
  
   <form action="../control.do" class="was-validated" methd="post" onsubmit="return check();" 
   enctype="multipart">
   
    <div class="form-group">
      <label for="uid">아이디:</label>
      <input type="text" class="form-control" id="uid" placeholder="아이디는 6~10자로 기입하세요.." name="uid" required>
       <!--  <div class="valid-feedback"></div>
      <div class="invalid-feedback">Please fill out this field.</div>-->
      <div id="errorUID" class="error"></div>
    </div>
    
    <div class="form-group">
      <label for="pwd1">비밀번호:</label>
     <input type="password" class="form-control" id="pwd1" placeholder="비밀번호는 6~12자리로 기입하세요.." name="pwd1" required>
     <div class="valid-feedback">Valid.</div>
      <div class="invalid-feedback">Please fill out this field.</div>
      <div id="errorPWD" class="error"></div>
    </div>
    
    <div class="form-group">
      <label for="pwd2">비밀번호 확인:</label>
     <input type="password" class="form-control" id="pwd2" placeholder="비밀번호 홛인.." name="pwd2">
      <div id="errorPWD2" class="error"></div>
    </div>
    
    <div class="form-group">
      <label for="pwd2">회원 이미지:</label>
     <input type="file" class="form-control" id="userImg" name="userImg">
      <div id="errorPWD2" class="error"></div>
    </div>
    
    <div class="form-group form-check">
      <label class="form-check-label">
        <input class="form-check-input" type="checkbox" name="remember" required> 회원가입 조항에 동의합니다
        <div class="valid-feedback">Valid.</div>
        <div class="invalid-feedback">Check this checkbox to continue.</div>
      </label>
    </div>
    
    <button type="reset" class="btn btn-danger">취소</button>
    <button type="submit" class="btn btn-dark">가입</button>

  </form>
</div>
</body>
</html>