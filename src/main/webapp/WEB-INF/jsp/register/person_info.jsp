<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-weui.min.js"></script>
<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/wxss.js"></script>
<script type="text/javascript" src="${ctx}/js/lrz.all.bundle.js"></script>
</head>
<body ontouchstart>
	<div class="weui-tab">
		<div class="weui-navbar">
			<div class="weui-navbar__item weui-bar__item--on wxss-register_title">
				<div style="width: 64px" class="wxss-register_turnimg"></div>
				<span id="NavTitle">个人信息</span>
				<div style="width: 64px"></div>
			</div>
		</div>
		<div class="weui-tab__bd">
			<div class="weui-tab__bd-item weui-tab__bd-item--active">
				<div class="weui-cells weui-cells_form">
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">姓名</label>
						</div>
						<div class="weui-cell__bd">
							<p class="username">${username}</p>
						</div>
					</div>
					<div class="weui-cell"
						onclick="javascript:select('62','livestatus','liveStatus');">
						<div class="weui-cell__hd">
							<label class="weui-label">住房情况</label>
						</div>
						<div class="weui-cell__bd">
							<p class="livestatus">${person.liveStatus}</p>
						</div>
					</div>
					<div class="weui-cell"
						onclick="javascript:select('34','edulevel','eduLevel');">
						<div class="weui-cell__hd">
							<label class="weui-label">学历</label>
						</div>
						<div class="weui-cell__bd">
							<p class="edulevel">${person.eduLevel}</p>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">家庭电话</label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input" type="number" pattern="\d*" name="familyPhone" placeholder="" value="${person.familyPhone}">
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">居住地址</label>
						</div>
						<div class="weui-cell__bd">
							<span class="provincename"
								onclick="javascript:selectProvince('provincename','provinceCode')">
								<c:choose>
									<c:when test="${!empty person.province}">${person.province}</c:when>
									<c:otherwise>选择省份</c:otherwise>
								</c:choose>
							</span> &nbsp;&nbsp;&nbsp; <span class="cityname"
								onclick="javascript:selectCity('cityname','cityCode')"> <c:choose>
									<c:when test="${!empty person.city}">${person.city}</c:when>
									<c:otherwise>选择城市</c:otherwise>
								</c:choose>
							</span> <br>
							<textarea class="weui-textarea" name="familyAddr" placeholder="详细地址" rows="2">${person.familyAddr}</textarea>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">邮箱</label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input" type="text" name="email"
								placeholder="此邮箱将用于还款计划发送" value="${person.email}">
						</div>
					</div>
					<div class="weui-cell"
						onclick="javascript:selectMarrStatus('39','marrstatus','marrStatus');">
						<div class="weui-cell__hd">
							<label class="weui-label">婚姻状况</label>
						</div>
						<div class="weui-cell__bd">
							<p class="marrstatus">${person.marrStatus}</p>
						</div>
					</div>									
					<div style="display: none;">
						<input type="hidden" name="userName" value="${username}" /> 
						<input type="hidden" name="liveStatus" value="${person.liveStatusCode}" />
						<input type="hidden" name="eduLevel" value="${person.eduLevelCode}" /> 
						<input type="hidden" name="province" value="${person.province}" /> 
						<input type="hidden" name="provinceCode" value="" /> 
						<input type="hidden" name="city" value="${person.city}" /> 
						<input type="hidden" name="cityCode" value="" /> 
						<input type="hidden" name="marrStatus" value="${person.marrStatusCode}" /> 
					</div>
				</div>
				<div class="wxss-register_btn">
					<div class="wxss-register_btn_div" onclick="nextbtn();">下一步</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$(document).ajaxStart(function() {	$.showLoading();}).ajaxStop(function() { $.hideLoading(); });
	});

	// 下一步
	function nextbtn() {
		if ($("input[name='userName']").val() === "") {
			$.alert('姓名不能为空');
		} else if ($("input[name='liveStatus']").val() === "") {
			$.alert('住房情况不能为空');
		} else if ($("input[name='eduLevel']").val() === "") {
			$.alert('请选择学历');
		} else if ($("input[name='familyPhone']").val() === "") {
			$.alert('家庭电话不能为空');
		} else if($("span[class='provincename']").text().replace(/[\s\r\n]/g, "") == "选择省份"
			|| $("span[class='cityname']").text().replace(/[\s\r\n]/g, "") == "选择城市" ) {
			$.alert('请选择省份和城市');
		} else if ($("textarea[name='familyAddr']").val() === "") {
			$.alert('居住地址不能为空');
		} else if ($("input[name='email']").val() === "") {
			$.alert('邮箱不能为空');
		} else if (!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test($("input[name='email']").val())) {
			$.alert('邮箱格式有误');
		} else if ($("input[name='marrStatus']").val() === "") {
			$.alert('请选择婚姻状况');
		} else {
			submitPersonInfo();
		}
	}

	function submitPersonInfo() {
		$.ajax({
			type : "POST",
			url : "${ctx}/register/savePersonInfo",
			datatype : "json",
			data : {
				'userName' : $("input[name='userName']").val(),
				'liveStatus' : $("p[class='livestatus']").text(),
				'liveStatusCode' : $("input[name='liveStatus']").val(),
				'eduLevel' : $("p[class='edulevel']").text(),
				'eduLevelCode' : $("input[name='eduLevel']").val(),
				'familyPhone' : $("input[name='familyPhone']").val(),
				'province' : $("input[name='province']").val(),
				'city' : $("input[name='city']").val(),
				'familyAddr' : $("textarea[name='familyAddr']").val(),
				'email' : $("input[name='email']").val(),
				'marrStatus' : $("p[class='marrstatus']").text(),
				'marrStatusCode' : $("input[name='marrStatus']").val()
			},
			success : function(data) {
				var obj = eval('(' + data + ')');
				if(obj.result=="success") {
					window.location.href = "${ctx}/register/contactInfo";
				} else {
					$.alert(obj.message);
				}
			},
			error : function() {
				alert("error");
			}
		});
	}

	function selectMarrStatus(category, showName, hideValue) {
		var ajaxJsonParam = getJsonObj(category);
		weui.picker(ajaxJsonParam, {
			className : 'custom-classname',
			container : 'body',
			onConfirm : function(result) {
				$("p[class='" + showName + "']").text(result[0].label);
				$("input[name='" + hideValue + "']").val(result[0].value);
			},
			id : 'singleLinePicker'
		});
	}

	function selectCity(showName, hideValue) {
		var provinceCode = $("input[name='provinceCode']").val();
		if (provinceCode.trim() == "") {
			alert("请先选择省份！");
			return;
		}

		var ajaxJsonCity = getJsonCity(provinceCode);
		weui.picker(ajaxJsonCity, {
			className : 'custom-classname',
			container : 'body',
			onConfirm : function(result) {
				$("span[class='" + showName + "']").text(result[0].label);
				$("input[name='" + hideValue + "']").val(result[0].value);
				$("input[name='city']").val(result[0].label);
			},
			id : 'singleLinePicker'
		});
	}

	function getJsonCity(provinceCode) {
		var rtnJsonObj;
		$.ajax({
			type : "POST",
			url : "${ctx}/register/getSelectCity",
			datatype : "json",
			async : false,
			data : {
				"provinceCode" : provinceCode
			},
			success : function(data) {
				rtnJsonObj = eval('(' + data + ')');
			},
			error : function() {
				alert("error");
			}
		});
		return rtnJsonObj;
	}

	function selectProvince(showName, hideValue) {
		var ajaxJsonProvince = getJsonProvince();
		weui.picker(ajaxJsonProvince, {
			className : 'custom-classname',
			container : 'body',
			onConfirm : function(result) {
				$("span[class='" + showName + "']").text(result[0].label);
				$("input[name='" + hideValue + "']").val(result[0].value);
				$("input[name='province']").val(result[0].label);
				$("span[class='cityname']").text("选择城市");
				$("input[name='cityCode']").val("");
			},
			id : 'singleLinePicker'
		});
	}

	function getJsonProvince() {
		var rtnJsonObj;
		$.ajax({
			type : "POST",
			url : "${ctx}/register/getSelectProvince",
			datatype : "json",
			async : false,
			data : {},
			success : function(data) {
				rtnJsonObj = eval('(' + data + ')');
			},
			error : function() {
				alert("error");
			}
		});
		return rtnJsonObj;
	}
</script>
</html>