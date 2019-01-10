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
<title>客户进件管理</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/js/jquery-easyui-1.5.3/themes/icon.css">
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-easyui-1.5.3/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="tb_console_head" style="height: auto; width: auto;">
		<table >
			<tr>
				<td style="text-align: right">客户ID：</td>
				<td><input id="custId" name="custId" value="" class="easyui-textbox"></td>
				<td style="text-align: right">客户姓名：</td>
				<td><input id="userName" name="userName" value="" class="easyui-textbox"></td>
				<td style="text-align: right">证件号码：</td>
				<td><input id="certId" name="certId" value="" class="easyui-textbox"></td>
				<td style="text-align: right">手机号码：</td>
				<td><input id="phone" name="phone" value="" class="easyui-textbox"></td>
				<td>
					<a href="javascript:void();" name="search" class="easyui-linkbutton" iconCls="icon-search"> 查 询 </a>
				</td>
			</tr>
		</table>
	</div>
	<table id="dg_console_body"
	       data-options="rownumbers:true,
						method:'post',
						singleSelect:true,
						url:'${ctx}/manage/order_list?pageSize=20',
						toolbar:'#tb_console_head',
						pagination:true,
						fit:true">
		<thead>
			<tr>
				<th data-options="field:'custId',width:80,align:'center'">客户ID</th>
				<th data-options="field:'userName',width:60,align:'left'">客户姓名</th>
				<th data-options="field:'certId',width:160,align:'center'">证件号码</th>
				<th data-options="field:'phone',width:120,align:'center'">手机号码</th>
				<th data-options="field:'stepNo',width:80,align:'center'">步骤号</th>
				<th data-options="field:'createAt',width:160,align:'center'">创建时间</th>
				<th	data-options="field:'updateAt',width:150,align:'center'">更新时间</th>
				<th data-options="field:'request_message_show',width:80,align:'center',formatter:showRequest">详情</th>
				<th data-options="field:'request_message',hidden:true"></th>
			</tr>
		</thead>
	</table>
	<div id="detailInfo" style="padding:10px;"></div>
	<script type="text/javascript">
		 $(function() {
			var order_list_page = $('#dg_console_body').datagrid().datagrid('getPager');
			order_list_page.pagination({
				pageSize:20,
				pageList:[20,30,50],
				onChangePageSize:function(pageSize) {
					 doSearch(1,pageSize);
				},
				onSelectPage:function(pageNumber, pageSize){
					doSearch(pageNumber,pageSize);
				},
				onRefresh:function(pageNumber, pageSize){
					doSearch(pageNumber,pageSize);
				}
			});
			$("a[name='search']").click(function(){ doSearch(1,20); });
		});  
		
		function showRequest(value,row,index) {
			return "<a id=\"btn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\" onclick=\"javascript:openDialog("+index+");\">查看</a>";
		}

		function openDialog(i) {
			var row = $("#dg_console_body").datagrid("getRows")[i];
			var custid = row.custId;
			$('#detailInfo').dialog({
			    title: '客户进件进度跟踪...',
			    width: 920,
			    height: 590,
			    closed: false,
			    cache: false,
			    resizable: true,
			    modal: true,
			    href: '${ctx}/manage/track?custid='+custid,
			    onClose: function() {
				    $.get("${ctx}/logout",{},function(result) {});
				}
			});
		}


		function doSearch(pageNumber, pageSize) {
			$.ajax({
                type : "POST",
                dataType : "json",
                async : false,
                data : {
                	"page":pageNumber,
					"pageSize":pageSize,
					"custId":$('#custId').textbox('getValue'),
					"userName":$('#userName').textbox('getValue'),
					"certId":$('#certId').textbox('getValue'),
					"phone":$('#phone').textbox('getValue')
				},
                url : "${ctx}/manage/order_list",
                success : function(data) {
                	$("#dg_console_body").datagrid("loadData", data);
                },
                error : function(XMLHttpRequest, textStatus, errorThrown) {
                	$.messager.alert('错误',"error:" + textStatus,'error');
                }
			});				
		}
	</script>
</body>
</html>