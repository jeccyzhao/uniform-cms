!function(a){"function"==typeof define&&define.amd?define(["jquery","./keyboard/keyboard-core","./scrollbar"],a):"object"==typeof module&&module.exports?module.exports=function(b,c){return void 0===c&&(c="undefined"!=typeof window?require("jquery"):require("jquery")(b)),a(c,require("./keyboard/keyboard-core"),require("./scrollbar")),c}:a(jQuery)}(function(a){"use strict";var b=null;a(".visualizationtablecell tbody tr:nth-child(odd) th").click(function(){a(this).data("isSelected")?(a(this).data("isSelected",!1),a(this).parent().removeClass("selected-grey")):(a(".visualizationtablecell thead tr th").removeClass("selected-column-header"),a(this).parent().siblings().removeClass("selected-grey").removeClass("selected-blue").children().removeClass("selected-grey-cell").removeClass("selected-blue-cell"),a(this).parent().addClass("selected-grey"),null!==b&&b.data("isSelected",!1),b=a(this),a(this).data("isSelected",!0))}),a(".visualizationtablecell tbody tr:nth-child(even) th").click(function(){a(this).data("isSelected")?(a(this).data("isSelected",!1),a(this).parent().removeClass("selected-blue")):(a(".visualizationtablecell thead tr th").removeClass("selected-column-header"),a(this).parent().addClass("selected-blue").siblings().removeClass("selected-grey").removeClass("selected-blue").children().removeClass("selected-grey-cell").removeClass("selected-blue-cell"),null!==b&&b.data("isSelected",!1),b=a(this),a(this).data("isSelected",!0))}),a(".visualizationtablecell thead tr th").click(function(){if(0!==a(this).index()){a(this).data("isSelected",!0),a(this).addClass("selected-column-header").siblings().removeClass("selected-column-header");var b=a(this).index()+1,c=a(".visualizationtablecell tbody tr");c.removeClass("selected-grey").removeClass("selected-blue");for(var d=c.length,e=1;d>=e;e++){var f=a(".visualizationtablecell tbody tr:nth-child("+e+") td:nth-child("+b+")");f.siblings().removeClass("selected-grey-cell").removeClass("selected-blue-cell"),0===e||e%2===0?f.addClass("selected-blue-cell"):f.addClass("selected-grey-cell")}}}),a(".visualizationtablecell").attr("cellspacing",0),a(".visualizationtablecell tbody tr th:first-child").wrapInner("<div style='white-space: nowrap' align='left'></div>"),a(".visualizationtablecell tbody tr th:first-child div").prepend("<span class='visualization-header-bullet'></span>");var c=null;a(".visualizationtablecell tbody tr th:first-child div").each(function(){var b=a(this).width();console.log(b),(null===c||b>c)&&(c=b)}).width(c+11)});