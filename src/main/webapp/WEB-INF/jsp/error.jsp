<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>迅信通</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link rel="stylesheet" href="${ctx}/css/weui.min.css">
<link rel="stylesheet" href="${ctx}/css/jquery-weui.min.css">
<link rel="stylesheet" href="${ctx}/css/wxss.css">
<link rel="stylesheet" href="${ctx}/css/login.css">
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-weui.min.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/wxss.js"></script>
</head>
<body ontouchstart>
	<div class="weui-msg">
		<div class="weui-msg__icon-area">
			<i class="weui-icon-warn weui-icon_msg"></i>
		</div>
		<div class="weui-msg__text-area">
			<h2 class="weui-msg__title">系统错误</h2>
			<p class="weui-msg__desc">
				${error_desc} <a href="javascript:void(0);">文字链接</a>
			</p>
			<p class="weui-msg__desc">
				${error_desc} <a href="javascript:void(0);">文字链接</a>
			</p>
			<p class="weui-msg__desc">
				${error_desc} <a href="javascript:void(0);">文字链接</a>
			</p>
		</div>
		<div class="weui-msg__opr-area">
			<div class="footer_btn">
		        <div class="footer_btn_div" >返回</div>
		    </div>
			<!-- <p class="weui-btn-area">
				<a href="javascript:;" class="weui-btn weui-btn_primary">返回</a> 
			</p> -->
		</div>
		<div class="weui-msg__extra-area">
			<div class="weui-footer">
				<p class="weui-footer__text">Copyright © 2016-2018 坤鹏融资租赁（上海）有限公司</p>
			</div>
		</div>
	</div>
</body>
</html>