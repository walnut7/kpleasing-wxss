<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta content="telephone=no" name="format-detection">
<link rel="stylesheet" href="${ctx}/css/weui.min.css">
<link rel="stylesheet" href="${ctx}/css/jquery-weui.min.css">
<link rel="stylesheet" href="${ctx}/css/wxss.css">
<script type="text/javascript" src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-weui.min.js"></script>
<script type="text/javascript"
	src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
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
				<span id="NavTitle">驾照正本信息</span>
				<div style="width: 64px"></div>
			</div>
		</div>
		<div class="weui-tab__bd">
			<div class="weui-tab__bd-item weui-tab__bd-item--active">
				<div class="upload">
					<div class="upload_content">
						<div id="previewA">
							<c:choose>
								<c:when test="${!empty driver.driveImagePath}">
									<img id="imgheadA" border="0"
										src="${ctx}${driver.driveImagePath}"
										style="border-radius: 10px;"
										onclick="$('#previewImgA').click();">
								</c:when>
								<c:otherwise>
									<img id="imgheadA" border="0" src="${ctx}/image/carclickfront.png"
										style="width: 65%; height: 100%; border-radius: 0px;"
										onclick="$('#previewImgA').click();">
								</c:otherwise>
							</c:choose>
						</div>
						<input type="file" onchange="previewImage(this,'A')"
							style="display: none;" id="previewImgA">
					</div>
				</div>
				<div class="upload">
					<div class="upload_content">
						<div id="previewB">
							<c:choose>
								<c:when test="${!empty driver.driveBackImagePath}">
									<img id="imgheadB" border="0"
										src="${ctx}${driver.driveBackImagePath}"
										style="border-radius: 10px;"
										onclick="$('#previewImgB').click();">
								</c:when>
								<c:otherwise>
									<img id="imgheadB" border="0" src="${ctx}/image/carclickback.png"
										style="width: 65%; height: 100%; border-radius: 0px;"
										onclick="$('#previewImgB').click();">
								</c:otherwise>
							</c:choose>
						</div>
						<input type="file" onchange="previewImage(this,'B')"
							style="display: none;" id="previewImgB">
					</div>
				</div>
				<div class="weui-cells weui-cells_form">
					<div class="weui-cell weui-cell_access"
						onclick="javascript:select('18','driveTypeDesc','driveType');">
						<div class="weui-cell__hd">
							<label class="weui-label">准驾车型</label>
						</div>
						<div class="weui-cell__bd">
							<p class="driveTypeDesc"
								style="text-align: right; padding-right: 20px;">${driver.driveTypeDesc}</p>
						</div>
						<div class="weui-cell__ft"></div>
					</div>
					<div class="weui-cell"
						onclick="javascript:selectdate('selectdate', 'driveFirstDate');">
						<div class="weui-cell__hd">
							<label class="weui-label">驾照办理日期</label>
						</div>
						<div class="weui-cell__bd">
							<p class="selectdate"
								style="text-align: right; padding-right: 20px;">
								<fmt:formatDate value="${driver.driveFirstDate}"
									pattern="yyyy年MM月dd日" />
							</p>
						</div>
					</div>
					<div style="display: none;">
						<input name="driveType" type="hidden" value="${driver.driveType}" />
						<input name="driveFirstDate" type="hidden"
							value="${driver.driveFirstDate}" /> <input name="driveImagePath"
							type="hidden" value="${driver.driveImagePath}" /> <input
							name="driveBackImagePath" type="hidden"
							value="${driver.driveBackImagePath}" />
					</div>
				</div>
				<br> <br>
				<div class="wxss-register_btn">
					<div class="wxss-register_btn_div" onclick="nextbtn();">下一步</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	$(function() {
		$(document).ajaxStart(function() {	$.showLoading();}).ajaxStop(function() { $.hideLoading(); });
	});
	
    // 下一步
    function nextbtn() {
        if ($("input[name='driveType']").val() === "") {
            $.alert("准驾车型不能为空");

        } else if ($("input[name='driveFirstDate']").val() === "") {
            $.alert("驾照办理日期不能为空");

        } else if ($("input[name='driveImagePath']").val() === "") {
            $.alert("请上传驾驶证正本照片");

        } else if ($("input[name='driveBackImagePath']").val() === "") {
            $.alert("请上传驾驶证副本照片");

        } else {
            $.ajax({
                type: "POST",
                url: "${ctx}/register/saveDriverInfo",
                datatype: "json",
                data: {
                    'driveType': $("input[name='driveType']").val(),
                    'driveTypeDesc': $("p[class='driveTypeDesc']").text(),
                    'driveFirstDate': $("input[name='driveFirstDate']").val()
                },
                success: function (data) {
                	var obj = eval('(' + data + ')');
                	if(obj.result=="success") {
                		window.location.href = "${ctx}/register/bankInfo";
                	} else {
                    	$.alert(obj.message);
                    }
                },
                error: function () {
                	alert("error");
                }
            }); 
        }
    }

    function selectdate(showName, hideValue) {
        weui.datePicker({
            start: 1960, // 从今天开始
            end: new Date(),
            onConfirm: function (result) {
                $("p[class='"+showName+"']").text(result[0].label + result[1].label + result[2].label)
                $("input[name='"+hideValue+"']").val(result[0].value + "-" + result[1].value + "-" + result[2].value);
            },
            id: 'datePicker'
        });
    }

    
    function isFileSizeLimit(file) {
    	var filesize = Math.round((file.files[0].size)/1024/1024);
		/* if(filesize > 5) {
			$.alert("文件不能大于5M！");
			return false;
		} */
		return true;
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
					if(type =='driverInfoA') {
						$("input[name='driveImagePath']").val("Y");
						$("p[class='driveTypeDesc']").text(jsonObj.driveTypeDesc);
						$("p[class='selectdate']").text(jsonObj.driveFirstDate);
						$("input[name='driveType']").val(jsonObj.driveType);
						$("input[name='driveFirstDate']").val(convertDate(jsonObj.driveFirstDate));
					}else if(type =='driverInfoB'){
						$("input[name='driveBackImagePath']").val("Y");
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
					if(type =='driverInfoA') {
						$("p[class='driveTypeDesc']").text(jsonObj.driveTypeDesc);
						$("p[class='selectdate']").text(jsonObj.driveFirstDate);
						$("input[name='driveType']").val(jsonObj.driveType);
						$("input[name='driveFirstDate']").val(convertDate(jsonObj.driveFirstDate));
						$("input[name='driveImagePath']").val("Y");
					}else if(type =='driverInfoB'){
						$("input[name='driveBackImagePath']").val("Y");
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
    	if(isFileSizeLimit(file)) {
    		$.showLoading();
	    	var type = "driverInfo"+v;
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
    }
    
    function turnback() {
		window.location.href = "${ctx}/register/certInfoB";
	}

    function convertDate(date) {
		return date.substring(0,4)+"-"+date.substring(5,7)+"-"+date.substring(8,10);
    }
</script>
</html>