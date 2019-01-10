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
				<div style="width: 64px" class="wxss-register_turnimg"
					onclick="turnback()">
					<img src="${ctx}/image/turnback.png" alt=""
						class="weui-tabbar__icon">
				</div>
				<span id="NavTitle">联系人信息</span>
				<div style="width: 64px"></div>
			</div>
		</div>
		<div class="weui-tab__bd">
			<div class="weui-tab__bd-item weui-tab__bd-item--active">
				<div class="weui-cells weui-cells_form">
					<c:choose>
						<c:when test="${!empty marrycode and  marrycode eq 'MARRIED'}">
							<div class="weui-cells__title">配偶信息（一）</div>
							<div class="weui-cell weui-cell_vcode">
								<div class="weui-cell__hd">
									<label class="weui-label">姓名</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" type="text" name="spouseName"	placeholder="" value="${contact.spouseName}">
								</div>
								<div class="weui-cell__ft">
									<a class="weui-vcode-btn" onclick="$('#previewImg1').click();">自动识别</a>
									<input type="file" onchange="previewImage(this,1)" style="display: none;" id="previewImg1">
								</div>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">身份证号</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" type="text" name="spouseCertId" placeholder="" value="${contact.spouseCertId}">
								</div>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">手机号</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" name="spousePhone" type="number" pattern="\d*" placeholder="" value="${contact.spousePhone}">
								</div>
							</div>
							<div class="weui-cell" onclick="javascript:select('51','spouseannualincome','spouseAnnualIncomeCode');">
								<div class="weui-cell__hd">
									<label class="weui-label">月收入</label>
								</div>
								<div class="weui-cell__bd">
									<p class="spouseannualincome">${contact.spouseAnnualIncome}</p>
								</div>
							</div>
							<div class="weui-cell" onclick="javascript:select('45','spouseincomefrom','spouseIncomeFromCode');">
								<div class="weui-cell__hd">
									<label class="weui-label">收入来源</label>
								</div>
								<div class="weui-cell__bd">
									<p class="spouseincomefrom">${contact.spouseIncomeFrom}</p>
								</div>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">工作单位</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" type="text" name="spouseWorkUnit"	placeholder="" value="${contact.spouseWorkUnit}">
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="weui-cells__title">紧急联系人（一）</div>
							<div class="weui-cell" onclick="javascript:select('4','contactrelation','contactRelationCode');">
								<div class="weui-cell__hd">
									<label class="weui-label">关系</label>
								</div>
								<div class="weui-cell__bd">
									<p class="contactrelation">${contact.contactRelation}</p>
								</div>
							</div>
							<div class="weui-cell weui-cell_vcode">
								<div class="weui-cell__hd">
									<label class="weui-label">姓名</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" type="text" name="contactName" placeholder="" value="${contact.contactName}">
								</div>
								<div class="weui-cell__ft">
									<a class="weui-vcode-btn" onclick="$('#previewImg2').click();">自动识别</a>
									<input type="file" onchange="previewImage(this,2)" style="display: none;" id="previewImg2">
								</div>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">身份证号</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" type="text" name="contactCertId" placeholder="" value="${contact.contactCertId}">
								</div>
							</div>
							<div class="weui-cell">
								<div class="weui-cell__hd">
									<label class="weui-label">手机号</label>
								</div>
								<div class="weui-cell__bd">
									<input class="weui-input" name="contactPhone" type="number" pattern="\d*" placeholder="" value="${contact.contactPhone}">
								</div>
							</div>
						</c:otherwise>
					</c:choose>
					<div class="weui-cells__title">紧急联系人（二）</div>
					<div class="weui-cell" onclick="javascript:select('4','contact2relation','contact2RelationCode');">
						<div class="weui-cell__hd">
							<label class="weui-label">关系</label>
						</div>
						<div class="weui-cell__bd">
							<p class="contact2relation">${contact.contact2Relation}</p>
						</div>
					</div>
					<div class="weui-cell weui-cell_vcode">
						<div class="weui-cell__hd">
							<label class="weui-label">姓名</label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input" type="text" name="contact2Name" placeholder="" value="${contact.contact2Name}">
						</div>
						<div class="weui-cell__ft">
							<a class="weui-vcode-btn" onclick="$('#previewImg3').click();">自动识别</a>
							<input type="file" onchange="previewImage(this,3)" style="display: none;" id="previewImg3">
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">身份证号</label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input" type="text" name="contact2CertId" placeholder="" value="${contact.contact2CertId}">
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">手机号</label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input" name="contact2Phone" type="number" pattern="\d*" placeholder="" value="${contact.contact2Phone}">
						</div>
					</div>
					<div style="display: none;">
						<input type="hidden" name="marrStatus" value="${marrycode}" />
						<input type="hidden" name="spouseAnnualIncomeCode" value="${contact.spouseAnnualIncomeCode}" />
						<input type="hidden" name="spouseIncomeFromCode" value="${contact.spouseIncomeFromCode}" />
						<input type="hidden" name="contactRelationCode" value="${contact.contactRelationCode}" />
						<input type="hidden" name="contact2RelationCode" value="${contact.contact2RelationCode}" />
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
		$(document).ajaxStart(function() {$.showLoading();}).ajaxStop(function() {$.hideLoading();});
	});

	function verifyContactInput() {
		if(trim($("input[name='marrStatus']").val()) === "MARRIED") {
			if(trim($("input[name='spouseName']").val())==="") {
				$.alert('配偶姓名不能为空！'); return false;
			} else if(trim($("input[name='spouseCertId']").val())==="") {
				$.alert('配偶身份证号不能为空！'); return false; 
			} else if(!isIdCardNo($("input[name='spouseCertId']").val())) {
				$.alert('配偶身份证号格式有误！'); return false;
			} else if(trim($("input[name='spousePhone']").val())==="") {
				$.alert('配偶手机号不能为空！'); return false;
			} else if(verifyPhoneFormat($("input[name='spousePhone']").val())) {
				$.alert('配偶手机号格式有误！'); return false;
			} else if(trim($("input[name='spouseAnnualIncomeCode']").val())==="") {
				$.alert('配偶月收入不能为空！'); return false;
			} else if(trim($("input[name='spouseIncomeFromCode']").val())==="") {
				$.alert('配偶收入来源不能为空！'); return false;
			} else if(trim($("input[name='spouseWorkUnit']").val())==="") {
				$.alert('配偶工作单位不能为空！'); return false;
			}
		} else {
			if(trim($("input[name='contactRelationCode']").val())==="") {
				$.alert('紧急联系人（一）：关系不能为空！'); return false;
			} else if(trim($("input[name='contactName']").val())==="") {
				$.alert('紧急联系人（一）：姓名不能为空！'); return false;
			} else if(trim($("input[name='contactCertId']").val())==="") {
				$.alert('紧急联系人（一）：身份证号不能为空！'); return false;
			} else if(!isIdCardNo($("input[name='contactCertId']").val())) {
				$.alert('紧急联系人（一）：身份证号格式有误！'); return false;
			} else if(trim($("input[name='contactPhone']").val())==="") {
				$.alert('紧急联系人（一）：手机号不能为空！'); return false;
			} else if(verifyPhoneFormat($("input[name='contactPhone']").val())) {
				$.alert('紧急联系人（一）：手机号格式有误！'); return false;
			}
		}

		if(trim($("input[name='contact2RelationCode']").val())==="") {
			$.alert('紧急联系人（二）：关系不能为空！'); return false;
		} else if(trim($("input[name='contact2Name']").val())==="") {
			$.alert('紧急联系人（二）：姓名不能为空！'); return false;
		} else if(trim($("input[name='contact2CertId']").val())==="") {
			$.alert('紧急联系人（二）：身份证号不能为空！'); return false;
		} else if(!isIdCardNo($("input[name='contact2CertId']").val())) {
			$.alert('紧急联系人（二）：身份证号格式有误！'); return false;
		} else if(trim($("input[name='contact2Phone']").val())==="") {
			$.alert('紧急联系人（二）：手机号不能为空！'); return false;
		} else if(verifyPhoneFormat($("input[name='contact2Phone']").val())) {
			$.alert('紧急联系人（二）：手机号格式有误！'); return false;
		}
		return true;
	}
		
	function getDataJsonParam() {
		if(trim($("input[name='marrStatus']").val())==="MARRIED") {
			return json = {
					'spouseName' : trim($("input[name='spouseName']").val()),
					'spouseCertId' : trim($("input[name='spouseCertId']").val()),
					'spousePhone' : trim($("input[name='spousePhone']").val()),
					'spouseAnnualIncome' : trim($("p[class='spouseannualincome']").text()),
					'spouseAnnualIncomeCode' : trim($("input[name='spouseAnnualIncomeCode']").val()),
					'spouseIncomeFrom' : trim($("p[class='spouseincomefrom']").text()),
					'spouseIncomeFromCode' : trim($("input[name='spouseIncomeFromCode']").val()),
					'spouseWorkUnit' : trim($("input[name='spouseWorkUnit']").val()),
					'contact2Relation' : trim($("p[class='contact2relation']").text()),
					'contact2RelationCode' : trim($("input[name='contact2RelationCode']").val()),
					'contact2Name' : trim($("input[name='contact2Name']").val()),
					'contact2CertId' : trim($("input[name='contact2CertId']").val()),
					'contact2Phone' : trim($("input[name='contact2Phone']").val())
				};
		} else {
			return json = {
					'contactRelation' : trim($("p[class='contactrelation']").text()),
					'contactRelationCode' : trim($("input[name='contactRelationCode']").val()),
					'contactName' : trim($("input[name='contactName']").val()), 
					'contactCertId' : trim($("input[name='contactCertId']").val()),
					'contactPhone' : trim($("input[name='contactPhone']").val()),
					'contact2Relation' : trim($("p[class='contact2relation']").text()),
					'contact2RelationCode' : trim($("input[name='contact2RelationCode']").val()),
					'contact2Name' : trim($("input[name='contact2Name']").val()),
					'contact2CertId' : trim($("input[name='contact2CertId']").val()),
					'contact2Phone' : trim($("input[name='contact2Phone']").val())
			};
		}
	}

	// 下一步
	function nextbtn() {
		if(!verifyContactInput()) return;
		$.ajax({
			type : "POST",
			url : "${ctx}/register/saveContactInfo",
			datatype : "json",
			data : getDataJsonParam(),
			success : function(data) {
				var obj = eval('(' + data + ')');
				if (obj.result == "success") {
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
	
	function turnback() {
		window.location.href = "${ctx}/register/personInfo";
	}

	// 上传图片
	function previewImage(file, v) {
		$.showLoading();
		var img_size = file.files[0].size;
		var i_quality = 0.9;
		if (img_size > 1048576) {
			i_quality = 1048576 / img_size;
		}

		lrz(file.files[0], {
			quality : i_quality
		}).then(function(rst) {
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
					if(v=="1") {
						$("input[name='spouseName']").val(jsonObj.userName);
	    				$("input[name='spouseCertId']").val(jsonObj.certId);
					} else if(v=="2") {
						$("input[name='contactName']").val(jsonObj.userName);
	    				$("input[name='contactCertId']").val(jsonObj.certId);
					} else if(v=="3") {
						$("input[name='contact2Name']").val(jsonObj.userName);
	    				$("input[name='contact2CertId']").val(jsonObj.certId);
					}
				},
				error : function() {
					$.hideLoading();
					$.alert("读取失败，请手工输入！");
				}
			});
		});
	}
</script>
</html>