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
    <link rel="stylesheet" href="https://cdn.bootcss.com/Swiper/4.1.0/css/swiper.min.css" >
    
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-weui.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/bscroll.min.js"></script>
	<script type="text/javascript" src="https://cdn.bootcss.com/Swiper/4.1.0/js/swiper.min.js"></script>
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
      <form name="frmLogin" action="${ctx}/authentication" method="post">
	    <div class="content_input">
	        <input type="text" name="phone" class="phone" value="${sessionScope.LOGIN_USER.phone}" placeholder="客户手机号">
	        <div class="content_input_code">
	            <input type="text" name="verifCode" class="code" value="" placeholder="验证码">
	            <button type="button" name="btnSendMsg" class="content_input_code_div">获取验证码</button>
	        </div>
	        <c:if test="${!empty loginInfo}"><div style="margin-bottom: 20px;color: red;">${loginInfo}</div></c:if>
	    </div>
	    </form>
	    <div class="footer_btn">
	        <div class="footer_btn_div" style="font-size: 18px;" onclick="loginbtn()">登录</div>
	    </div>
</body>
<script>
	var InterValObj; // timer变量，控制时间
	var count = 120-(new Date().getTime()-(new Date(Date.parse("${sessionScope.LOGIN_USER.starttime}".replace(/-/g,"/"))).getTime()))/1000;
	var curCount = (!isNaN(count) && count>0)?parseInt(count):120;    // 当前剩余秒数

	$(function() {
	    if(!isNaN(count) && count>0) {
	    	$(".content_input_code_div").attr("disabled", "disabled");
            $(".content_input_code_div").text(curCount + "秒内输入");
            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
		}

		$("button[name='btnSendMsg']").click(function() {
			// 验证是否是手机号登录
	        if (!/^13[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|16[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$|19[0-9]{9}$/.test($(".phone").val())) {
	        	$.alert("请输入正确的11位手机号码");
	            return;
	        } else {
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
						var obj = eval('(' + data + ')');
						if (obj.result == "success") {
							$(".content_input_code_div").attr("disabled", "disabled");
				            $(".content_input_code_div").text(curCount + "秒内输入");
				            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
				            
							$.alert("短信已发送，请注意查收！");
						} else if(obj.result == "failed"){
							$.alert(obj.message);
						} else {
							return;
						}
					},
					error : function() {
						
					}
				});
	        }
		});
	}); 

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

    
    //timer处理函数
    function SetRemainTime() {
        if (curCount == 0) {
            window.clearInterval(InterValObj); //停止计时器
            $(".content_input_code_div").removeAttr("disabled"); //启用按钮
            $(".content_input_code_div").text("重新发送");
            curCount=120;
        } else {
            curCount--;
            $(".content_input_code_div").text(curCount + "秒内输入");
        }
    }
    
    //获取手机号之后进行验证
    function loginbtn() {
        if (!/^13[0-9]{9}$|14[0-9]{9}$|15[0-9]{9}$|16[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$|19[0-9]{9}$/.test($(".phone").val())) {
        	$.alert('请输入正确的11位手机号码');
        } else if ($("input[name='verifCode']").val().trim() === "") {
        	$.alert('请输入验证码');
		} else {
			$("form[name='frmLogin']").submit();
        } 
    }
</script>
</html>