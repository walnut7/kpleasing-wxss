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
    <link rel="stylesheet" href="${ctx}/css/reset2.css">
    <link rel="stylesheet" href="${ctx}/css/city.css">
    
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
      <form name="frmLogin" action="${ctx}/login" method="post">
	    <div class="content_input">
	        <input type="text" name="phone" class="phone" value="${sessionScope.LOGIN_USER.phone}" placeholder="客户手机号">
	        <div class="content_input_code">
	            <input type="text" name="verifCode" class="code" value="" placeholder="验证码">
	            <button type="button" name="btnSendMsg" class="content_input_code_div">获取验证码</button>
	        </div>
	        <c:if test="${!empty loginInfo}"><div style="margin-bottom: 20px;color: red;">${loginInfo}</div></c:if>
	        
	        
	        <div class="content_input_select">
	            <!-- <div class="content_input_select_div" onclick="selectpeople()"> -->
	            <div class="content_input_select_div">
                	<p class="content_input_select_value" style="font-size: 16px;">销售人员</p>
                	<img id="selectPeople" src="${ctx}/image/config-icon.png" alt="" />
	            </div>
	        </div> 
	        <div style="display:none;">
				<input type="hidden" name="bpId" value="${param.bpId}">
    			<input type="hidden" name="bpCode" value="${param.bpCode}">
    			<input type="hidden" name="modelId" value="${param.modelId}">
    			<input type="hidden" name="planId" value="${param.planId}">
	            <input type="hidden" name="saleId" value="">
		    </div>
	    </div>
	    </form>
	    <div class="footer_btn">
	        <div class="footer_btn_div" style="font-size: 18px;" onclick="loginbtn()">登录</div>
	    </div>
	    
	    <div id="people_list" class='weui-popup__container'>
            <div class="weui-popup__overlay"></div>
            <div class="weui-popup__modal">
		        <div class="city">
		            <div class="city-wrapper city-wrapper-hook">
		                <div class="scroller-hook">
		                    <div class="cities cities-hook"></div>
		                </div>
		                <div class="shortcut shortcut-hook"></div>
		            </div>
		        </div>
                <!-- <a href="javascript:;" class="weui-btn weui-btn_primary close-popup">关闭</a> -->
            </div>
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

		$("#selectPeople").click(function() {
			$("#people_list").popup('open');
			initCities();
		    bindEvent();
		});

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
				$.alert("请先扫描门店信息二维码，选择方案信息，再选择销售人员！");
			}
		});
		return rtnJsonObj;
	}


    function sendMessage() {
        
    }
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
		} else if($("input[name='saleId']").val() === "") {
			$.alert('请选择销售人员!');
		} else {
			$("form[name='frmLogin']").submit();
        } 
    }

    var cityWrapper = document.querySelector('.city-wrapper-hook');
    var cityScroller = document.querySelector('.scroller-hook');
    var cities = document.querySelector('.cities-hook');
    var shortcut = document.querySelector('.shortcut-hook');
    var scroll;
    var shortcutList = [];
    var anchorMap = {};

    function initCities() {
      var y = 0;
      var titleHeight = 28;
      var itemHeight = 44;

      var lists = '';
      var en = '<ul>';

      var bpId = $("input[name='bpId']").val();
	  var cityData = getJsonSales(bpId);
      
      cityData.forEach(function (group) {
        var name = group.name;

        lists += '<div class="title">'+name+'</div>';
        lists += '<ul>';
        group.people.forEach(function(g) {
          lists += '<li class="item"><span class="border-1px name" data-name="'+ g.name +'" data-id="'+ g.spid +'">'+ g.name +'</span></li>';
        });
        lists += '</ul>';


        var name = group.name.substr(0, 1);
        en += '<li data-anchor="'+name+'" class="item">'+name+'</li>';
        var len = group.people.length;
        anchorMap[name] = y;
        y -= titleHeight + len * itemHeight;

      });
      en += '</ul>';

      cities.innerHTML = lists;
      shortcut.innerHTML = en;
      shortcut.style.top = (cityWrapper.clientHeight - shortcut.clientHeight) / 2 + 'px';
      scroll = new window.BScroll(cityWrapper, {
        probeType: 3
      });

      scroll.scrollTo(0, 0);
    }


    //bind Event
    function bindEvent() {
      var touch = {};
      var firstTouch;

      shortcut.addEventListener('touchstart', function (e) {
        
        var anchor = e.target.getAttribute('data-anchor');
        firstTouch = e.touches[0];
        touch.y1 = firstTouch.pageY;
        touch.anchor = anchor;
        scrollTo(anchor);
      });

      shortcut.addEventListener('touchmove', function (e) {
        firstTouch = e.touches[0];
        touch.y2 = firstTouch.pageY;
        
        var anchorHeight = 16;
        var delta = (touch.y2 - touch.y1) / anchorHeight | 0;
        
        var anchor = shortcutList[shortcutList.indexOf(touch.anchor) + delta];
        scrollTo(anchor);
        e.preventDefault();
        e.stopPropagation();
      });


	  var tmove = {};
      $("li span").bind("touchstart", function(event){
    	  var event = event || window.event;  
    	  var touch = event.originalEvent.changedTouches[0];
    	  tmove.y1 = touch.pageY;
	  });
      

      $("li span").bind("touchend", function(event){
    	  var event = event || window.event;  
    	  var touch = event.originalEvent.changedTouches[0];
    	  tmove.y2 = touch.pageY;
    	  if((tmove.y2 - tmove.y1) === 0) {
	          var name = $(touch.target).attr("data-name");
	          var spid = $(touch.target).attr("data-id");
	          $("p[class='content_input_select_value']").text(name);
	          $("input[name='saleId']").val(spid);
	          $.closePopup();
    	  }
	  });
    }

    function scrollTo(anchor) {
        var maxScrollY = cityWrapper.clientHeight - cityScroller.clientHeight;
        var y = Math.min(0, Math.max(maxScrollY, anchorMap[anchor]));
        if (typeof y !== 'undefined') {
          scroll.scrollTo(0, y);
        }
    }
</script>
</html>