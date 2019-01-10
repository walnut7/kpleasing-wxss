/**
 * picker 选择
 * @param category
 * @param showLabel
 * @param hideValue
 * @returns
 */
function select(category, showName, hideValue) {
    var ajaxJsonParam = getJsonObj(category);
    weui.picker(ajaxJsonParam, {
        className: 'custom-classname',
        container: 'body',
        onConfirm: function(result) {
        	$("p[class='"+showName+"']").text(result[0].label);
        	$("input[name='"+hideValue+"']").val(result[0].value);
        },
        id: 'singleLinePicker'
    });
} 


function getRepayBanks(bank) {
	var jsonBanks = getJsonObj(86);
	for(var i = 0; i < jsonBanks.length; i++) {
		// alert(jsonBanks[i].label+"<>"+bank);
		if(jsonBanks[i].label.indexOf(bank)>=0) {
			return jsonBanks[i];
		}
	}
	return null;
}


function getJsonObj(category) {
	var rtnJsonObj;
	$.ajax({
		type : "POST",
		url : "register/getSelectParamList",
		datatype : "json",
		async: false,
		data : { "category" : category },
		success : function(data) {
			rtnJsonObj = eval('(' + data + ')');
		},
		error : function() {
			alert("error");
		}
	});
	return rtnJsonObj;
}

function checkDate(date) {
	if(date.length == 6){
		//
		var year = date.substring(0, 2);
		var month = date.substring(2, 4);
		var day = date.substring(4, 6);
		
		if(year > 99 || year < 00)
			return false;
			
		if(month > 12 || month < 01)
			return false;
			
		if(day > 31 || day < 01)
			return false;
	}
	else if(date.length == 8){
		//
		var year = date.substring(0, 4);
		var month = date.substring(4, 6);
		var day = date.substring(6, 8);
		
		if(year > 9999 || year < 0001)
			return false;
			
		if(month > 12 || month < 01)
			return false;
			
		if(day > 31 || day < 01)
			return false;
	}
    return true;
}

function isIdCardNo(num) { 
    var factorArr = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1);
    var error;
    var varArray = new Array();
    var intValue;
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;    
    // initialize
    if ((intStrLen != 15) && (intStrLen != 18)) {
        return false;
    }    
    for(i=0;i<intStrLen;i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            return false;
        } else if (i < 17) {
            varArray[i] = varArray[i]*factorArr[i];
        }
    }
    if (intStrLen == 18) {
        var date8 = idNumber.substring(6,14);
        if (checkDate(date8) == false) {
            return false;
        }        
        for(i=0;i<17;i++) {
            lngProduct = lngProduct + varArray[i];
        }        
        intCheckDigit = 12 - lngProduct % 11;
        switch (intCheckDigit) {
            case 10:
                intCheckDigit = 'X';
                break;
            case 11:
                intCheckDigit = 0;
                break;
            case 12:
                intCheckDigit = 1;
                break;
        }        
        if (varArray[17].toUpperCase() != intCheckDigit) {
            return false;
        }
    } 
    else{
        var date6 = idNumber.substring(6,12);
        if (checkDate(date6) == false) {
            return false;
        }
    }
    return true;
}


function verifyPhoneFormat(phone) {
	return !(/^1[3|4|5|6|7|8|9][0-9]\d{8}$/.test(phone))?true:false;
}


function trim(src) {
	return src.replace(/\s/g,"");
}

