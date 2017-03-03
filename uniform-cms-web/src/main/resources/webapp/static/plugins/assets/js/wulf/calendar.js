!function(a){"function"==typeof define&&define.amd?define(["jquery","bootstrap","fuelux/spinbox","fuelux/datepicker","./spinner","./keyboard/keyboard-calendar"],a):"object"==typeof module&&module.exports?module.exports=function(b,c){return void 0===c&&(c="undefined"!=typeof window?require("jquery"):require("jquery")(b)),a(c,require("bootstrap"),require("fuelux/spinbox"),require("fuelux/datepicker"),require("./spinner"),require("./keyboard/keyboard-calendar")),c}:a(jQuery)}(function(a){"use strict";function b(b){var c=a(b.currentTarget).find("button")[0];b.target!==c&&"none"!==a(c).closest("span").css("display")&&c.click()}function c(b){40===b.which&&(a(this).nextAll(".datepicker-wheels-footer").find(".datepicker-wheels-back").focus(),b.preventDefault(),b.stopPropagation())}function d(b){a(this).closest(".datepicker-calendar-wrapper").find("button.title").focus(),b.preventDefault(),b.stopPropagation()}function e(){f(a(this).closest(".n-calendar"))}function f(b){var c=b.find(".datepicker-calendar-wrapper"),d=c.outerWidth()-b.width();if("fixed"===b.find("input").data("position")){if(0!==c.length){c.css("position","fixed"),c.css("top",b.offset().top+b.height()-a(document).scrollTop());var e=b.offset().left-c.outerWidth()+b.width()-a(document).scrollLeft();0>e&&(e+=d),c.css("left",e),c.css("right","auto")}}else d>b.offset().left?c.css("right",-d):c.css("right",0)}function g(){a(".datepicker-calendar-wrapper").each(function(){if("block"===a(this).css("display")){var b=a(this).closest(".n-calendar").find("input");"fixed"===b.data("position")&&a(this).parent().find("button.dropdown-toggle").trigger("click")}})}function h(a,b,c,d,e){var f=d.getHours(),g=d.getMinutes(),h=0;e&&(h=f>=12?0:1,f>12&&(f-=12)),a.spinbox("value",f),b.spinbox("value",g),c.spinbox("value",h)}function i(b){var c=a(b.currentTarget).parents(".datepicker"),d=c.datepicker("is12HoursFormat"),e=new Date;c.datepicker("setDate",e);var f=a(b.currentTarget).parents(".datepicker-calendar-timer"),g=f.find(".datepicker-calendar-hour .spinbox"),i=f.find(".datepicker-calendar-minute .spinbox"),j=f.find(".datepicker-calendar-AMPM .spinbox");h(g,i,j,e,d)}function j(b){var c=a(b.currentTarget).parents(".datepicker"),d=c.datepicker("is12HoursFormat"),e=c.datepicker("getDate"),f=c.find(".datepicker-calendar-timer"),g=f.find(".datepicker-calendar-hour input"),h=f.find(".datepicker-calendar-minute input"),i=f.find(".datepicker-calendar-AMPM .spinbox input"),j=parseInt(g.val());d&&"0"===i.val()&&(j+=12),e.setHours(j),e.setMinutes(h.val()),c.datepicker("setDate",e),k(c)}function k(a){a.find(".input-group-btn").removeClass("open");var b=a.find("input:first");b.next().find(".dropdown-toggle").attr("aria-expanded","false"),b.hasClass(n)&&b.removeClass(n)}function l(b){var c=a(b.currentTarget),d=c.datepicker("getDate"),e=c.datepicker("is12HoursFormat"),f=c.find(".datepicker-calendar-hour .spinbox"),g=c.find(".datepicker-calendar-minute .spinbox"),i=c.find(".datepicker-calendar-AMPM .spinbox");h(f,g,i,d,e)}function m(){var b=a(this).closest(".n-calendar-lock-past");b.find(".past").each(function(){a(this).find("button").attr("disabled","disabled")})}var n="n-inputfield-nonradius-lb";"function"==typeof a.fn.datepicker&&(a(document).on("shown.bs.dropdown hidden.bs.dropdown",".n-calendar",function(){a(this).children("input").toggleClass(n)}).on("blur.wf.calendar",".n-calendar input",function(){var b=a(this);b.next().find(".dropdown-toggle").attr("aria-expanded","false"),b.hasClass(n)&&b.removeClass(n)}).on("keydown.wf.calendar",".n-calendar .datepicker-wheels-year",c).on("keydown.wf.calendar",".n-calendar .datepicker-wheels-month",c).on("click.wf.calendar",".datepicker-wheels-footer .datepicker-wheels-back",d).on("click.wf.calendar",".datepicker-wheels-footer .datepicker-wheels-select",d).on("shown.bs.dropdown",".n-calendar .input-group-btn",e).on("scroll.wf.calendar",g).on("changed.fu.datepicker",".datepicker",l).on("click.wf.calendar",".n-calendar-lock-past button",m).on("click.fu.datepicker",".datepicker-calendar tr td",b),a(window).on("resize.wf.calendar",function(){g(),a(".n-calendar").each(function(){var b=a(this).find("input");"fixed"!==b.data("position")&&f(a(this))})}),a(function(){a('[data-markup^="calendar"]').each(function(){0===a(this).parent().find(".datepicker-calendar-wrapper").length&&a(this).after('<div class="input-group-btn"><button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">  <span class="glyphicon glyphicon-calendar"></span>  <span class="sr-only">Toggle Calendar</span></button><div class="dropdown-menu dropdown-menu-right datepicker-calendar-wrapper" role="menu">  <div class="datepicker-calendar"><div class="datepicker-calendar-header"><button type="button" class="prev"><span class="glyphicon glyphicon-chevron-left"></span><span class="sr-only">Previous Month</span></button><button type="button" class="next"><span class="glyphicon glyphicon-chevron-right"></span><span class="sr-only">Next Month</span></button><button type="button" class="title"><span class="month">  <span data-month="0">January</span>  <span data-month="1">February</span>  <span data-month="2">March</span>  <span data-month="3">April</span>  <span data-month="4">May</span>  <span data-month="5">June</span>  <span data-month="6">July</span>  <span data-month="7">August</span>  <span data-month="8">September</span>  <span data-month="9">October</span>  <span data-month="10">November</span>  <span data-month="11">December</span></span> <span class="year"></span></button></div><table class="datepicker-calendar-days"><thead><tr><th>SUN</th><th>MON</th><th>TUE</th><th>WED</th><th>THU</th><th>FRI</th><th>SAT</th></tr></thead><tbody></tbody></table></div><div class="datepicker-wheels" aria-hidden="true"><div class="datepicker-wheels-month"><h2 class="header">Month</h2><ul><li data-month="0"><button type="button">Jan</button></li><li data-month="1"><button type="button">Feb</button></li><li data-month="2"><button type="button">Mar</button></li><li data-month="3"><button type="button">Apr</button></li><li data-month="4"><button type="button">May</button></li><li data-month="5"><button type="button">Jun</button></li><li data-month="6"><button type="button">Jul</button></li><li data-month="7"><button type="button">Aug</button></li><li data-month="8"><button type="button">Sep</button></li><li data-month="9"><button type="button">Oct</button></li><li data-month="10"><button type="button">Nov</button></li><li data-month="11"><button type="button">Dec</button></li></ul></div><div class="datepicker-wheels-year"><h2 class="header">Year</h2><ul></ul></div><div class="datepicker-wheels-footer clearfix"><button type="button" class="btn datepicker-wheels-back"><span class="icon icon-left"></span><span class="sr-only">Return to Calendar</span></button><button type="button" class="btn datepicker-wheels-select">Select <span class="sr-only">Month and Year</span></button></div></div></div></div> </div></div>')}),a('[data-markup^="disabled_calendar"]').each(function(){0===a(this).parent().find(".datepicker-calendar-wrapper").length&&a(this).after('<div class="input-group-btn"><button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" disabled>  <span class="glyphicon glyphicon-calendar"></span>  <span class="sr-only">Toggle Calendar</span></button><div class="dropdown-menu dropdown-menu-right datepicker-calendar-wrapper" role="menu">  <div class="datepicker-calendar"><div class="datepicker-calendar-header"><button type="button" class="prev"><span class="glyphicon glyphicon-chevron-left"></span><span class="sr-only">Previous Month</span></button><button type="button" class="next"><span class="glyphicon glyphicon-chevron-right"></span><span class="sr-only">Next Month</span></button><button type="button" class="title"><span class="month">  <span data-month="0">January</span>  <span data-month="1">February</span>  <span data-month="2">March</span>  <span data-month="3">April</span>  <span data-month="4">May</span>  <span data-month="5">June</span>  <span data-month="6">July</span>  <span data-month="7">August</span>  <span data-month="8">September</span>  <span data-month="9">October</span>  <span data-month="10">November</span>  <span data-month="11">December</span></span> <span class="year"></span></button></div><table class="datepicker-calendar-days"><thead><tr><th>SUN</th><th>MON</th><th>TUE</th><th>WED</th><th>THU</th><th>FRI</th><th>SAT</th></tr></thead><tbody></tbody></table></div><div class="datepicker-wheels" aria-hidden="true"><div class="datepicker-wheels-month"><h2 class="header">Month</h2><ul><li data-month="0"><button type="button">Jan</button></li><li data-month="1"><button type="button">Feb</button></li><li data-month="2"><button type="button">Mar</button></li><li data-month="3"><button type="button">Apr</button></li><li data-month="4"><button type="button">May</button></li><li data-month="5"><button type="button">Jun</button></li><li data-month="6"><button type="button">Jul</button></li><li data-month="7"><button type="button">Aug</button></li><li data-month="8"><button type="button">Sep</button></li><li data-month="9"><button type="button">Oct</button></li><li data-month="10"><button type="button">Nov</button></li><li data-month="11"><button type="button">Dec</button></li></ul></div><div class="datepicker-wheels-year"><h2 class="header">Year</h2><ul></ul></div><div class="datepicker-wheels-footer clearfix"><button type="button" class="btn datepicker-wheels-back"><span class="icon icon-left"></span><span class="sr-only">Return to Calendar</span></button><button type="button" class="btn datepicker-wheels-select">Select <span class="sr-only">Month and Year</span></button></div></div></div></div> </div></div>')}),a('[data-markup^="timer_calendar"]').each(function(){0===a(this).parent().find(".datepicker-calendar-wrapper").length&&a(this).after('<div class="input-group-btn"><button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">  <span class="glyphicon glyphicon-calendar"></span>  <span class="sr-only">Toggle Calendar</span></button><div class="dropdown-menu dropdown-menu-right datepicker-calendar-wrapper" role="menu">  <div class="datepicker-calendar"><div class="datepicker-calendar-header"><button type="button" class="prev"><span class="glyphicon glyphicon-chevron-left"></span><span class="sr-only">Previous Month</span></button><button type="button" class="next"><span class="glyphicon glyphicon-chevron-right"></span><span class="sr-only">Next Month</span></button><button type="button" class="title"><span class="month">  <span data-month="0">January</span>  <span data-month="1">February</span>  <span data-month="2">March</span>  <span data-month="3">April</span>  <span data-month="4">May</span>  <span data-month="5">June</span>  <span data-month="6">July</span>  <span data-month="7">August</span>  <span data-month="8">September</span>  <span data-month="9">October</span>  <span data-month="10">November</span>  <span data-month="11">December</span></span> <span class="year"></span></button></div><table class="datepicker-calendar-days"><thead><tr><th>SUN</th><th>MON</th><th>TUE</th><th>WED</th><th>THU</th><th>FRI</th><th>SAT</th></tr></thead><tbody></tbody></table><div class="datepicker-calendar-timer"><div class="spinner-container datepicker-calendar-hour"><div class="spinbox" data-initialize="spinbox"><input type="text" class="form-control spinbox-input n-inputfield"><div class="spinbox-buttons btn-group btn-group-vertical"><button type="button" class="btn btn-default spinbox-up btn-xs"><span class="icon icon-arrow-up"></span><span class="sr-only">Increase</span></button><button type="button" class="btn btn-default spinbox-down btn-xs"><span class="icon icon-arrow"></span><span class="sr-only">Decrease</span></button></div></div></div><div class="spinner-container datepicker-calendar-minute"><div class="spinbox" data-initialize="spinbox"><input type="text" class="form-control spinbox-input n-inputfield"><div class="spinbox-buttons btn-group btn-group-vertical"><button type="button" class="btn btn-default spinbox-up btn-xs"><span class="icon icon-arrow-up"></span><span class="sr-only">Increase</span></button><button type="button" class="btn btn-default spinbox-down btn-xs"><span class="icon icon-arrow"></span><span class="sr-only">Decrease</span></button></div></div></div><div class="spinner-container datepicker-calendar-AMPM"><div class="spinbox" data-initialize="spinbox"><input id="s-normal" type="text" tabIndex="-1" class="form-control spinbox-input n-inputfield n-inputfield-uneditable" readonly><div class="spinbox-buttons btn-group btn-group-vertical"><button type="button" class="btn btn-default spinbox-up btn-xs"><span class="icon icon-arrow-up"></span><span class="sr-only">Increase</span></button><button type="button" class="btn btn-default spinbox-down btn-xs"><span class="icon icon-arrow"></span><span class="sr-only">Decrease</span></button></div></div><input type="text" tabIndex="-1" class="form-control spinbox-input n-inputfield ampm n-inputfield-uneditable" readonly></div><div class="operator-btn"><button type="button" class="btn btn-small now">Now</button> <button type="button" class="btn btn-defaultBlue btn-small done">Done</button></div></div></div><div class="datepicker-wheels" aria-hidden="true"><div class="datepicker-wheels-month"><h2 class="header">Month</h2><ul><li data-month="0"><button type="button">Jan</button></li><li data-month="1"><button type="button">Feb</button></li><li data-month="2"><button type="button">Mar</button></li><li data-month="3"><button type="button">Apr</button></li><li data-month="4"><button type="button">May</button></li><li data-month="5"><button type="button">Jun</button></li><li data-month="6"><button type="button">Jul</button></li><li data-month="7"><button type="button">Aug</button></li><li data-month="8"><button type="button">Sep</button></li><li data-month="9"><button type="button">Oct</button></li><li data-month="10"><button type="button">Nov</button></li><li data-month="11"><button type="button">Dec</button></li></ul></div><div class="datepicker-wheels-year"><h2 class="header">Year</h2><ul></ul></div><div class="datepicker-wheels-footer clearfix"><button type="button" class="btn datepicker-wheels-back"><span class="icon icon-left"></span><span class="sr-only">Return to Calendar</span></button><button type="button" class="btn datepicker-wheels-select">Select <span class="sr-only">Month and Year</span></button></div></div></div></div> </div></div>')}),a('[data-markup^="disabled_timer_calendar"]').each(function(){0===a(this).parent().find(".datepicker-calendar-wrapper").length&&a(this).after('<div class="input-group-btn"><button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" disabled>  <span class="glyphicon glyphicon-calendar"></span>  <span class="sr-only">Toggle Calendar</span></button><div class="dropdown-menu dropdown-menu-right datepicker-calendar-wrapper" role="menu">  <div class="datepicker-calendar"><div class="datepicker-calendar-header"><button type="button" class="prev"><span class="glyphicon glyphicon-chevron-left"></span><span class="sr-only">Previous Month</span></button><button type="button" class="next"><span class="glyphicon glyphicon-chevron-right"></span><span class="sr-only">Next Month</span></button><button type="button" class="title"><span class="month">  <span data-month="0">January</span>  <span data-month="1">February</span>  <span data-month="2">March</span>  <span data-month="3">April</span>  <span data-month="4">May</span>  <span data-month="5">June</span>  <span data-month="6">July</span>  <span data-month="7">August</span>  <span data-month="8">September</span>  <span data-month="9">October</span>  <span data-month="10">November</span>  <span data-month="11">December</span></span> <span class="year"></span></button></div><table class="datepicker-calendar-days"><thead><tr><th>SUN</th><th>MON</th><th>TUE</th><th>WED</th><th>THU</th><th>FRI</th><th>SAT</th></tr></thead><tbody></tbody></table><div class="datepicker-calendar-timer"><div class="spinner-container datepicker-calendar-hour"><div class="spinbox" data-initialize="spinbox"><input type="text" class="form-control spinbox-input n-inputfield"><div class="spinbox-buttons btn-group btn-group-vertical"><button type="button" class="btn btn-default spinbox-up btn-xs"><span class="icon icon-arrow-up"></span><span class="sr-only">Increase</span></button><button type="button" class="btn btn-default spinbox-down btn-xs"><span class="icon icon-arrow"></span><span class="sr-only">Decrease</span></button></div></div></div><div class="spinner-container datepicker-calendar-minute"><div class="spinbox" data-initialize="spinbox"><input type="text" class="form-control spinbox-input n-inputfield"><div class="spinbox-buttons btn-group btn-group-vertical"><button type="button" class="btn btn-default spinbox-up btn-xs"><span class="icon icon-arrow-up"></span><span class="sr-only">Increase</span></button><button type="button" class="btn btn-default spinbox-down btn-xs"><span class="icon icon-arrow"></span><span class="sr-only">Decrease</span></button></div></div></div><div class="spinner-container datepicker-calendar-AMPM"><div class="spinbox" data-initialize="spinbox"><input id="s-normal" type="text" tabIndex="-1" class="form-control spinbox-input n-inputfield n-inputfield-uneditable" readonly><div class="spinbox-buttons btn-group btn-group-vertical"><button type="button" class="btn btn-default spinbox-up btn-xs"><span class="icon icon-arrow-up"></span><span class="sr-only">Increase</span></button><button type="button" class="btn btn-default spinbox-down btn-xs"><span class="icon icon-arrow"></span><span class="sr-only">Decrease</span></button></div></div><input type="text" tabIndex="-1" class="form-control spinbox-input n-inputfield ampm n-inputfield-uneditable" readonly></div><div class="operator-btn"><button type="button" class="btn btn-small now">Now</button> <button type="button" class="btn btn-defaultBlue btn-small done">Done</button></div></div></div><div class="datepicker-wheels" aria-hidden="true"><div class="datepicker-wheels-month"><h2 class="header">Month</h2><ul><li data-month="0"><button type="button">Jan</button></li><li data-month="1"><button type="button">Feb</button></li><li data-month="2"><button type="button">Mar</button></li><li data-month="3"><button type="button">Apr</button></li><li data-month="4"><button type="button">May</button></li><li data-month="5"><button type="button">Jun</button></li><li data-month="6"><button type="button">Jul</button></li><li data-month="7"><button type="button">Aug</button></li><li data-month="8"><button type="button">Sep</button></li><li data-month="9"><button type="button">Oct</button></li><li data-month="10"><button type="button">Nov</button></li><li data-month="11"><button type="button">Dec</button></li></ul></div><div class="datepicker-wheels-year"><h2 class="header">Year</h2><ul></ul></div><div class="datepicker-wheels-footer clearfix"><button type="button" class="btn datepicker-wheels-back"><span class="icon icon-left"></span><span class="sr-only">Return to Calendar</span></button><button type="button" class="btn datepicker-wheels-select">Select <span class="sr-only">Month and Year</span></button></div></div></div></div> </div></div>')}),setTimeout(function(){a(".datepicker .n-calendar .form-control").off("focus.fu.datepicker")},25)}),a.fn.datepicker.Constructor.prototype.initTimer=function(){this.options.showTime=!0,a(this.$element.find(".datepicker-calendar-timer")).css("display","block"),this.$input.off("blur.fu.datepicker"),this.$input=this.$element.find("input:first"),this.$input.on("blur.fu.datepicker",a.proxy(this.inputBlurred,this)),this.$hour=this.$element.find(".datepicker-calendar-hour .spinbox"),this.$minute=this.$element.find(".datepicker-calendar-minute .spinbox"),this.$ampm=this.$element.find(".datepicker-calendar-AMPM .spinbox"),this.$hour.spinbox("max",12),this.$hour.spinbox("min",1),this.$minute.spinbox("max",59),this.$minute.spinbox("min",0),this.$ampm.spinbox("max",1),this.$ampm.spinbox("min",0),this.$element.find(".now").on("click",i),this.$element.find(".done").on("click",j),this.resetTimer(),this.is12HoursFormat()?a(this.$element.find(".datepicker-calendar-AMPM")).css("display","inline-block"):(this.$hour.spinbox("max",23),this.$hour.spinbox("min",0)),this.$element.on("keyup",".n-calendar div.dropdown-menu",function(a){var b=13;a.which===b&&j(a)})},a.fn.datepicker.Constructor.prototype.dateClicked=function(b){var c,d=a(b.currentTarget).parents("td:first");d.hasClass("restricted")||(this.$days.find("td.selected").removeClass("selected"),d.addClass("selected"),c=new Date(d.attr("data-year"),d.attr("data-month"),d.attr("data-date")),this.selectedDate=c,this.options.showTime?b.stopPropagation():(this.$input.val(this.formatDate(c)),this.inputValue=this.$input.val(),this.$input.focus(),this.$element.trigger("dateClicked.fu.datepicker",c)))},a.fn.datepicker.Constructor.prototype.resetTimer=function(){h(this.$hour,this.$minute,this.$ampm,new Date,this.is12HoursFormat())},a.fn.datepicker.Constructor.prototype.is12HoursFormat=function(){return this.options.momentConfig.format.indexOf("H")<0},a.fn.spinbox.Constructor.prototype.output=function(b,c){var d=a(this.$element).parent().find(".ampm");return d.length>0&&a(d[0]).val(b%2===0?"PM":"AM"),b=(b+"").split(".").join(this.options.decimalMark),c=c||!0,c&&this.$input.val(b),b},a.fn.spinbox.Constructor.prototype.max=function(a){this.options.max=a},a.fn.spinbox.Constructor.prototype.min=function(a){this.options.min=a},a(document).on("keydown.wf.calendar.keyboard",".datepicker-calendar-days",a.wfKBCalendar.calendarKeyboardHandler).on("focusin.wf.calendar.keyboard",".datepicker-calendar-days",a.wfKBCalendar.calendarFocusinHandler))});