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

function initJqxTable (container_id, columns, data, width, height)
{
    var source = { localdata: data, datatype: "array"};
    var dataAdapter = new $.jqx.dataAdapter(source);

    $("#" + container_id).jqxGrid({
        width: width != undefined ? width : '100%',
        height: height != undefined ? height : 480,
        source: dataAdapter,
        columnsResize: true,
        columnsreorder: true,
        pageable: true,
        selectionmode: 'singleRow',
        columns: columns,
        filterable: true,
        showfilterrow: true,
        sortable: true,
        altRows: true,
        enableBrowserSelection: true,
        autoshowcolumnsmenubutton: true,
        altRows: true,
        scrollBarSize: 8,
        pagerheight: 40,
        rowsheight: 26,
        columnsheight: 26,
        pagerrenderer: $.grid.pagerrenderer.bind(null, "#" + container_id, true),
        handlekeyboardnavigation: function (e)
        {
           return $.grid.handlekeyboardnavigation(e);
        },
        ready: function()
        {
            disableAjaxLoading();
        }
    });
}