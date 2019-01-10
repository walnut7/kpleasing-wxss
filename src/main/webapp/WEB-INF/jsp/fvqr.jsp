<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()	+ path + "/";
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>视频面签二维码</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link rel="stylesheet" href="${ctx}/css/weui.min.css">
<link rel="stylesheet" href="${ctx}/css/jquery-weui.min.css">
<link rel="stylesheet" href="${ctx}/css/wxss.css">
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-weui.min.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/wxss.js"></script>
<script type="text/javascript" src="${ctx}/js/lrz.all.bundle.js"></script>
<style type="text/css">
.upload {
    width: 100%;
    height: 180px;
    display: flex;
    padding-top: 15px;
    flex-direction: column;
    align-items: center;
    justify-content: center;
}

.upload_content{
    width: 80%;
    height: 675px;
    background-color: white;
    border-radius: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border: 1px solid #cccccc;
}

#preview {
    display:flex;
    align-items: center;
    justify-content: center;
}

/* .upload_content img{
    width: 100%;
    height: 375px;
    border-radius: 10px;
} */
</style>
</head>
<body ontouchstart>
	<div class="weui-tab">
		<div class="weui-navbar">
			<div class="weui-navbar__item weui-bar__item--on wxss-register_title">
				<div style="width: 64px" class="wxss-register_turnimg"></div>
				<span id="NavTitle">视频面签二维码</span>
				<div style="width: 64px"></div>
			</div>
		</div>
		<div class="weui-tab__bd">
			<div class="weui-tab__bd-item weui-tab__bd-item--active">
				<div class="upload">
					<!-- <div class="upload_content"> -->
						<div id="preview">
							<c:choose>
								<c:when test="${!empty qr}">
									<img id="imghead" border="0" src="${qr}" style="border-radius: 10px;">
								</c:when>
								<c:otherwise>
									<img id="imghead" border="0" src="${ctx}/image/iscard.png" style="width: 65%; height: 100%; border-radius: 0px;">
								</c:otherwise>
							</c:choose>
						</div>
					<!-- </div> -->
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	function getSign() {
		$.ajax({
			type : "POST",
			url : "${ctx}/fv/getSign",
			datatype : "json",
			data : {
				'bizCode' : $("input[name='bizCode']").val(),
				'tm' : $("input[name='tm']").val(),
				'random' : $("input[name='random']").val()
			},
			success : function(data) {
				alert(data);
				return data;
			},
			error : function() {
				alert("error");
			}
	    });
	}

	function nextbtn() {
		var sign = getSign();
	   	$.ajax({
			type : "POST",
			url : "https://console.jianmianqian.com/api",
			datatype : "json",
			data : {
				'userName' : $("input[name='username']").val(),
				'gender' : $("p[class='selectsex']").text(),
				'genderCode' : $("input[name='gender']").val(),
				'nation' : $("input[name='national']").val(),
				'birthday' : $("input[name='birthday']").val(),
				'liveAddr' : $("textarea[name='address']").val(),
				'certId' : $("input[name='cardnumber']").val()
			},
			success : function(data) {
				var obj = eval('(' + data + ')');
	           	if(obj.result=="success") {
	           		window.location.href = "${ctx}/register/certInfoB";
	           	} else {
	               	$.alert(obj.message);
	               }
			},
			error : function() {
				alert("error");
			}
	    });
	}
</script>
</html>