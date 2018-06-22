<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include.inc.jsp"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.lang.Double"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<c:set var="ctx" value="${pageContext['request'].contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="UTF-8">
<title>迅信通</title>
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=contain">
<link rel="stylesheet" href="${ctx}/font/iconfont.css">
<link rel="stylesheet" href="${ctx}/css/reset.css">
<script src="${ctx}/js/jquery-3.2.1.min.js"></script>
<script src="${ctx}/js/scrollfix.js"></script>
<script src="${ctx}/js/fastclick.js"></script>
<script src="${ctx}/js/lxl.js"></script>
<script type="text/javascript" src="${ctx}/js/common_util.js"></script>
</head>
<body style="padding-bottom:constant(safe-area-inset-bottom)">
	<div id="scrollMain" class="pb15 ofy_auto">
		<img id="seriesLogo_id"
			src="http://test.e-autofinance.net:8080/kp_test/${car.seriesLogo}"
			alt="" class="wp100">
		<!-- 增配选项 -->
		<div class="flx flx_m bg_white plist mb8">
			<div class="flx_1">
				<p class="fs20 bold mb8" id="model_id">${car.brand}</p>
				<p class="fs14 mb6">${car.series}</p>
				<p class="fs14 mb6">${car.model}</p>
				<p class="fs12 clr_orange">指导价 ${car.msrp}</p>
			</div>
		</div>
		<!-- 最新报价 -->
		<div class="flx flx_m bg_white plist line_btm">
			<div class="flx_1">
				<p class="fs16 mb8">
					购置税：<span id="purchaseTax_id">${car.purchaseTax}</span>元
				</p>
				<p class="fs16 flx flx_m">
					1年保费：<span id="insuranceFeeFinancing_id">${car.insuranceFeeFinancing}</span>元

				</p>
			</div>

		</div>
		<!-- 几年期税率 -->
		<div class="bg_white line_btm">
			<p class="h30 flx flx_m line_btm_15 pl15 pr15">1年期</p>
			<c:forEach items="${schemes}" var="scheme">
				<c:if test="${scheme.leaseTimes==12}">
					<div class="flx flx_m plist tax cur" _planId="${scheme.planId}">
						<div class="circle flx flx_m flx_c">
							<p></p>
						</div>
						<div class="flx_1 pl15">
							<c:if test="${scheme.depositAmount >0 }">
								<p class="mb5">
									保证金：￥<span><c:out value="${scheme.depositAmount}" /></span>元
								</p>
							</c:if>

							<c:if test="${scheme.downpaymentAmount >0 }">
								<p class="mb5">
									首付款：￥<span><c:out value="${scheme.downpaymentAmount}" /></span>元
								</p>
							</c:if>
							<p>
								月租金：￥<span><c:out value="${scheme.rental}" /></span>元
							</p>
						</div>
						<p class="fs16 clr_orange">
							年利率：<span><fmt:formatNumber value="${scheme.intRate*100}" type="number" maxFractionDigits="2"/>
							%</span>
						</p>
					</div>
				</c:if>
			</c:forEach>

		</div>
		<div class="bg_white line_btm">
			<p class="h30 flx flx_m line_btm_15 pl15 pr15">2年期</p>
			<c:forEach items="${schemes}" var="scheme">
				<c:if test="${scheme.leaseTimes==24}">
					<div class="flx flx_m plist tax cur" id="plan_${scheme.planId}">
						<div class="circle flx flx_m flx_c" _planId="${scheme.planId}">
							<p></p>
						</div>
						<div class="flx_1 pl15">
							<c:if test="${scheme.depositAmount >0 }">
								<p class="mb5">
									保证金：￥<span><c:out value="${scheme.depositAmount}" /></span>元
								</p>
							</c:if>

							<c:if test="${scheme.downpaymentAmount >0 }">
								<p class="mb5">
									首付款：￥<span><c:out value="${scheme.downpaymentAmount}" /></span>元
								</p>
							</c:if>
							<p>
								月租金：￥<span><c:out value="${scheme.rental}" /></span>元
							</p>
						</div>
						<p class="fs16 clr_orange">
							年利率：<span><fmt:formatNumber value="${scheme.intRate*100}" type="number" maxFractionDigits="2"/>
							%</span>
						</p>
					</div>
				</c:if>
			</c:forEach>
		</div>
		<div class="bg_white line_btm">
			<p class="h30 flx flx_m line_btm_15 pl15 pr15">3年期</p>
			<c:forEach items="${schemes}" var="scheme">
				<c:if test="${scheme.leaseTimes==36	 }">
					<div class="flx flx_m plist tax cur" id="plan_${scheme.planId}">
						<div class="circle flx flx_m flx_c" _planId="${scheme.planId}">
							<p></p>
						</div>
						<div class="flx_1 pl15">
							<c:if test="${scheme.depositAmount >0 }">
								<p class="mb5">
									保证金：￥<span><c:out value="${scheme.depositAmount}" /></span>元
								</p>
							</c:if>

							<c:if test="${scheme.downpaymentAmount >0 }">
								<p class="mb5">
									首付款：￥<span><c:out value="${scheme.downpaymentAmount}" /></span>元
								</p>
							</c:if>
							<p>
								月租金：￥<span><c:out value="${scheme.rental}" /></span>元
							</p>
						</div>
						<p class="fs16 clr_orange">
							年利率：<span><fmt:formatNumber value="${scheme.intRate*100}" type="number" maxFractionDigits="2"/>
							%</span>
						</p>
					</div>
				</c:if>
			</c:forEach>
		</div>

		<div class="bg_white line_btm">
			<p class="h30 flx flx_m line_btm_15 pl15 pr15">4年期</p>
			<c:forEach items="${schemes}" var="scheme">
				<c:if test="${scheme.leaseTimes==48 && scheme.planType!='XMC'	 }">
					<div class="flx flx_m plist tax cur" id="plan_${scheme.planId}">
						<div class="circle flx flx_m flx_c" _planId="${scheme.planId}">
							<p></p>
						</div>
						<div class="flx_1 pl15">
							<c:if test="${scheme.depositAmount >0 }">
								<p class="mb5">
									保证金：￥<span><c:out value="${scheme.depositAmount}" /></span>元
								</p>
							</c:if>

							<c:if test="${scheme.downpaymentAmount >0 }">
								<p class="mb5">
									首付款：￥<span><c:out value="${scheme.downpaymentAmount}" /></span>元
								</p>
							</c:if>
							<p>
								月租金：￥<span><c:out value="${scheme.rental}" /></span>元
							</p>
						</div>
						<p class="fs16 clr_orange">
							年利率：<span>
							<fmt:formatNumber value="${scheme.intRate*100}" type="number" maxFractionDigits="2"/>
							%</span>
						</p>
					</div>
				</c:if>
			</c:forEach>
		</div>

		<!-- 1加3方案 -->
		<div class="bg_white line_btm">
			<p class="h30 flx flx_m line_btm_15 pl15 pr15">1+3方案</p>
			<c:forEach items="${schemes}" var="scheme">
				<c:if test="${scheme.planType=='XMC'	 }">
					<div class="flx flx_m plist tax cur" id="plan_${scheme.planId}">
						<div class="circle flx flx_m flx_c" _planId="${scheme.planId}">
							<p></p>
						</div>
						<div class="flx_1 pl15">
							<c:if test="${scheme.downpaymentAmount >0 }">
								<p class="mb5">
									首付款：￥<span><c:out value="${scheme.downpaymentAmount}" /></span>元
								</p>
							</c:if>
							<c:if test="${scheme.depositAmount >0 }">
								<p class="mb5">
									保证金：￥<span><c:out value="${scheme.depositAmount}" /></span>元
								</p>
							</c:if>
							<p>
								月租金：￥<span><c:out value="${scheme.rental}" /></span>元
							</p>
						</div>
						<div class="fs12">
							<p class="mb3">
								1年买断价：<span><c:out value="${scheme.buyoutAmount}" /></span>元
							</p>
							<p class="mb3">
								1-12期租金：<span><c:out value="${scheme.rental112}" /></span>元
							</p>
							<p>
								13-48期租金：<span><c:out value="${scheme.rental1348}" /></span>元
							</p>
						</div>
					</div>
				</c:if>
			</c:forEach>
		</div>

		<!-- 议价方案 -->
		<!-- 
		<div class="bg_white line_btm">
			<p class="h30 flx flx_m line_btm_15 pl15 pr15">议价方案</p>
			<div class="flx flx_m plist  tax cur" id="plan_0">
				<div class="circle flx flx_m flx_c   tax cur" _planId="">
					<p></p>
				</div>
				<div class="flx_1 pl15 ">
					<p class="mb5">
						保证金：<span><c:out value="待议" /></span>
					</p>
					<p>
						月租金：<span><c:out value="待议" /></span>
					</p>
				</div>
				<p class="fs16 clr_orange">
					年利率：<span><c:out value="待议" /></span>
				</p>
			</div>
		</div>
	</div>
	 -->
