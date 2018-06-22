<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() + path+"/";
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}"/>
<c:set var="now" value="<%=new java.util.Date()%>" />
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
</head>
<body ontouchstart>
      <div class="weui-tab">
          <div class="weui-navbar">
              <div class="weui-navbar__item weui-bar__item--on wxss-register_title">
              	<div style="width:64px" class="wxss-register_turnimg" onclick="turnback()">
		            <img src="${ctx}/image/turnback.png" alt="" class="weui-tabbar__icon">
		        </div>
		        <span id="NavTitle">信息确认</span>
		        <div style="width:64px"></div>
              </div>
          </div>
          <div class="weui-tab__bd">
          	<div class="weui-tab__bd-item weui-tab__bd-item--active">
          	 <div class="weui-form-preview">
            <div class="weui-form-preview__hd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">身份证信息</label>
                </div>
            </div>
            <div class="weui-form-preview__bd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">姓名</label>
                    <span class="weui-form-preview__value">${order.certInfo.userName}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">性别</label>
                    <span class="weui-form-preview__value">${order.certInfo.gender}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">民族</label>
                    <span class="weui-form-preview__value">${order.certInfo.nation}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">出生年月</label>
                    <span class="weui-form-preview__value">${order.certInfo.birthday}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">住址</label>
                    <span class="weui-form-preview__value">${order.certInfo.liveAddr}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">证件号码</label>
                    <span class="weui-form-preview__value">${order.certInfo.certId}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">签发机关</label>
                    <span class="weui-form-preview__value">${order.certInfo.certAddr}</span>
                </div>
            </div>
        </div>
        <br>
        <div class="weui-form-preview">
            <div class="weui-form-preview__hd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">驾照信息</label>
                </div>
            </div>
            <div class="weui-form-preview__bd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">准驾车型</label>
                    <span class="weui-form-preview__value">${order.drivingLicenseInfo.driveTypeDesc}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">驾驶证领取日期</label>
                    <span class="weui-form-preview__value">${order.drivingLicenseInfo.driveFirstDate}</span>
                </div>
            </div>
        </div>
        <br>
        <div class="weui-form-preview">
            <div class="weui-form-preview__hd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">银行卡信息</label>
                </div>
            </div>
            <div class="weui-form-preview__bd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">借记卡开户行</label>
                    <span class="weui-form-preview__value">${order.bankInfo.repayAccBank}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">借记卡号</label>
                    <span class="weui-form-preview__value">${order.bankInfo.repayCardNo}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">借记卡手机号</label>
                    <span class="weui-form-preview__value">${order.bankInfo.bankPhone}</span>
                </div>
            </div>
        </div>
        <br>
        <div class="weui-form-preview">
            <div class="weui-form-preview__hd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">个人信息</label>
                </div>
            </div>
            <div class="weui-form-preview__bd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">住房情况</label>
                    <span class="weui-form-preview__value">${order.personInfo.liveStatus}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">学历</label>
                    <span class="weui-form-preview__value">${order.personInfo.eduLevel}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">家庭电话</label>
                    <span class="weui-form-preview__value">${order.personInfo.familyPhone}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">居住地址</label>
                    <span class="weui-form-preview__value">${order.personInfo.province} ${order.personInfo.city} ${order.personInfo.familyAddr}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">邮箱</label>
                    <span class="weui-form-preview__value">${order.personInfo.email}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">婚姻状况</label>
                    <span class="weui-form-preview__value">${order.personInfo.marrStatus}</span>
                </div>
            </div>
         </div>
         <br>
         <c:choose>
	       <c:when test="${order.personInfo.marrStatusCode eq 'MARRIED'}">
	       <div class="weui-form-preview">
	       	   <div class="weui-form-preview__hd">
	               <div class="weui-form-preview__item">
	                   <label class="weui-form-preview__label">配偶信息</label>
	               </div>
	           </div>
	           <div class="weui-form-preview__bd">
			       <div class="weui-form-preview__item">
	                   <label class="weui-form-preview__label">姓名</label>
	                   <span class="weui-form-preview__value">${order.personInfo.spouseName}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                   <label class="weui-form-preview__label">身份证</label>
	                   <span class="weui-form-preview__value">${order.personInfo.spouseCertId}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                   <label class="weui-form-preview__label">手机号</label>
	                   <span class="weui-form-preview__value">${order.personInfo.spousePhone}</span>
	                </div>
	                <div class="weui-form-preview__item">
	                   <label class="weui-form-preview__label">月收入</label>
	                   <span class="weui-form-preview__value">${order.personInfo.spouseAnnualIncome}</span>
	                </div>
               </div>
           </div>
	       </c:when>
	       <c:otherwise>
	         <div class="weui-form-preview">
	           <div class="weui-form-preview__hd">
	               <div class="weui-form-preview__item">
	                   <label class="weui-form-preview__label">紧急联系人信息</label>
	               </div>
	           </div>
	           <div class="weui-form-preview__bd">
		       	   <div class="weui-form-preview__item">
	                   <label class="weui-form-preview__label">关系</label>
	                   <span class="weui-form-preview__value">${order.personInfo.contactRelation}</span>
	                  </div>
	                  <div class="weui-form-preview__item">
	                   <label class="weui-form-preview__label">姓名</label>
	                   <span class="weui-form-preview__value">${order.personInfo.contactName}</span>
	                  </div>
	                  <div class="weui-form-preview__item">
	                   <label class="weui-form-preview__label">身份证</label>
	                   <span class="weui-form-preview__value">${order.personInfo.contactCertId}</span>
	                  </div>
	                  <div class="weui-form-preview__item">
	                   <label class="weui-form-preview__label">手机号</label>
	                   <span class="weui-form-preview__value">${order.personInfo.contactPhone}</span>
	                  </div>
	               </div>
	           </div>
	       </c:otherwise>
		</c:choose>
        <br>
        <div class="weui-form-preview">
            <div class="weui-form-preview__hd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">工作信息</label>
                </div>
            </div>
            <div class="weui-form-preview__bd">
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">工作单位</label>
                    <span class="weui-form-preview__value">${order.workInfo.workUnit}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">入职年限</label>
                    <span class="weui-form-preview__value">${order.workInfo.entryYear}年</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">职务</label>
                    <span class="weui-form-preview__value">${order.workInfo.position}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">单位电话</label>
                    <span class="weui-form-preview__value">${order.workInfo.unitTel}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">月收入状态</label>
                    <span class="weui-form-preview__value">${order.workInfo.incomeStatus}</span>
                </div>
                <div class="weui-form-preview__item">
                    <label class="weui-form-preview__label">收入来源</label>
                    <span class="weui-form-preview__value">${order.workInfo.incomeFrom}</span>
                </div>
            </div>
        </div>
        <c:choose>
			<c:when test="${sessionScope.LOGIN_USER.loginType eq 1}">
				<div class="weui-cells__tips">
			       <input id="weuiAgree" type="checkbox" class="weui-agree__checkbox"/> 
			       <span class="weui-agree__text">阅读并同意
			       	   <a href="javascript:;" class="open-popup" data-target="#showItems">《个人征信授权声明》</a>
			       </span>
			    </div>
			</c:when>
			<c:otherwise>
				<input id="weuiAgree" type="checkbox" checked="checked" style="display:none;"/> 
			</c:otherwise>
		</c:choose>
	    <div class="wxss-register_btn">
		    <div id="btnSubmit" class="wxss-register_btn_div" onclick="nextbtn();">提交</div>
		</div>
      </div>
  	</div>
