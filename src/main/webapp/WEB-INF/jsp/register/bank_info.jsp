<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path+"/";
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<base href="<%=basePath %>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>迅信通</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <link rel="stylesheet" href="${ctx}/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/css/weui.min.css">
    <link rel="stylesheet" href="${ctx}/css/wxss.css">
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/fastclick.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-weui.min.js"></script>
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/wxss.js"></script>
	<script type="text/javascript" src="${ctx}/js/lrz.all.bundle.js"></script>
</head>
<body ontouchstart>
<div class="weui-tab">
	<div class="weui-navbar">
    	<div class="weui-navbar__item weui-bar__item--on wxss-register_title">
    		<div style="width:64px" class="wxss-register_turnimg" onclick="turnback()">
    			<img src="${ctx}/image/turnback.png" alt="" class="weui-tabbar__icon">
		    </div>
			<span id="NavTitle">绑定银行卡</span>
			<div style="width:64px"></div>
        </div>
    </div>
    <div class="weui-tab__bd">
    	<div class="weui-tab__bd-item weui-tab__bd-item--active">
	    	<div class="upload">
		        <div class="upload_content">
		             <div id="previewA">
		                 <c:choose>
					         <c:when test="${!empty bank.bankcardFrontImagePath}">
					         	<img id="imgheadA" border="0" src="${ctx}${bank.bankcardFrontImagePath}" style="border-radius: 10px;" onclick="$('#previewImgA').click();">
					         </c:when>
					         <c:otherwise>
					         	<img id="imgheadA" border="0" src="${ctx}/image/cardclick.png" style="width:65%;height:100%;border-radius: 0px;" onclick="$('#previewImgA').click();">
					         </c:otherwise>
						 </c:choose>
		             </div>
		             <input type="file" onchange="previewImage(this, 'A')" style="display: none;" id="previewImgA">
		        </div>
	        </div>
	        <div class="upload">
		        <div class="upload_content">
		             <div id="previewB">
		             	 <c:choose>
					         <c:when test="${!empty bank.bankcardBackImagePath}">
					         	<img id="imgheadB" border="0" src="${ctx}${bank.bankcardBackImagePath}" style="border-radius: 10px;" onclick="$('#previewImgB').click();">
					         </c:when>
					         <c:otherwise>
					         	<img id="imgheadB" border="0" src="${ctx}/image/cardclick.png" style="width:65%;height:100%;border-radius: 0px;" onclick="$('#previewImgB').click();">
					         </c:otherwise>
						 </c:choose>
		             </div>
		             <input type="file" onchange="previewImage(this, 'B')" style="display: none;" id="previewImgB">
		        </div>
	        </div>
	         <div class="weui-loadmore" style="display:none;">
			 	<i class="weui-loading"></i>
			    <span class="weui-loadmore__tips">loading...</span>
			 </div>
    		<div class="weui-cells weui-cells_form">
    		 	<div class="weui-cell weui-cell_access" onclick="javascript:select('86','repayAccBank','repayAccBankCode');">
			        <div class="weui-cell__hd"><label class="weui-label">还款银行</label></div>
			        <div class="weui-cell__bd">
			            <p class="repayAccBank" style="text-align:right;padding-right:20px;">${bank.repayAccBank}</p>
			        </div>
			        <div class="weui-cell__ft"></div>
			    </div>
      			<div class="weui-cell weui-cell_access" onclick="javascript:selectCardType();">
        			<div class="weui-cell__hd"><label class="weui-label">卡种</label></div>
        			<div class="weui-cell__bd" >
	                	<p class="cardType" style="text-align:right;padding-right:20px;">储蓄卡</p>
	                </div>
	                <div class="weui-cell__ft"></div>
      			</div>
      			<div class="weui-cell">
        			<div class="weui-cell__hd"><label class="weui-label">银行卡号</label></div>
        			<div class="weui-cell__bd" style="padding-right:20px;">
	                    <input class="weui-input" type="tel" style="text-align:right;padding-right:20px;" name="repayCardNo" placeholder="" value="${bank.repayCardNo}" >
	                </div>
      			</div>
      			<div class="weui-cell">
			        <div class="weui-cell__hd">
			            <label class="weui-label">开卡手机号</label>
			        </div>
			        <div class="weui-cell__bd" style="padding-right:20px;">
			            <input class="weui-input" name="bankPhone" type="tel" style="text-align:right;padding-right:20px;" placeholder="银行预留手机号" value="${bank.bankPhone}">
			        </div>
			    </div>
			    <c:choose>
			        <c:when test="${spdb_cert_flag eq 1 and (!empty bank.spdbStcardNo or bank.spdbFlag eq 1)}">
			            <input type="hidden" name="verifyCode" style="text-align:right;" placeholder="验证码" value="000000" >
			            <span style="display:none"><input id="weuiAgree" type="checkbox" class="weui-agree__checkbox" checked="checked" /></span>
			        </c:when>
			        <c:otherwise>
			            <div class="weui-cell weui-cell_vcode">
		        			<div class="weui-cell__hd"><label class="weui-label">短信验证码</label></div>
		        			<div class="weui-cell__bd" style="padding-right:20px;">
			                    <input class="weui-input" type="text" name="verifyCode" style="text-align:right;" type="tel" placeholder="验证码" value="" >
			                </div>
			                 <div class="weui-cell__ft">
					            <button class="weui-vcode-btn" onclick="javascript:sendVerifyCode();">获取验证码</button>
					        </div>
		      			</div>
		      			<div class="weui-cells__tips">
		      			   <input id="weuiAgree" type="checkbox" class="weui-agree__checkbox"/> 
					       <span class="weui-agree__text">本人同意在浦发银行开设专户电子账户！ </span>
					    </div>
			        </c:otherwise>
				</c:choose>
      			<div style="display:none;">
      				<input name="repayAccBankCode" type="hidden" value="${bank.repayAccBankCode}" />
      				<input name="bankcardFrontImagePath" type="hidden" value="${bank.bankcardFrontImagePath}" />
      				<input name="bankcardBackImagePath" type="hidden" value="${bank.bankcardBackImagePath}" />
			    </div>
      		</div>
      		<div class="wxss-register_btn">
			    <div class="wxss-register_btn_div" onclick="nextbtn();">下一步</div>
			</div>
      	</div>
 	</div>
 </div>