</div>
<!-- 底部菜单 -->
		<div class="flx btmMenu wp100 line_top">
			<p class="pl15 pr15 flx flx_m mw100">
				<span id="totalPrice"></span>
			</p>
			<div class="flx_1 flx flx_m flx_c bg_orange clr_fff btn_active cur"
				id="bugBtn">订购</div>
		</div>
		<script>
			$(document)
					.on(
							"click",
							"#bugBtn",
							function() {

								if ('${car.modelId}' == '') {
									return;
								}

								var choose = $(document).find('.circleChoose');
								if (choose.length == 0) {
									alert("请选择方案");
									return;
								}

								var planId = choose[0].getAttribute("_planId");
								var bpId = "${sp.bpId}";
								var bpCode = "${sp.bpCode}";

								window.location.href = "${ctx}/login?bpId=${sp.bpId}&bpCode=${sp.bpCode}&modelId=${car.modelId}&planId="
										+ planId;
							})

			$(document).on("click", ".tax", function() {
				$(".circle").removeClass('circleChoose');
				$(this).find('.circle').addClass("circleChoose");
			});

			$(document)
					.ready(
							function() {
								//不显示不存在的期限
								var divs = $("div[class='bg_white line_btm']");
								for (var i = 0; i < divs.length; i++) {
									var dis = $(divs[i]);
									var plans_div = dis
											.children("div[class='flx flx_m plist tax cur']");
									if (plans_div.length == 0) {
										dis.hide();
									}
								}

							});
		</script>
</body>
</html>