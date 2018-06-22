<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path+"/";
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>迅信通</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0">
<link rel="stylesheet" href="${ctx}/font/iconfont.css">
<link rel="stylesheet" href="${ctx}/css/reset.css">
<script src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/common_util.js"></script>
<script src="${ctx}/js/scrollfix.js"></script>
	<script src="${ctx}/js/fastclick.js"></script>
</head>
<body>
	<div class="flx flx_m bg_white mb6">
		<p  class="logo" />
		<p class="flx_1 fs16 bdr_eee" id="bpName_id">${sp.bpName}</p>
		<a href="tel:021-80179000" class="cur"> <img
			src="${ctx}/image/phone.jpg" alt="" class="phone">
		</a>
	</div>

	<c:forEach items="${spcars}" var="car">
		<a href="${ctx}/carList/carDetail?bpId=${sp.bpId}&bpCode=${sp.bpCode}&modelId=${car.modelId}">
			<div class="flx flx_m bg_white pr10 mb6 btn_active" >
				<div class="imgWrap flx flx_c flx_m">
					<img src="http://leasing.e-autofinance.net:8080/kp_prod/${car.seriesLogo}" alt="" class="block" />
				</div>
				<div class="pl15 flx_1">
					<p class="fs16 bold mb5">${car.brand}</p>
					<p class="fs16 mb3">${car.series}</p>
					<p class="fs16 mb3">${car.model}</p>
					<p>${car.description1}</p>
					<p>${car.description2}</p>
				</div>
				<p>
					<i class="iconfont clr_aaa">&#xe611;</i>
				</p>
			</div>
		</a>
	</c:forEach>

	<div class="flx flx_b bg_white btmWrap ">
		<div class="lh22">
			<p class="btmTit">坤鹏融资租赁（上海）有限公司</p>
			<p>
				官方微信号：<span class="clr_org">迅信通</span>
			</p>
			<p>
				搜索微信号：<span class="clr_org">迅信通</span>
			</p>
			<p>扫一扫我们的二维码</p>
		</div>
		<a href="tel:021-80179000"></a> <img src="${ctx}/image/twoCode.jpg"
			alt="" class="twoCode">
	</div>
	<div class="copyRight">版权所有© 2018 坤鹏融资租赁（上海）有限公司</div>
	

</body>
</html>