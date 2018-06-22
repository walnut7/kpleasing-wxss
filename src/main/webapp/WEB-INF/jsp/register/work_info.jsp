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
    <script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery-weui.min.js"></script>
	<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/wxss.js"></script>
</head>
<body>
<div class="weui-tab">
	<div class="weui-navbar">
    	<div class="weui-navbar__item weui-bar__item--on wxss-register_title">
    		<div style="width:64px" class="wxss-register_turnimg" onclick="turnback()">
    			<img src="${ctx}/image/turnback.png" alt="" class="weui-tabbar__icon">
		    </div>
			<span id="NavTitle">工作信息</span>
			<div style="width:64px"></div>
        </div>
    </div>
    <div class="weui-tab__bd">
    	<div class="weui-tab__bd-item weui-tab__bd-item--active">
    		<div class="weui-cells weui-cells_form">
    			<div class="weui-cell">
        			<div class="weui-cell__hd"><label class="weui-label">工作单位</label></div>
        			<div class="weui-cell__bd">
          				<input class="weui-input" type="text" name="workUnit" placeholder="" value="${work.workUnit}" >
        			</div>
      			</div>
      			<div class="weui-cell weui-cell_vcode">
        			<div class="weui-cell__hd"><label class="weui-label">入职年限</label></div>
        			<div class="weui-cell__bd">
          				<input class="weui-input" type="text" name="entryYear" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="" value="${work.entryYear}" >
        			</div>
        			<div class="weui-cell__ft"><p class="weui-vcode-btn">年</p></div>
      			</div>
      			<div class="weui-cell">
        			<div class="weui-cell__hd"><label class="weui-label">职务</label></div>
        			<div class="weui-cell__bd">
          				<input class="weui-input" type="text" name="position" placeholder="" value="${work.position}" >
        			</div>
      			</div>
      			<div class="weui-cell">
        			<div class="weui-cell__hd"><label class="weui-label">单位电话</label></div>
        			<div class="weui-cell__bd">
          				<input class="weui-input" type="text" name="unitTel" placeholder="" value="${work.unitTel}" >
        			</div>
      			</div>
      			<div class="weui-cell">
        			<div class="weui-cell__hd"><label class="weui-label">办公地址</label></div>
        			<div class="weui-cell__bd">
          				<input class="weui-input" type="text" name="workAddr" placeholder="" value="${work.workAddr}" >
        			</div>
      			</div>
      			<div class="weui-cell">
        			<div class="weui-cell__hd"><label class="weui-label">年收入</label></div>
        			<div class="weui-cell__bd">
          				<input class="weui-input" type="text" name="annualIncome" onkeyup="value=value.replace(/[^\d.]/g,'')" placeholder="" value="${work.annualIncome}" >
        			</div>
      			</div>
      			<div class="weui-cell" onclick="javascript:select('51','incomestatus','incomeStatus');">
        			<div class="weui-cell__hd"><label class="weui-label">月收入状态</label></div>
        			<div class="weui-cell__bd">
          				<p class="incomestatus">${work.incomeStatus}</p>
        			</div>
      			</div>
      			<div class="weui-cell" onclick="javascript:select('45','incomefrom','incomeFrom');">
        			<div class="weui-cell__hd"><label class="weui-label">收入来源</label></div>
        			<div class="weui-cell__bd">
          				<p class="incomefrom">${work.incomeFrom}</p>
        			</div>
      			</div>
      			<div class="weui-cell weui-cell_vcode">
        			<div class="weui-cell__hd"><label class="weui-label">工作年限</label></div>
        			<div class="weui-cell__bd">
          				<input class="weui-input" type="text" name="workYear" onkeyup="value=value.replace(/[^\d]/g,'')" placeholder="" value="${work.workYear}" >
        			</div>
        			<div class="weui-cell__ft"><p class="weui-vcode-btn">年</p></div>
      			</div>
      			<div style="display:none;">
	    			<input type="hidden" name="incomeStatus" value="${work.incomeStatusCode}" />
	    			<input type="hidden" name="incomeFrom" value="${work.incomeFromCode}" />
			    </div>
      		</div>
      		<br><br>
      		<div class="wxss-register_btn">
			    <div class="wxss-register_btn_div" onclick="nextbtn();">下一步</div>
			</div>
      	</div>
 	</div>
 </div>
 <script type="text/javascript" >
 	$(function() {
		$(document).ajaxStart(function() {	$.showLoading();}).ajaxStop(function() { $.hideLoading(); });
	});
	
    function nextbtn() {
    	if ($("input[name='workUnit']").val() === "") {
    		$.alert('工作单位不能为空');
        } else if ($("input[name='entryYear']").val() === "") {
        	$.alert('入职年限不能为空');
        } else if ($("input[name='position']").val() === "") {
        	$.alert('职务不能为空');
        } else if ($("input[name='unitTel']").val() === "") {
        	$.alert('单位电话不能为空');
        } else if ($("input[name='workAddr']").val() === "") {
        	$.alert('办公地址不能为空');
        } else if ($("input[name='annualIncome']").val() === "") {
        	$.alert('年收入不能为空');
        } else if ($("input[name='workYear']").val() === "") {
        	$.alert('工作年限不能为空');
        } else if ($("input[name='incomeStatus']").val() === "") {
        	$.alert('请选择收入状态');
        } else if ($("input[name='incomeFrom']").val() === "") {
        	$.alert('请选择收入来源');
        } else {
        	$.ajax({
    			type : "POST",
    			url : "${ctx}/register/saveWorkInfo",
    			datatype : "json",
    			data : {
    				'workUnit' : $("input[name='workUnit']").val(),
    				'entryYear' : $("input[name='entryYear']").val(),
    				'position' : $("input[name='position']").val(),
    				'unitTel' : $("input[name='unitTel']").val(),
    				'workAddr' : $("input[name='workAddr']").val(),
    				'annualIncome' : $("input[name='annualIncome']").val(),
    				'workYear' : $("input[name='workYear']").val(),
    				'incomeStatus' : $("p[class='incomestatus']").text(),
    				'incomeStatusCode' : $("input[name='incomeStatus']").val(),
    				'incomeFrom' : $("p[class='incomefrom']").text(),
    				'incomeFromCode' : $("input[name='incomeFrom']").val()
    			},
    			success : function(data) {
    				var obj = eval('(' + data + ')');
    				if(obj.result=="success") {
    					window.location.href = "${ctx}/register/confirm";
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

    function turnback() {
		window.location.href = "${ctx}/register/personInfo";
	}
    </script>
</body>
</html>