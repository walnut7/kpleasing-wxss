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


function trim(src) {
	return src.replace(/\s/g,"");
}

