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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>浦发银行Ⅱ/Ⅲ开户查询</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/icon.css">
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="tb_spdb_head" style="height: auto; width: auto;">
		<table >
			<tr>
				<td style="text-align: right">客户ID：</td>
				<td><input id="custId" name="custId" value="" class="easyui-textbox"></td>
				<td style="text-align: right">客户姓名：</td>
				<td><input id="custName" name="custName" value="" class="easyui-textbox"></td>
				<td style="text-align: right">证件号码：</td>
				<td><input id="certCode" name="certCode" value="" class="easyui-textbox"></td>
				<td style="text-align: right">手机号码：</td>
				<td><input id="phone" name="phone" value="" class="easyui-textbox"></td>
				<td>
					<a href="javascript:void(0)" class="easyui-linkbutton" onclick="doSearch()" iconCls="icon-search"> 查 询 </a>
				</td>
			</tr>
		</table>
	</div>
	<table id="dg_spdb_logs" class="easyui-datagrid" 
	       data-options="rownumbers:true,
						method:'post',
						singleSelect:true,
						toolbar:'#tb_spdb_head',
						pagination:false,
						fit:true">
		<thead>
			<tr>
				<th data-options="field:'cust_id',width:80,align:'center'">客户ID</th>
				<th data-options="field:'cust_name',width:60,align:'left'">客户姓名</th>
				<th data-options="field:'cert_code',width:160,align:'center'">证件号码</th>
				<th data-options="field:'phone',width:120,align:'center'">手机号码</th>
				<th data-options="field:'request_time',width:160,align:'center'">发起时间</th>
				<th	data-options="field:'opType',width:150,align:'left'">操作类型</th>
				<th	data-options="field:'result',width:80,align:'center'">处理结果</th>
				<th data-options="field:'request_message_show',width:100,align:'center',formatter:showRequest">请求报文</th>
				<th data-options="field:'request_message',hidden:true"></th>
				<th data-options="field:'response_message_show',width:100,align:'center',formatter:showResponse">响应报文</th>
				<th data-options="field:'response_message',hidden:true"></th>
			</tr>
		</thead>
	</table>
	<div id="dd" ></div>
	<script type="text/javascript">
		function showRequest(value,row,index) {
			return "<a id=\"btn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\" onclick=\"javascript:openDialogRequest("+index+");\">查看信息</a>";
		}
		
		function showResponse(value,row,index) {
			return "<a id=\"btn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\" onclick=\"javascript:openDialogResponse("+index+");\">查看信息</a>";
		}

		function openDialogRequest(index) {
			var row = $("#dg_spdb_logs").datagrid("getRows")[index];
			var content = "\r\n";
			if(row.request_message != "") {
				content = JSON.stringify(JSON.parse(row.request_message), null, " <br/>");
			} 
			$('#dd').dialog({
			    title: '发送请求信息',
			    width: 600,
			    height: 300,
			    closed: false,
			    cache: false,
			    content: content,
			    modal: false
			});
		}

		function openDialogResponse(index) {
			var row = $("#dg_spdb_logs").datagrid("getRows")[index];
			var content = "\r\n";
			if(row.response_message != "") {
				var content = JSON.stringify(JSON.parse(row.response_message), null, " <br/>");
			}
			$('#dd').dialog({
			    title: '接收响应信息',
			    width: 600,
			    height: 300,
			    closed: false,
			    cache: false,
			    content: content,
			    modal: false
			});
		}

		function doSearch() {
		 	if ($('#custId').textbox('getValue') == "" && $('#custName').textbox('getValue') == "" 
			 	  && $('#certCode').textbox('getValue')=="" && $('#phone').textbox('getValue')=="" ) {
	            $.messager.alert('提示',"请输入查询条件！",'info');
	            return;
	        } 
	        
			$.ajax({
                type : "POST",
                dataType : "json",
                async : false,
                data : {
                	"cust_id":$('#custId').textbox('getValue'),
					"cust_name":$('#custName').textbox('getValue'),
					"cert_code":$('#certCode').textbox('getValue'),
					"phone":$('#phone').textbox('getValue')
				},
                url : "${ctx}/api/search_spdb_list",
                success : function(data) {
                    if(data=="") {
                    	$.messager.alert('提示',"查无数据！",'info');
                	} else {
                		$("#dg_spdb_logs").datagrid("loadData", data);
                    }
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                	$.messager.alert('错误',"error:" + textStatus,'error');
                }
			});				
			
		}
	</script>
</body>
</html>