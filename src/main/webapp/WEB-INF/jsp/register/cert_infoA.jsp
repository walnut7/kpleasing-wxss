<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="https://res.wx.qq.com/open/libs/weuijs/1.1.3/weui.min.js"></script>
<script type="text/javascript" src="${ctx}/js/wxss.js"></script>
<script type="text/javascript" src="${ctx}/js/lrz.all.bundle.js"></script>
</head>
<body ontouchstart>
	<div class="weui-tab">
		<div class="weui-navbar">
			<div class="weui-navbar__item weui-bar__item--on wxss-register_title">
				<div style="width: 64px" class="wxss-register_turnimg"></div>
				<span id="NavTitle">身份证正面信息</span>
				<div style="width: 64px"></div>
			</div>
		</div>
		<div class="weui-tab__bd">
			<div class="weui-tab__bd-item weui-tab__bd-item--active">
				<div class="upload">
					<div class="upload_content">
						<div id="preview">
							<c:choose>
								<c:when test="${!empty cert.certFrontImagePath}">
									<img id="imghead" border="0" src="${ctx}${cert.certFrontImagePath}" style="border-radius: 10px;" onclick="$('#previewImg').click();">
								</c:when>
								<c:otherwise>
									<img id="imghead" border="0" src="${ctx}/image/iscard.png" style="width: 65%; height: 100%; border-radius: 0px;" onclick="$('#previewImg').click();">
								</c:otherwise>
							</c:choose>
						</div>
						<input type="file" onchange="previewImage(this)" style="display: none;" id="previewImg">
					</div>
				</div>
				<div class="weui-cells weui-cells_form">
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">姓名</label>
						</div>
						<div class="weui-cell__bd">
							<c:choose>
								<c:when test="${sessionScope.LOGIN_USER.loginType eq 2}">
									<p class="weui-input">${sessionScope.LOGIN_USER.userName}</p>
									<input type="hidden" name="username" value="${sessionScope.LOGIN_USER.userName}">
								</c:when>
								<c:otherwise>
									<input class="weui-input" type="text" name="username" placeholder="" value="${cert.userName}">
								</c:otherwise>
							</c:choose>
						</div>
					</div>
					<div class="weui-cell weui-cell_access"	onclick="javascript:select('30','selectsex','gender');">
						<div class="weui-cell__hd">
							<label class="weui-label">性别</label>
						</div>
						<div class="weui-cell__bd">
							<p class="selectsex">${cert.gender}</p>
						</div>
						<div class="weui-cell__ft"></div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">民族</label>
						</div>
						<div class="weui-cell__bd">
							<input class="weui-input" type="text" name="national" placeholder="" value="${cert.nation}">
						</div>
					</div>
					<div class="weui-cell weui-cell_access"
						onclick="javascript:selectdate('selectdate', 'birthday');">
						<div class="weui-cell__hd">
							<label class="weui-label">出生日期</label>
						</div>
						<div class="weui-cell__bd">
							<p class="selectdate">
								<fmt:formatDate value="${cert.birthday}" pattern="yyyy年MM月dd日" />
							</p>
						</div>
						<div class="weui-cell__ft"></div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">住址</label>
						</div>
						<div class="weui-cell__bd">
							<textarea class="weui-textarea" name="address" placeholder="" rows="2">${cert.liveAddr}</textarea>
						</div>
					</div>
					<div class="weui-cell">
						<div class="weui-cell__hd">
							<label class="weui-label">证件号码</label>
						</div>
						<div class="weui-cell__bd">
							<c:choose>
								<c:when test="${sessionScope.LOGIN_USER.loginType eq 2}">
									<p class="weui-input">${sessionScope.LOGIN_USER.certCode}</p>
									<input type="hidden" name="cardnumber" value="${sessionScope.LOGIN_USER.certCode}">
								</c:when>
								<c:otherwise>
									<input class="weui-input" type="text" name="cardnumber" placeholder="" value="${cert.certId}">
								</c:otherwise>
							</c:choose>
							
						</div>
					</div>
					<div style="display: none;">
						<input name="birthday" type="hidden" value="${cert.birthday}" />
						<input name="gender" type="hidden" value="${cert.genderCode}" />
						<input name="certFrontImagePath" type="hidden" value="${cert.certFrontImagePath}" />
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
	$(function() {
		$(document).ajaxStart(function() {	$.showLoading();}).ajaxStop(function() { $.hideLoading(); });
	});

	//身份证号合法性验证 	
	function IdentityCodeValid(code) { 
	    var city={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江 ",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北 ",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏 ",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外 "};
	    var tip = "";
	    var pass= true;
	
	    if(!code || !/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/.test(code)){
	        tip = "身份证号格式错误";
	        pass = false;
	    }
	
	   else if(!city[code.substr(0,2)]){
	        tip = "身份证号地址编码错误";
	        pass = false;
	    }
	   else if(!checkBirthDayCode(code)){
	        tip = "身份证号生日编码错误";
	        pass = false;
	    }
	    else{
	        //18位身份证需要验证最后一位校验位
	        if(code.length == 18){
	            code = code.split('');
	            //∑(ai×Wi)(mod 11)
	            //加权因子
	            var factor = [ 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 ];
	            //校验位
	            var parity = [ 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 ];
	            var sum = 0;
	            var ai = 0;
	            var wi = 0;
	            for (var i = 0; i < 17; i++)
	            {
	                ai = code[i];
	                wi = factor[i];
	                sum += ai * wi;
	            }
	            var last = parity[sum % 11];
	            if(parity[sum % 11] != code[17]){
	                tip = "身份证号校验位错误";
	                pass =false;
	            }
	        }
	    }
	    if(!pass) $.alert(tip);
	    return pass;
	}

	/*校验生日编码*/
	function checkBirthDayCode(code){
		var birDayCode = "";
		if(code.length == 18){
			birDayCode = code.substring(6,14);
	 	}else if(code.length == 15){
			birDayCode = '19' + code.substring(6,12);
	    }
	    var check = /^[1-9]\d{3}((0[1-9])|(1[0-2]))((0[1-9])|([1-2][0-9])|(3[0-1]))$/.test(birDayCode);
	    if(!check) return false;  
	    var yyyy = parseInt(birDayCode.substring(0,4),10);
	    var mm = parseInt(birDayCode.substring(4,6),10);
	    var dd = parseInt(birDayCode.substring(6),10);
	    var xdata = new Date(yyyy,mm-1,dd);
	    if(xdata > new Date()){
	    	return false;//生日不能大于当前日期
	  	}else {
	   		return true;
	  	}
	}

    // 下一步
    function nextbtn() {
        //身份证号码校验
	    if ($("input[name='cardnumber']").val().trim() === "") {
	        $.alert("证件号码不能为空");
	        return;
	    }else{
	    	var idCardValid = IdentityCodeValid($("input[name='cardnumber']").val().trim());
	    	if(!idCardValid){
				return;
	        }
	    }
        
        if ($("input[name='username']").val() === "") {
            $.alert("姓名不能为空");

        } else if ($("input[name='gender']").val() == "") {
            $.alert("性别不能为空");

        } else if ($("input[name='national']").val() == "") {
            $.alert("民族不能为空");

        } else if ($("input[name='birthday']").val() == "") {
            $.alert("出生日期不能为空");

        } else if ($("textarea[name='address']").val() == "") {
            $.alert("住址不能为空");

        }else if ($("input[name='certFrontImagePath']").val() == "") {
            $.alert("请上传身份证正面照片");

        } else { 
        	$.ajax({
    			type : "POST",
    			url : "${ctx}/register/saveCertInfoA",
    			datatype : "json",
    			data : {
    				'userName' : $("input[name='username']").val(),
    				'gender' : $("p[class='selectsex']").text(),
    				'genderCode' : $("input[name='gender']").val(),
    				'nation' : $("input[name='national']").val(),
    				'birthday' : $("input[name='birthday']").val(),
    				'liveAddr' : $("textarea[name='address']").val(),
    				'certId' : $("input[name='cardnumber']").val()
    			},
    			success : function(data) {
					var obj = eval('(' + data + ')');
                	if(obj.result=="success") {
                		window.location.href = "${ctx}/register/certInfoB";
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

    function uploadImage(file, img, type) {
    	$.showLoading();
    	var img_size=file.files[0].size;
		var i_quality=0.9;
		if (img_size>1048576){
			i_quality=1048576/img_size;
		}

    	lrz(file.files[0], { quality: i_quality }).then(function(rst) {
    		$.hideLoading();
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
			    				$("input[name='certFrontImagePath']").val("Y"); //是否上传附件标识
								jsonObj = eval('(' + data + ')');
								$("input[name='username']").val(jsonObj.userName);
								$("p[class='selectsex']").text(jsonObj.gender);
			    				$("input[name='gender']").val(jsonObj.genderCode);
			    				$("input[name='national']").val(jsonObj.nation);
			    				$("p[class='selectdate']").text(jsonObj.birthday);
			    				$("input[name='birthday']").val(convertDate(jsonObj.birthday));
			    				$("textarea[name='address']").val(jsonObj.liveAddr);
			    				$("input[name='cardnumber']").val(jsonObj.certId);
							},
							error : function() {
								$.hideLoading();
								$.alert("读取失败，请重新上传！");
							}
						});
        }); 
      
       /*  var reader = new FileReader();
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
					$("input[name='username']").val(jsonObj.userName);
					$("p[class='selectsex']").text(jsonObj.gender);
    				$("input[name='gender']").val(jsonObj.genderCode);
    				$("input[name='national']").val(jsonObj.nation);
    				$("p[class='selectdate']").text(jsonObj.birthday);
    				$("input[name='birthday']").val(convertDate(jsonObj.birthday));
    				$("textarea[name='address']").val(jsonObj.liveAddr);
    				$("input[name='cardnumber']").val(jsonObj.certId);
    				$("input[name='certFrontImagePath']").val("Y"); //是否上传附件标识
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
    function previewImage(file) {
    		$.showLoading();
    		var div = document.getElementById('preview');
            if (file.files && file.files[0]) {
                div.innerHTML = '<img id=imghead onclick=$("#previewImg").click()>';
                var img = document.getElementById('imghead');
                img.onload = function () {
                }
	            uploadImage(file, img, "certA");
	            
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

    function convertDate(date) {
        try {
			return date.substring(0,4)+"-"+date.substring(5,7)+"-"+date.substring(8,10);
        } catch(err) {
			return "1990年01月0日";
        }
    }
</script>
</html>