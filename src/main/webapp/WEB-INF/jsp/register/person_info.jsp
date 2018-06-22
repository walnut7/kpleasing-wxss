<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link rel="stylesheet" href="${ctx}/css/weui.min.css">
<link rel="stylesheet" href="${ctx}/css/jquery-weui.min.css">
<link rel="stylesheet" href="${ctx}/css/wxss.css">
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-weui.min.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/wxss.js"></script>
<script type="text/javascript" src="${ctx}/js/lrz.all.bundle.js"></script>
</head>
<body ontouchstart>
	<div class="weui-tab">
		<div class="weui-navbar">
			<div class="weui-navbar__item weui-bar__item--on wxss-register_title">
				<div style="width: 64px" class="wxss-register_turnimg"
					onclick="turnback()">
					<img src="${ctx}/image/turnback.png" alt=""
						class="weui-tabbar__icon">
				</div>
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
							<input class="weui-input" type="text" name="familyPhone"
								placeholder="" value="${person.familyPhone}">
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
							<textarea class="weui-textarea" name="familyAddr"
								placeholder="详细地址" rows="2">${person.familyAddr}</textarea>
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
					<div id="spouse_area" style="display: none;">
						<div class="weui-cells__title">配偶信息</div>
						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">姓名</label>
							</div>
							<div class="weui-cell__bd">
								<input class="weui-input" type="text" name="spouseName"
									placeholder="" value="${person.spouseName}">
							</div>
							<div class="weui-cell__ft">
								<a class="weui-vcode-btn" onclick="$('#previewImg1').click();">自动识别</a>
								<input type="file" onchange="previewImage(this,1)"
									style="display: none;" id="previewImg1">
							</div>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">身份证</label>
							</div>
							<div class="weui-cell__bd">
								<input class="weui-input" type="text" name="spouseCertId"
									placeholder="" value="${person.spouseCertId}">
							</div>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">手机号</label>
							</div>
							<div class="weui-cell__bd">
								<input class="weui-input" type="text" name="spousePhone"
									placeholder="" value="${person.spousePhone}">
							</div>
						</div>
						<div class="weui-cell"
							onclick="javascript:select('51','spouseannualincome','spouseAnnualIncomeCode');">
							<div class="weui-cell__hd">
								<label class="weui-label">月收入</label>
							</div>
							<div class="weui-cell__bd">
								<p class="spouseannualincome">${person.spouseAnnualIncome}</p>
							</div>
						</div>
						<div class="weui-cell"
							onclick="javascript:select('45','spouseincomefrom','spouseIncomeFromCode');">
							<div class="weui-cell__hd">
								<label class="weui-label">收入来源</label>
							</div>
							<div class="weui-cell__bd">
								<p class="spouseincomefrom">${person.spouseIncomeFrom}</p>
							</div>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">工作单位</label>
							</div>
							<div class="weui-cell__bd">
								<input class="weui-input" type="text" name="spouseWorkUnit"
									placeholder="" value="${person.spouseWorkUnit}">
							</div>
						</div>
					</div>

					<div id="contact_area" style="display: none;">
						<div class="weui-cells__title">紧急联系人信息</div>
						<div class="weui-cell"
							onclick="javascript:select('4','contactrelation','contactRelationCode');">
							<div class="weui-cell__hd">
								<label class="weui-label">关系</label>
							</div>
							<div class="weui-cell__bd">
								<p class="contactrelation">${person.contactRelation}</p>
							</div>
						</div>
						<div class="weui-cell weui-cell_vcode">
							<div class="weui-cell__hd">
								<label class="weui-label">姓名</label>
							</div>
							<div class="weui-cell__bd">
								<input class="weui-input" type="text" name="contactName"
									placeholder="" value="${person.contactName}">
							</div>
							<div class="weui-cell__ft">
								<a class="weui-vcode-btn" onclick="$('#previewImg2').click();">自动识别</a>
								<input type="file" onchange="previewImage(this,2)"
									style="display: none;" id="previewImg2">
							</div>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">身份证</label>
							</div>
							<div class="weui-cell__bd">
								<input class="weui-input" type="text" name="contactCertId"
									placeholder="" value="${person.contactCertId}">
							</div>
						</div>
						<div class="weui-cell">
							<div class="weui-cell__hd">
								<label class="weui-label">手机号</label>
							</div>
							<div class="weui-cell__bd">
								<input class="weui-input" type="text" name="contactPhone"
									placeholder="" value="${person.contactPhone}">
							</div>
						</div>
					</div>

					<div style="display: none;">
						<input type="hidden" name="userName" value="${username}" /> <input
							type="hidden" name="liveStatus" value="${person.liveStatusCode}" />
						<input type="hidden" name="eduLevel"
							value="${person.eduLevelCode}" /> <input type="hidden"
							name="marrStatus" value="${person.marrStatusCode}" /> <input
							type="hidden" name="province" value="${person.province}" /> <input
							type="hidden" name="provinceCode" value="" /> <input
							type="hidden" name="city" value="${person.city}" /> <input
							type="hidden" name="cityCode" value="" /> <input type="hidden"
							name="spouseAnnualIncomeCode"
							value="${person.spouseAnnualIncomeCode}" /> <input type="hidden"
							name="spouseIncomeFromCode"
							value="${person.spouseIncomeFromCode}" /> <input type="hidden"
							name="contactRelationCode" value="${person.contactRelationCode}" />
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
		if ($("input[name='marrStatus']").val() != "") {
			if ($("input[name='marrStatus']").val() === "MARRIED") {
				$("#spouse_area").css("display", "block");
				$("#contact_area").css("display", "none");
			} else {
				$("#spouse_area").css("display", "none");
				$("#contact_area").css("display", "block");
			}
		}
	});

	//身份证号合法性验证 	
	function IdentityCodeValid(code) {
		var city = {
			11 : "北京",
			12 : "天津",
			13 : "河北",
			14 : "山西",
			15 : "内蒙古",
			21 : "辽宁",
			22 : "吉林",
			23 : "黑龙江 ",
			31 : "上海",
			32 : "江苏",
			33 : "浙江",
			34 : "安徽",
			35 : "福建",
			36 : "江西",
			37 : "山东",
			41 : "河南",
			42 : "湖北 ",
			43 : "湖南",
			44 : "广东",
			45 : "广西",
			46 : "海南",
			50 : "重庆",
			51 : "四川",
			52 : "贵州",
			53 : "云南",
			54 : "西藏 ",
			61 : "陕西",
			62 : "甘肃",
			63 : "青海",
			64 : "宁夏",
			65 : "新疆",
			71 : "台湾",
			81 : "香港",
			82 : "澳门",
			91 : "国外 "
		};
		var tip = "";
		var pass = true;

		if (!code
				|| !/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/
						.test(code)) {
			tip = "身份证号格式错误";
			pass = false;
		}

		else if (!city[code.substr(0, 2)]) {
			tip = "身份证号地址编码错误";
			pass = false;
		} else if (!checkBirthDayCode(code)) {
			tip = "身份证号生日编码错误";
			pass = false;
		} else {
			//18位身份证需要验证最后一位校验位
			if (code.length == 18) {
				code = code.split('');
				//∑(ai×Wi)(mod 11)
				//加权因子
				var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8,
						4, 2 ];
				//校验位
				var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
				var sum = 0;
				var ai = 0;
				var wi = 0;
				for (var i = 0; i < 17; i++) {
					ai = code[i];
					wi = factor[i];
					sum += ai * wi;
				}
				var last = parity[sum % 11];
				if (parity[sum % 11] != code[17]) {
					tip = "身份证号校验位错误";
					pass = false;
				}
			}
		}
		if (!pass) {
			$.alert(tip);
		}
		return pass;
	}

	/*校验生日编码*/
	function checkBirthDayCode(code) {
		var birDayCode = "";
		if (code.length == 18) {
			birDayCode = code.substring(6, 14);
		} else if (code.length == 15) {
			birDayCode = '19' + code.substring(6, 12);
		}
		var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/
				.test(birDayCode);
		if (!check)
			return false;
		var yyyy = parseInt(birDayCode.substring(0, 4), 10);
		var mm = parseInt(birDayCode.substring(4, 6), 10);
		var dd = parseInt(birDayCode.substring(6), 10);
		var xdata = new Date(yyyy, mm - 1, dd);
		if (xdata > new Date()) {
			return false;//生日不能大于当前日期
		} else {
			return true;
		}
	}

	// 下一步
	function nextbtn() {
		if ($("input[name='userName']").val() === "") {
			$.alert('姓名不能为空');
		} else if ($("input[name='liveStatus']").val() === "") {
			$.alert('住房情况不能为空');
		} else if ($("input[name='eduLevel']").val() === "") {
			$.alert('请选择学历');
		} else if ($("input[name='familyPhone']").val() === "") {
			weui.alert('家庭电话不能为空');
		} else if ($("input[name='familyPhone']").val() == "") {
			$.alert('居住地址不能为空');
		} else if ($("input[name='email']").val() == "") {
			$.alert('邮箱不能为空');
		} else if (!/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/.test($("input[name='email']").val())) {
			$.alert('邮箱格式有误');
		} else if ($("input[name='marrStatus']").val() === "") {
			$.alert('请选择婚姻状况');
		} else if ($("input[name='provinceCode']").val() == "选择省份"
				|| $("input[name='cityCode']").val() === "选择城市") {
			$.alert('请选择省份和城市');
		} else if($("span[class='provincename']").text() == "选择省份"
			|| $("span[class='cityname']").text() == "选择省份" ) {
			$.alert('请选择省份和城市');
		} else if ($("p[class='marrstatus']").text() == "已婚") {
			//配偶身份证号码校验
			if ($("input[name='spouseCertId']").val().trim() === "") {
				$.alert("配偶身份证不能为空");
				return;
			} else {
				var idCardValid = IdentityCodeValid($(
						"input[name='spouseCertId']").val().trim());
				if (!idCardValid) {
					return;
				}
			}

			if ($("input[name='spouseName']").val().trim() == "") {
				$.alert('配偶姓名不能为空');
			}
			/* else if ($("input[name='spouseCertId']").val().trim() === "") {
				$.alert('配偶身份证不能为空');
			} else if (!/\d{18}|\d{15}/.test($("input[name='spouseCertId']").val().trim())) {
				$.alert('配偶身份证格式有误');
			}  */
			else if ($("input[name='spousePhone']").val().trim() === "") {
				$.alert('配偶手机号不能为空');
			} else if (!/^1[3|4|5|7|8][0-9]\d{8}$/.test($(
					"input[name='spousePhone']").val().trim())) {
				$.alert('配偶手机号格式有误');
			} else if ($("input[name='spouseWorkUnit']").val().trim() === "") {
				$.alert('配偶工作单位不能为空');
			} else if ($("input[name='spouseAnnualIncomeCode']").val().trim() === "") {
				$.alert('请选择配偶月收入');
			} else if ($("input[name='spouseIncomeFromCode']").val().trim() === "") {
				$.alert('请选择配偶收入来源');
			} else {
				submitPersonInfo();
			}
		} else if ($("p[class='marrstatus']").text() != "已婚") {
			//联系人身份证号码校验
			if ($("input[name='contactCertId']").val().trim() === "") {
				$.alert("联系人身份证不能为空");
				return;
			} else {
				var idCardValid = IdentityCodeValid($(
						"input[name='contactCertId']").val().trim());
				if (!idCardValid) {
					return;
				}
			}

			if ($("input[name='contactName']").val().trim() == "") {
				$.alert('联系人姓名不能为空');
			}
			/* else if ($("input[name='contactCertId']").val().trim() === "") {
				$.alert('联系人身份证不能为空');
			} else if (!/\d{18}|\d{15}/.test($("input[name='contactCertId']").val().trim())) {
				$.alert('联系人身份证格式有误');
			} */
			else if ($("input[name='contactPhone']").val().trim() === "") {
				$.alert('联系人手机号不能为空');
			} else if (!/^1[3|4|5|7|8][0-9]\d{8}$/.test($(
					"input[name='contactPhone']").val().trim())) {
				$.alert('联系人手机号格式有误');
			} else {
				submitPersonInfo();
			}
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
				'marrStatusCode' : $("input[name='marrStatus']").val(),
				'spouseName' : $("input[name='spouseName']").val(),
				'spouseCertId' : $("input[name='spouseCertId']").val(),
				'spousePhone' : $("input[name='spousePhone']").val(),
				'spouseAnnualIncome' : $("p[class='spouseannualincome']").text(),
				'spouseAnnualIncomeCode' : $("input[name='spouseAnnualIncomeCode']").val(),
				'spouseIncomeFrom' : $("p[class='spouseincomefrom']").text(),
				'spouseIncomeFromCode' : $("input[name='spouseIncomeFromCode']").val(),
				'spouseWorkUnit' : $("input[name='spouseWorkUnit']").val(),
				'contactRelation' : $("p[class='contactrelation']").text(),
				'contactRelationCode' : $("input[name='contactRelationCode']").val(),
				'contactName' : $("input[name='contactName']").val(),
				'contactCertId' : $("input[name='contactCertId']").val(),
				'contactPhone' : $("input[name='contactPhone']").val()
			},
			success : function(data) {
				var obj = eval('(' + data + ')');
				if(obj.result=="success") {
					window.location.href = "${ctx}/register/workInfo";
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
				if ($("input[name='marrStatus']").val() === "MARRIED") {
					$("#spouse_area").css("display", "block");
					$("#contact_area").css("display", "none");
				} else {
					$("#spouse_area").css("display", "none");
					$("#contact_area").css("display", "block");
				}
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

	function turnback() {
		window.location.href = "${ctx}/register/bankInfo";
	}

	// 上传图片
	function previewImage(file, v) {
		$.showLoading();
    	var img_size=file.files[0].size;
		var i_quality=0.9;
		if (img_size>1048576){
			i_quality=1048576/img_size;
		}

    	lrz(file.files[0], { quality: i_quality }).then(function(rst) {
            //img.src = rst.base64;
            //先追加到页面里
            var base64_str = rst.base64;
            var base64string = base64_str.split(",")[1]; 
            $.ajax({
				type : "POST",
				url : "${ctx}/register/uploadImage",
				datatype : "json",
				async : false,
				data : {
					'type' : 'cert',
					'base64string' : base64string
				},
				success : function(data) {
					$.hideLoading();
					jsonObj = eval('(' + data + ')');
					if (v == 1) {
						$("input[name='spouseName']").val(
								jsonObj.userName);
						$("input[name='spouseCertId']").val(
								jsonObj.certId);
					} else if (v == 2) {
						$("input[name='contactName']").val(
								jsonObj.userName);
						$("input[name='contactCertId']").val(
								jsonObj.certId);
					}
				},
				error : function() {
					$.hideLoading();
					$.alert("读取失败，请手工输入！");
				}
			});
    	});	
		 
		/* if (file.files && file.files[0]) {
			var reader = new FileReader();
			reader.onload = function(evt) {
				var id_cart_base64_front = reader.result;
				var base64string = id_cart_base64_front.split(",")[1]
				$.ajax({
							type : "POST",
							url : "${ctx}/register/uploadImage",
							datatype : "json",
							async : false,
							data : {
								'type' : 'cert',
								'base64string' : base64string
							},
							success : function(data) {
								jsonObj = eval('(' + data + ')');
								if (v == 1) {
									$("input[name='spouseName']").val(
											jsonObj.userName);
									$("input[name='spouseCertId']").val(
											jsonObj.certId);
								} else if (v == 2) {
									$("input[name='contactName']").val(
											jsonObj.userName);
									$("input[name='contactCertId']").val(
											jsonObj.certId);
								}
							},
							error : function() {
								$.alert("读取失败，请手工输入！");
							}
						});
			}
			reader.readAsDataURL(file.files[0]);
			reader.onloadstart = function(evt) {
				$.showLoading();
			};
			reader.onloadend = function(evt) {
				$.hideLoading();
			};
			reader.onerror = function(evt) {
				$.alert("图片上传失败，请重试...");
			};
		} */
	}
</script>
</html>