</div>

<div id="showItems" class='weui-popup__container'>
  <div class="weui-popup__overlay"></div>
  <div class="weui-popup__modal">
	<p>申请人声明：	</p>
	<p style="text-indent:1em;">
	本人拟以向贵公司申请车辆融资租赁业务，贵公司需要了解本人的信用记录，用于该项业务，因此本人特做出以下授权并清楚理解其含义：
	</p>
	<p style="text-indent:1em;">
	1.本人同意授权贵公司通过依法成立的征信机构查询本人的征信信息多次，授权期限为业务存续期；
	</p>
	<p style="text-indent:1em;">
	2.本人同意授权贵公司向依法成立的征信机构提供因本业务产生的本人的信息（包括本人在本业务中产生的不良信息，且无须另行告知本人）；
	</p>
	<p style="text-indent:1em;">
	3.本人同意授权依法成立的征信机构进行以下事项：
	</p>
	<p style="text-indent:1em;">
	(1)同意授权依法成立的征信机构向有关机构采集本人的个人信息，可依法整理、保存，加工、使用、提供，并出具个人信用报告；
	</p>
	<p style="text-indent:1em;">
	(2)同意授权拥有本人不良信息的信息提供者可以向依法成立的征信机构提供本人的不良信息，且无须另行告知本人；
	</p>
	<p style="text-indent:1em;">
	(3)同意授权依法成立的征信机构向信息提供者采集本人收入、存款、有价证券、商业保险、不动产的信息和纳税数额信息。本人已被明确告知提供上述信息可能会给本人带来财产或信用损失，以及其他可能的不利后果，包括但不限于：采集这些信息对本人的信用评级（评分）、信用报告等结果可能产生的不利影响，以及该等信息被信息使用者依法提供给第三方后被他人不当利用的风险。但本人仍然同意授权依法成立的征信机构采集这些信息。特别提示：为了保障您的合法权益,您应当阅读并遵守本授权书。请您务必审慎阅读，并充分理解本授权书的全部内容。同时，我们公司将依法对信息保密，履行信息安全保障义务。若您不接受本授权书的任何条款，请您立即停止授权。特别声明：本授权书经接受后即时生效,且效力具有独立性,不因相关业务合同或条款无效或被撤销而无效或失效，本授权一经做出，便不可撤消。本人已知悉本授权书全部内容的含义及因此产生的法律效力，自愿做出以上授权。
	</p>
	<p style="text-indent:1em;">
	上海浦东发展银行股份有限公司：
	</p>
	<p style="text-indent:1em;">
	本人（“授权人”）在此不可撤销地授权贵行（“被授权人”）根据本授权书查询和提供授权人的信息。
	</p>
	★一、授权事项
	<p style="text-indent:1em;">
	1、授权人个人信息的查询和使用
	</p>
	<p style="text-indent:1em;">
	授权人在办理本业务时，被授权人依法可以通过如下途径获取您的以下信息：
	</p>
	<p style="text-indent:1em;">
	向中国人民银行金融信用信息基础数据库及其他经中国人民银行批准设立的征信机构查询授权人的全部信用信息及信用报告。
	向资信评估机构、风险管理专业机构或有关法律、监管机构许可的类似数据机构查询、核实、打印、留存您的信息和（或）信用报告。
	向行政机关、事业单位、司法机关、监管部门、中国互联网金融协会等行业自律组织查询、打印、留存任何与本业务相关信息。
	</p>
	<p style="text-indent:1em;">
	2、授权信息范围：
	</p>
	<p style="text-indent:1em;">
		授权人同意被授权人收集的信息包括但不限于：
		授权人的姓名/名称、证件类型及号码、户籍所在地、住所地、经常居住地、电话号码、在被授权人处开立的账户认证信息、人脸识别所需生物特征以及其他身份信息。
		授权人在申请、办理本业务时，访问本行服务网站或业务移动设备客户端时的操作日志、包括但不限于授权人的计算机IP地址、设备标识符、硬件型号、操作系统版本、授权人位置信息等。
		授权人在申请、办理本业务时所提供、形成的任何数据和信息，包括但不限于授权人提供的关联数据信息.
		授权人的财产信息，包括但不限于授权人的财税信息、房产信息、车辆信息、基金、保险、股票、信托、债券等投资理财信息和负债信息等。
		授权人在各机构留存的任何信息，包括但不限于授权人作为法定代表人或负责人或经营者或股东的工商信息、诉讼信息、执行信息和违法犯罪信息等。
		授权人在此同意并确认，授权人在前述信息提供给被授权人之前，已清楚知晓可能带来的不利后果，包括但不限于基于前述信息对授权人作出的负面评价。
	</p>
	<p style="text-indent:1em;">3、授权人信息的对外提供</p>
	<p style="text-indent:1em;">
		根据征信相关法律法规和监管规定的要求，被授权人有权将在双方相关授信业务关系建立和存续期间获得的与本授权人有关的信息，包括信贷信息、信用信息、个人基本信息、不良信息和其他信息资料，提供给金融信用信息基础数据库及其他经中国人民银行批准设立的征信机构的数据库。
	</p>
	★二、信息使用
	<p style="text-indent:1em;">
		授权信息用于审核本人授信和贷款申请，以及对已发放的个人信贷进行贷后管理；
		基于本人/本单位资格审查、授信方案制定、信贷资产质量监控、债务追索、担保方案制定、担保资产质量监控、担保责任追索、关联关系查询及其他为确保实现被授权人在相关授信业务合同下利益之目的，被授权人有权查询、使用和保存前述信用信息及信用报告。
	</p>
	★三、授权期限
	<p style="text-indent:1em;">
		授权期限自本授权书载明的签署日期起至授信意向下发生的业务（如有）完全履行完毕之日止。如被授权人经审核拒绝接受本授权人的申请，则授权期限至被授权人正式通知本授权人之日止。
	</p>
	★四、其他
	<p style="text-indent:1em;">
	如被授权人超出本授权书的范围违法查询和使用本授权人的信用信息，被授权人应承担相应的法律责任。
	本授权人知悉和完全理解本授权书的内容，尤其是标记★的条款，本授权书下的授权是本授权人自愿作出的真实意思表示，并愿意承担相应的法律责任。经本授权人通过在线确认同意即视为签署本授权书并生效。
	</p>
	<p>授权人：${order.certInfo.userName}</p>
	<p>证件类型：身份证</p>
	<p>证件号码：${order.certInfo.certId}</p>
	<p>授权日期：<fmt:formatDate value="${now}" pattern="yyyy年MM月dd日" /></p>
	<div class="wxss-register_btn">
	    <div class="wxss-register_btn_div close-popup">确定</div>
	</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$(document).ajaxStart(function() {	$.showLoading();}).ajaxStop(function() { $.hideLoading(); });
		$(".open-popup").click(function() {
			$(".weui-tab").css("display","none");
		});
		
		$(".close-popup").click(function() {
			$(".weui-tab").css("display","block");
		});
	});


	function nextbtn() {
        if ($("#weuiAgree").prop("checked") === true) {
        	$("#btnSubmit").removeClass("wxss-register_btn_div").addClass("wxss-register_btn_disabled_div").removeAttr("onclick");
            $.ajax({
                type: "POST",
                url: "${ctx}/register/submitInfo",
                datatype: "json",
                data: { },
                success: function (data) {
                	var obj = eval('(' + data + ')');
                	
                	if(obj.result=="success") {
                		window.location.href = "${ctx}/register/faceVideo";
                	} else {
                    	$.alert(obj.message);
                    	$("#btnSubmit").removeClass("wxss-register_btn_disabled_div").addClass("wxss-register_btn_div").attr("onclick","nextbtn();"); 
                    }
                },
                error: function () {
                	$.alert("提交失败，请重试！");
                    $("#btnSubmit").removeClass("wxss-register_btn_disabled_div").addClass("wxss-register_btn_div").attr("onclick","nextbtn();"); 
                }
            });
        } else {
			$.alert("请阅读并同意《个人征信授权声明》");
			return;
        }
    }
	
	function turnback() {
		window.location.href = "${ctx}/register/workInfo";
	}
</script>
</body>
</html>