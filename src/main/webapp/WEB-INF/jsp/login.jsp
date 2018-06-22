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
    <link rel="stylesheet" href="${ctx}/css/weui.min.css">
    <link rel="stylesheet" href="${ctx}/css/jquery-weui.min.css">
    <link rel="stylesheet" href="${ctx}/css/wxss.css">
    <link rel="stylesheet" href="${ctx}/css/login.css">
    <link href="https://cdn.bootcss.com/Swiper/4.1.0/css/swiper.min.css" rel="stylesheet">
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-weui.min.js"></script>
	<script src="https://cdn.bootcss.com/Swiper/4.1.0/js/swiper.min.js"></script>
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/wxss.js"></script>
</head>
<body ontouchstart>
    <div class="swiper_content">
        <div class="swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide">
                    <image src="${ctx}/image/swiper.png" alt="">
                </div>
                <div class="swiper-slide">
                    <image src="${ctx}/image/swipertwo.png" alt="">
                </div>
                <div class="swiper-slide">
                    <image src="${ctx}/image/swiper.png" alt="">
                </div>
                <div class="swiper-slide">
                    <image src="${ctx}/image/swipertwo.png" alt="">
                </div>
            </div>
            <div class="swiper-pagination"></div>
        </div>
    </div>
	    <div class="content_input">
	        <input type="text" name="phone" class="phone" value="${sessionScope.LOGIN_USER.phone}" placeholder="手机号">
	        <div class="content_input_code">
	            <input type="text" name="verifCode" class="code" value="${verifcode}" placeholder="验证码">
	            <button class="content_input_code_div" onclick="sendMessage()">获取验证码</button>
	        </div>
	        <c:if test="${!empty loginInfo}"><div style="margin-bottom: 20px;color: red;">${loginInfo}</div></c:if>
	        <%-- <div class="weui-cell" onclick="javascript:selectpeople();">
       			<div class="weui-cell__hd"><label class="weui-label">关系</label></div>
       			<div class="weui-cell__bd">
                    <p class="content_input_select_value"></p>
                </div>
     		</div>
	         --%>
	        <div class="content_input_select">
	            <div class="content_input_select_div" onclick="selectpeople()">
	                <p class="content_input_select_value"></p><image src="${ctx}/image/config-icon.png" alt="">
	            </div>
	        </div> 
	        <div style="display:none;">
    			<input type="hidden" name="bpId" value="${bpId}">
	            <input type="hidden" name="saleId" value="">
		    </div>
	    </div>
	    <div class="footer_btn">
	        <div class="footer_btn_div" onclick="loginbtn()">登录</div>
	    </div>
</body>
<script>
	var InterValObj; // timer变量，控制时间
	var count = 60-(new Date().getTime()-(new Date(Date.parse("${sessionScope.LOGIN_USER.starttime}".replace(/-/g,"/"))).getTime()))/1000;
	var curCount = (!isNaN(count) && count>0)?parseInt(count):60;    // 当前剩余秒数

	$(function() {
	    if(!isNaN(count) && count>0) {
	    	$(".content_input_code_div").attr("disabled", "disabled");
            $(".content_input_code_div").text(curCount + "秒内输入");
            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
		}
	}); 

    // weui.alert('尊敬的用户，我司会采集您填写的信息，并依法对信息保密，履行信息安全保障义务');
    // swiper
    var swiper = new Swiper('.swiper-container', {
        spaceBetween: 30,
        centeredSlides: true,
        autoplay: {
            delay: 2000,
            disableOnInteraction: false,
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true,
        },
    });

    function selectpeople() {
    	var bpId = $("input[name='bpId']").val();
		var ajaxJsonSales = getJsonSales(bpId);
	    weui.picker(ajaxJsonSales, {
	        className: 'custom-classname',
	        container: 'body',
	        onConfirm: function(result) {
	        	$("p[class='content_input_select_value']").text(result[0].label);
	        	$("input[name='saleId']").val(result[0].value);
	        },
	        id: 'singleLinePicker'
	    });
	}


    function getJsonSales(bpId) {
		var rtnJsonObj;
		$.ajax({
			type : "POST",
			url : "${ctx}/getSaleList",
			datatype : "json",
			async: false,
			data : { "bpId" : bpId },
			success : function(data) {
				rtnJsonObj = eval('(' + data + ')');
			},
			error : function() {
				$.alert("error");
			}
		});
		return rtnJsonObj;
	}


    function sendMessage() {
        // 验证是否是手机号登录
        if (!/^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$/.test($(".phone").val())) {
        	$.alert("请输入正确的11位手机号码");
            return;
        } else {
        	$(".content_input_code_div").attr("disabled", "disabled");
            $(".content_input_code_div").text(curCount + "秒内输入");
            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
            
            // ajax
			var phone = $("input[name='phone']").val();
			var verifCode = $("input[name='verifCode']").val();
			
			$.ajax({
				type : "POST",
				url : "${ctx}/sendVerifCode",
				datatype : "json",
				data : {
					'phone' : phone,
					'verifCode': verifCode
				},
				success : function(data) {
					if (data.result == "success") {
						$.alert("请注意查收短信！");
					} else {
					    return;
					}
				},
				error : function() {
					
				}
			});
        }
    }
    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            $(".content_input_code_div").removeAttr("disabled"); //启用按钮
            $(".content_input_code_div").text("重新发送");
            curCount=60;
        } else {
            curCount--;
            $(".content_input_code_div").text(curCount + "秒内输入");
        }
    }
    
    //获取手机号之后进行验证
    function loginbtn() {
        if (!/^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$/.test($(".phone").val())) {
        	$.alert('请输入正确的11位手机号码');
        } else if ($(".code").val().trim() === "") {
        	$.alert('请输入验证码');
		} else if($("input[name='saleId']").val() === "") {
			$.alert('请选择服务人员!');
		} else {
			var str=location.href; 
			var parametes = str.substr(str.indexOf("?")+1);
			window.location.href="${ctx}/login?"+parametes+"&saleId="+$("input[name='saleId']").val()+"&phone="+$(".phone").val()+"&verifCode="+$(".code").val().trim();
        } 
    }
</script>
</html>