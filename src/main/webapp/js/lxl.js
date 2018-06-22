//MSRP价
var MSRP_price = 791169;
//总价
var totalPrice=0;
//车辆开票价
var ticket_price = 791169;
//购置税
var purchaseTax = 0;
//计算保费
var premiumPrice =0;

//1+3首付款
var downpayment=118675;
//1+3月租金
var rental=17294;
//1+3买断价
var buyout=553818;
//1-12期租金
var rental_1_12=17294;
//13-48期租金
var rental_13_48=19198;


var check_1='0';
var check_2='0';
var check_3='0';

var initResidualData={
	"000":{
	downpayment:118675,
	rental:17294,
	buyout:553818,
	rental_1_12:17294,
	rental_13_48:19198
	},
	"100":{
	downpayment: 119425,
	rental: 17402,
	buyout: 557318 ,
	rental_1_12:17402,
	rental_13_48: 19320
	},
	"010":{
	downpayment:119575,
	rental:17424,
	buyout:558018,
	rental_1_12:17424,
	rental_13_48:19344
	},
	"001":{
	downpayment:119500,
	rental:17413,
	buyout:557668,
	rental_1_12:17413,
	rental_13_48:19332
	},
	"110":{
	downpayment:120325,
	rental:17532,
	buyout:561518,
	rental_1_12:17532,
	rental_13_48:19465
	},
	"101":{
	downpayment:120250,
	rental:17521,
	buyout:561168,
	rental_1_12:17521,
	rental_13_48:19453
	},
	"011":{
	downpayment:120400,
	rental:17543,
	buyout:561868,
	rental_1_12:17543,
	rental_13_48:19477
	},
	"111":{
	downpayment:121150,
	rental:17651,
	buyout:565368,
	rental_1_12:17651,
	rental_13_48:19599,
	}
}

//配置一
var allocationFir_price = 5000;
//配置二
var allocationSec_price = 6000;
//配置三
var allocationThr_price = 5500;
//一年期利率
var oneYearRate = 3.88;
//二年期利率
var twoYearRate = 3.88;
//三年期利率
var thrYearRate = 3.88;
var carDetail = {
	init:function(){
		//调用ScrollFix插件解决IOS滚动兼容
		var btmH = $(".btmMenu").height();
		var winH = $(window).height();
		$('#scrollMain').css("height",winH-btmH+'px');
		new ScrollFix($('#scrollMain')[0]);
		
		//初始化总价
		this.calcTotal();
		//绑定DOM
		this.bindDom();
		//计算总价
		this.countTotalPrice();
	},
	calcTotal:function(){
		//设置年利率
		$('#oneYearRate').html(oneYearRate);
		$('#twoYearRate').html(twoYearRate);
		$('#thrYearRate').html(thrYearRate);
		
		//三个配置
		$('#allocationFir_price').html(allocationFir_price);
		$('#allocationSec_price').html(allocationSec_price);
		$('#allocationThr_price').html(allocationThr_price);
		
		//1+3方案
		$('#1_3_downpayment').html(downpayment);
		$('#1_3_rental').html(rental);
		$('#1_3_buyout').html(buyout);
		$('#1_3_12_rental').html(rental_1_12);
		$('#1_3_48_rental').html(rental_13_48);
		
		//计算保费
	    premiumPrice = 
		Math.round(ticket_price*0.045);
		$('#premiumPrice').html(premiumPrice);
		//购置税
	    purchaseTax = 
		Math.round(ticket_price/11.7);
		$('#purchaseTax').html(purchaseTax);
		//总价,开票价+购置税+一年保险+配置
	    totalPrice = ticket_price+purchaseTax+premiumPrice;
		$("#totalPrice").html(totalPrice);
	},
	bindDom:function(){
		var that = this;
		$(document).on("click",".tax",function(){
			$(".circle").removeClass('circleChoose');
			$(this).find('.circle').addClass("circleChoose");
		});
		$(document).on("click",".mask",function(){
			$('.mask').addClass("hide");
			$('.chooseModal').addClass("hide");
		});
		$(document).on('click','.chooseModal',function(e){
			e.stopPropagation();
		})

		//选中的逻辑
		$(document).on('click','.chooseModal .checkOne',function(){
			if($(this).find('.check').hasClass('checked')){
				$(this).find('.check').removeClass('checked');
				totalPrice = totalPrice*1 -($(this).find('.allocation').html())*1;
				debugger;
				//如果选中的是第一条
				if ($(this).find('#allocationFir_price').length>0){
					check_1='0';
				}
				if ($(this).find('#allocationSec_price').length>0){
					check_2='0';
				}
				if ($(this).find('#allocationThr_price').length>0){
					check_3='0';
				}


			}else{
				$(this).find('.check').addClass('checked');
				totalPrice = totalPrice*1 +($(this).find('.allocation').html())*1;

				//如果选中的是第一条
				if ($(this).find('#allocationFir_price').length>0){
					check_1='1';
				}
				if ($(this).find('#allocationSec_price').length>0){
					check_2='1';
				}
				if ($(this).find('#allocationThr_price').length>0){
					check_3='1';
				}

			}


			that.countTotalPrice();

			//计算1+3方案
			that.calcResidualData();

			$("#totalPrice").html(totalPrice);
		})


		$(document).on('click','#chooseAllocation',function(e){
			$(".mask").removeClass('hide');
			$(".chooseModal").removeClass('hide');
		})
		
	},
	countTotalPrice:function(){
		
		//设置保证金
		var bail = Math.ceil(totalPrice*0.3);
		$('#onebail').html(bail);
		$('#twobail').html(bail);
		$('#thrbail').html(bail);
		//设置月租金
		var oneRent = Math.ceil(totalPrice*(oneYearRate/100+1)/12);
		var twoRent = Math.ceil(totalPrice*(twoYearRate/100*2+1)/24);
		var thrRent = Math.ceil(totalPrice*(thrYearRate/100*3+1)/36);
		$("#oneRent").html(oneRent);
		$("#twoRent").html(twoRent);
		$("#thrRent").html(thrRent);

	},

	calcResidualData:function(){
		debugger;
		debugger;
		var str=check_1+check_2+check_3;
		var result=initResidualData[str];
		//1+3方案
		$('#1_3_downpayment').html(result.downpayment);
		$('#1_3_rental').html(result.rental);
		$('#1_3_buyout').html(result.buyout);
		$('#1_3_12_rental').html(result.rental_1_12);
		$('#1_3_48_rental').html(result.rental_13_48);
	}
}