</body>
<script>
	var InterValObj; // timer变量，控制时间
	var count = 60-(new Date().getTime()-(new Date(Date.parse("${bank.smsSendtime}".replace(/-/g,"/"))).getTime()))/1000;
	var curCount = (!isNaN(count) && count>0)?parseInt(count):60;    // 当前剩余秒数
	
	$(function() {
		$(document).ajaxStart(function() {	$.showLoading();}).ajaxStop(function() { $.hideLoading(); });
	    if(!isNaN(count) && count>0) {
	    	$(".weui-vcode-btn").attr("disabled", "disabled");
	        $(".weui-vcode-btn").text(curCount + "秒内输入");
	        InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
		}
	});

	// 下一步
    function nextbtn() {
        if ($("p[class='repayAccBank']").text() === "") {
            $.alert("还款银行不能为空");

        } else if ($("input[name='repayCardNo']").val() === "") {
            $.alert("银行卡号不能为空");

        } else if ($("input[name='bankPhone']").val() === "") {
            $.alert("银行卡预留手机号不能为空");
            
        } else if (!/^1[3|4|5|6|7|8|9][0-9]\d{8}$/.test($("input[name='bankPhone']").val().trim())) {
        	$.alert("手机号格式不正确！");
        	
        } else if ($("input[name='bankcardFrontImagePath']").val() === "") {
            $.alert("请上传银行卡正面照片");

        } else if ($("input[name='bankcardBackImagePath']").val() === "") {
            $.alert("请上传银行卡反面照片");

        }  else if ($("input[name='repayAccBankCode']").val() === "") {
            $.alert("请重新选择还款银行");

        } else if ($("input[name='verifyCode']").val() === "") {
            $.alert("请输入短信验证码！");

        } else if (${empty bank.spdbStcardNo} && !$("#weuiAgree").is(":checked")) {
            $.alert("请选择同意在浦发银行开设专户电子账户！");

        } else {
            $.ajax({
				type : "POST",
				url : "${ctx}/register/saveBankInfo",
				datatype : "json",
				data : {
					'repayAccBank' : $("p[class='repayAccBank']").text(),
					'repayAccBankCode' : $("input[name='repayAccBankCode']").val(),
					'repayCardNo' : trim($("input[name='repayCardNo']").val()),
					'bankPhone' : trim($("input[name='bankPhone']").val()),
					'verifyCode' : trim($("input[name='verifyCode']").val())
				},
				success : function(data) {
					var obj = eval('(' + data + ')');
					if(obj.result=="success") {
						window.location.href = "${ctx}/register/personInfo";
					} else {
						$.alert(obj.message);
					}
				},
				error : function() {
					alert("error");
				}
			}); 
        } 
    }

    function selectCardType() {
        weui.picker([{'label':'储蓄卡', 'value':'储蓄卡'}], {
            className: 'custom-classname',
            container: 'body',
            onConfirm: function(result) {
            },
            id: 'singleLinePicker'
        });
    }

    
	/**
	* 发送短信验证码
	*/
    function sendVerifyCode() {
        //if (!/^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$/.test($("input[name='bankPhone']").val())) {
        if (!/^13[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|16[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$|19[0-9]{9}$/.test($("input[name='bankPhone']").val())) {
        	$.alert("请输入正确的11位手机号码");
            return;
        } else if($("p[class='repayAccBank']").text()=="") {
        	$.alert("请选择还款银行！");
            return;
        } else if($("input[name='repayCardNo']").val()=="") {
        	$.alert("请输入银行卡号！");
            return;
        } else {
        	$(".weui-vcode-btn").attr("disabled", "disabled");
        	$.ajax({
	            type : "POST",
	            url : "${ctx}/register/sendVerifyCode",
	            datatype : "json",
	            data : {
	               "repayAccBank" : $("p[class='repayAccBank']").text(),
	               "repayAccBankCode" : $("input[name='repayAccBankCode']").val(),
	               "repayCardNo" : trim($("input[name='repayCardNo']").val()),
	               "bankPhone" : trim($("input[name='bankPhone']").val())
				},
				success : function(data) {
					obj = eval('(' + data + ')');
					$.alert(obj.message);
					if(obj.result=="success") {
						$(".weui-vcode-btn").text(curCount + "秒内输入");
			        	InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
					} else {
						$(".weui-vcode-btn").removeAttr("disabled"); //启用按钮
					}
				},					
				error : function() {
					$.alert("短信验证码发送出错！");
					$(".weui-vcode-btn").removeAttr("disabled"); //启用按钮
				}
			});
        }
    }


    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            $(".weui-vcode-btn").removeAttr("disabled"); //启用按钮
            $(".weui-vcode-btn").text("重新发送");
            curCount=60;
        } else {
            curCount--;
            $(".weui-vcode-btn").text(curCount + "秒内输入");
        }
    }

    function uploadImage(file, img, type) {
    	$.showLoading();
    	var img_size=file.files[0].size;
		var i_quality=0.9;
		if (img_size>1048576){
			i_quality=1048576/img_size;
		}

    	lrz(file.files[0], { quality: i_quality }).then(function(rst) {
            img.src = rst.base64;
            //先追加到页面里
            var base64_str = rst.base64;
            var base64string = base64_str.split(",")[1];
            $.ajax({
                type : "POST",
                url : "${ctx}/register/uploadImage",
                datatype : "json",
                async: false,
                data : {
                   "type" : type,
                   "base64string" : base64string
				},
				success : function(data) {
					$.hideLoading();
					jsonObj = eval('(' + data + ')');
					if(type =='bankInfoA') {
						$("input[name='bankcardFrontImagePath']").val("Y");
						var bank = getRepayBanks(jsonObj.repayAccBank);
						if(null == bank) {
							$("p[class='repayAccBank']").text("");
							$("input[name='repayAccBankCode']").val("");
							$.alert("银行卡照片读取成功！请确认该银行是否在指定银行列表中.");
						} else {
							$("p[class='repayAccBank']").text(bank.label);
							$("input[name='repayAccBankCode']").val(bank.value);
						}
						$("input[name='repayCardNo']").val(jsonObj.repayCardNo);
					}else if(type =='bankInfoB') {
						$("input[name='bankcardBackImagePath']").val("Y");
					}
				},					
				error : function() {
					$.hideLoading();
					$.alert("读取失败，请重新上传！");
				}
			});
    	});
        
        /* var reader = new FileReader();
        reader.onload = function (evt) {
        	img.src = evt.target.result;
            var driver_licence_base64 = reader.result;
            var base64string = driver_licence_base64.split(",")[1];
            $.ajax({
                type : "POST",
                url : "${ctx}/register/uploadImage",
                datatype : "json",
                async: false,
                data : {
                   "type" : type,
                   "base64string" : base64string
				},
				success : function(data) {
					jsonObj = eval('(' + data + ')');
					if(type =='bankInfoA') {
						var bank = getRepayBanks(jsonObj.repayAccBank);
						if(null == bank) {
							$("p[class='repayAccBank']").text("");
							$("input[name='repayAccBankCode']").val("");
							$.alert("银行卡照片读取成功！请确认该银行是否在指定银行列表中.");
						} else {
							$("p[class='repayAccBank']").text(bank.label);
							$("input[name='repayAccBankCode']").val(bank.value);
						}
						$("input[name='repayCardNo']").val(jsonObj.repayCardNo);
						$("input[name='bankcardFrontImagePath']").val("Y");
					}else if(type =='bankInfoB') {
						$("input[name='bankcardBackImagePath']").val("Y");
					}
				},					
				error : function() {
					$.alert("读取失败，请重新上传！");
				}
			}); 
        };
        reader.readAsDataURL(file.files[0]);
        reader.onloadstart = function(evt) {$.showLoading(); };
        reader.onloadend = function(evt) { $.hideLoading(); };
        reader.onerror = function(evt) { $.alert("图片上传失败，请重试..."); }; */
    }


 	// 上传图片
    function previewImage(file, v) {
    		$.showLoading();
    		var type = "bankInfo"+v;
	    	var div = document.getElementById('preview'+v);
	        if (file.files && file.files[0]) {
	            div.innerHTML = '<img id=imghead'+v+' onclick=$("#previewImg'+v+'").click()>';
	            var img = document.getElementById('imghead'+v);
	            img.onload = function () {
	            }
	            uploadImage(file, img, type);
	            
		    } else {
			    var sFilter = 'filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';
	            file.select();
	            var src = document.selection.createRange().text;
	            div.innerHTML = '<img id=imghead>';
	            var img = document.getElementById('imghead');
	            img.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
	
	            status = ('rect:' + rect.top + ',' + rect.left + ',' + rect.width + ',' + rect.height);
	            div.innerHTML = "<div id=divhead style='width:" + rect.width + "px;height:" + rect.height + "px;" + sFilter + src + "\"'></div>";
	        }
    }

    function turnback() {
		window.location.href = "${ctx}/register/driverInfo";
	}
</script>
</html>