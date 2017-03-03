function enableAjaxLoading(text) 
{  
     $("<div class=\"datagrid-mask\"></div>").css(
    		{ display: "block", height: $(window).height()}).appendTo("body");  
     $("<div class=\"datagrid-mask-msg\"></div>").html(text).appendTo("body").css(
    		 { display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
}  
 
function disableAjaxLoading() 
{  
     $(".datagrid-mask").remove();  
     $(".datagrid-mask-msg").remove();  
}

String.prototype.replaceAll = function(search, replacement) 
{
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};

String.prototype.stripHTML = function() 
{  
    var reTag = /<(?:.|\s)*?>/g;  
    return this.replace(reTag,"");  
}  

Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
	"M+" : this.getMonth()+1,                 //月份   
	"d+" : this.getDate(),                    //日   
	"h+" : this.getHours(),                   //小时   
	"m+" : this.getMinutes(),                 //分   
	"s+" : this.getSeconds(),                 //秒   
	"q+" : Math.floor((this.getMonth()+3)/3), //季度   
	"S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
	fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
	if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}