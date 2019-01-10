<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
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
<meta content="black" name="apple-mobile-web-app-status-bar-style">
</head>
<body>
<div>
	<div style="float:left; width:360px; height:620px;">
		<div style="background:url('${ctx}/image/iphone-bg.png') no-repeat center;background-size:100% 100%;" >
			<div style="padding-top:65px;padding-bottom:75px;margin-left:15px; ">
		    	<iframe id="iframePhone" runat="server" src="${ctx}/manage/shadow?custid=${custid}" height="560px;" width="325px;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</div>
		</div>
	</div>
	<div style="float:right; width:500px;">
		<div style="text-align:right;">
		    <a href="javascript:reloadIframePhone();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">刷新</a>
	        <a href="javascript:unbind();" class="easyui-linkbutton" data-options="iconCls:'icon-unlock'" style="margin-left:10px;">微信解绑</a>
	        <a href="javascript:setSpdbAccount();" class="easyui-linkbutton" data-options="iconCls:'icon-note_go'" style="margin-left:10px;">不开浦发电子户</a>
		</div>
		<div>
	    	<iframe runat="server" src="${ctx}/manage/flowchart?custid=${custid}" width="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
		</div>
	</div>
	<div style="display:none">
		<input type="hidden" name="custid" value="${custid}" />
	</div>
</div>
<script type="text/javascript">
function reloadIframePhone() {
	$('#iframePhone').attr('src', $('#iframePhone').attr('src'));
}

function unbind() {
	var custid = $("input[name='custid']").val();
	$.post("${ctx}/manage/unbind",{"custid":custid},function(result) {
		if("success"==result) $.messager.alert("提示","解绑成功！"); 
	});
}

function setSpdbAccount() {
	var custid = $("input[name='custid']").val();
	$.post("${ctx}/manage/igone_spdb",{"custid":custid},function(result) { 
		reloadIframePhone();
		$.messager.alert("提示",result);
	});
}
</script>
</body>
</html>