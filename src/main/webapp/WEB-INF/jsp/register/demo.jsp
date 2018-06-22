<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path+"/";
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath %>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>DEMO</title>
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
    <script type="text/javascript" src="${ctx}/js/wxss.js"></script>
</head>
<body ontouchstart>
 <header class='demos-header'>
      <h1 class="demos-title">Picker</h1>
    </header>

    <div class="weui-cells weui-cells_form">
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="time" class="weui-label">默认配置</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" id="time" type="text" value="">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="time2" class="weui-label">无分钟</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" id="time2" type="text" value="2012-12-12 12时">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="time3" class="weui-label">上午下午</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" id="time3" type="text">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="time4" class="weui-label">定制时间</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" id="time4" type="text" value="2012-12-12 上午8点">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="time-format" class="weui-label">自定义格式</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" id="time-format" type="text" value="2012年12月12日 12时13分">
        </div>
      </div>
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="time-format" class="weui-label">限制年月</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" id="years-monthes" type="text" value="2017-06-20 12:00">
        </div>
      </div>
    </div>

    <div class="weui-cells weui-cells_form">
      <div class="weui-cell">
        <div class="weui-cell__hd"><label for="time-inline" class="weui-label">内联显示</label></div>
        <div class="weui-cell__bd">
          <input class="weui-input" id="time-inline" type="text" value="">
        </div>
      </div>
    </div>

    <div id="time-container"></div>
    <script>
    $("#time2").datetimePicker({
        times: function () {
          return [
            {
              values: (function () {
                var hours = [];
                for (var i=0; i<24; i++) hours.push(i > 9 ? i : '0'+i);
                return hours;
              })()
            },
            {
              divider: true,  // 这是一个分隔符
              content: '时'
            }
          ];
        },
        onChange: function (picker, values, displayValues) {
          console.log(values);
        },
      });
      $("#time3").datetimePicker({
        times: function () {
          return [
            {
              values: ['上午', '下午']
            }
          ];
        },
        value: '2012-12-12 上午',
        onChange: function (picker, values, displayValues) {
          console.log(values);
        }
      });
      $("#time4").datetimePicker({
        times: function () {
          return [
            {
              values: ['上午8点', '下午2点', '晚上8点']
            }
          ];
        },
        max: '2013-12-12',
        onChange: function (picker, values, displayValues) {
          console.log(values);
        }
      });

      $("#time-format").datetimePicker({
        title: '自定义格式',
        yearSplit: '年',
        monthSplit: '月',
        dateSplit: '日',
        times: function () {
          return [  // 自定义的时间
            {
              values: (function () {
                var hours = [];
                for (var i=0; i<24; i++) hours.push(i > 9 ? i : '0'+i);
                return hours;
              })()
            },
            {
              divider: true,  // 这是一个分隔符
              content: '时'
            },
            {
              values: (function () {
                var minutes = [];
                for (var i=0; i<59; i++) minutes.push(i > 9 ? i : '0'+i);
                return minutes;
              })()
            },
            {
              divider: true,  // 这是一个分隔符
              content: '分'
            }
          ];
        },
        onChange: function (picker, values, displayValues) {
          console.log(values);
        }
      });
      $("#time-inline").datetimePicker({
        container: '#time-container',
        onChange: function (picker, values, displayValues) {
          console.log(values);
        }
      })
      $("#years-monthes").datetimePicker({
        title: '限定年月',
        years: [2017, 2018],
        monthes: ['06', '07'],
        onChange: function (picker, values, displayValues) {
          console.log(values);
        }
      });
    </script>
</body>
</html>