<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@include file="./common/taglib.jsp"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="images1/favicon.ico" type="image/x-icon" />
	<title>美创版本管理系统v1.4</title>
	<script type="text/javascript" src="<c:url value="/js/zTree3/js/jquery-1.4.4.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/js/jquery/jquery-impromptu.2.5.min.js"/>"></script>
	<link href="<c:url value='/css/public.css'/>" rel="stylesheet" type="text/css"></link>
	<link href="css/jquery/impromptu.css" rel="stylesheet" type="text/css"/>
	<link href="css/skin1/common.css" type="text/css" rel="stylesheet" />
	<link href="css/jquery/wiard.css" rel="stylesheet" type="text/css"/>
	<%@include file="./common/skin1/include.jsp"%>
<script language="javascript">
function login(){
    var username=$("#j_username").val();
	var password=$("#j_password").val();
	if(username==""){
		myAlert("用户名不能为空，请核对!");
		document.getElementById("j_username").focus();
		return false;
	}
	else if(password==""){
		myAlert("密码不能为空，请核对!");
		document.getElementById("j_password").focus();
		return false;
	}else {
		$.ajax({
			type : "post",
			url : "<c:url value='login.action'/>",
			data : {
				username : username,
				password : password
			},
			dataType : "text",
			success : function(data) {
				if(data=="true"){
					 window.location.href="<c:url value='/version_main.do'/>"; 
				}else{
					myAlert("用户名密码错误！");
				}
			}
		})
	}

}
</script>
</head>

<body class="login-background">
	<div id="login-box">
		<div id="login-head">
		     
		</div>
	    <div id="login-main1">
		        <form id="form1" action="login.action" method="post">
		                <input type="hidden" name="hid" id="hid">
						    <div class="login-input" style="display:block"><input id="j_username" name="username" type="text" class="login-input" /></div>
		                    <div class="login-pass"><input type="password" id="j_password" name="password" class="login-input" onkeydown="enterIn(event)"/></div>
		                    <div class="login-button"><a href="#"><img src="images1/login1_08.jpg" width="70" height="70" onclick="return login();"/></a></div>
						</div>
				</form>
	    </div>
	    <div id="login-foot">
	         <div style="text-align:center;color:#4e86b0;">VERSION MANAGE SYSTEM v1.4	杭州美创科技有限公司	 版权所有	©<br />	推荐IE8浏览器,1024*768或以上分辨率</div>
	    </div>
    </div>
</body>
</html>
