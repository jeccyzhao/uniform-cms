!function(a){"function"==typeof define&&define.amd?define(["angular","../const/constants"],a):"object"==typeof module&&module.exports?module.exports=function(b,c){return void 0===c&&(c="undefined"!=typeof window?require("angular"):require("angular")(b)),a(c,require("../const/constants")),c}:a(angular)}(function(a){"use strict";var b=function(b,c,d){var e=this;this.isShow=!1,void 0===this.pattern&&(this.pattern="standard"),void 0===this.active&&(this.active=!1),void 0===this.toggleText&&(this.toggleText="link"),void 0===this.buttonText&&(this.buttonText="OK");var f,g,h,i,j,k;"function"!=typeof CustomEvent?(f=document.createEvent("CustomEvent"),f.initCustomEvent("show.wulf.dialog",!1,!1,{message:"before show"}),h=document.createEvent("CustomEvent"),h.initCustomEvent("hide.wulf.dialog",!1,!1,{message:"before hide"}),g=document.createEvent("CustomEvent"),g.initCustomEvent("shown.wulf.dialog",!1,!1,{message:"after show"}),i=document.createEvent("CustomEvent"),i.initCustomEvent("hidden.wulf.dialog",!1,!1,{message:"after hide"}),j=document.createEvent("CustomEvent"),j.initCustomEvent("confirm.wulf.dialog",!1,!1,{message:"confirm"}),k=document.createEvent("CustomEvent"),k.initCustomEvent("cancel.wulf.dialog",!1,!1,{message:"cancel"})):(f=new CustomEvent("show.wulf.dialog"),h=new CustomEvent("hide.wulf.dialog"),g=new CustomEvent("shown.wulf.dialog"),i=new CustomEvent("hidden.wulf.dialog"),j=new CustomEvent("confirm.wulf.dialog"),k=new CustomEvent("cancel.wulf.dialog")),this.keydownHandler=function(c){if(c.keyCode===b.SPACE){c.preventDefault();var d=a.element(c.target);(d.hasClass("confirm-btn")||d.hasClass("cancel-btn")||d.hasClass("toggle-target"))&&this.open(c)}else c.keyCode===b.ESC&&this.open(c)},this.open=function(a){var b=d[0].querySelector(".modal");this.animation===!0?this.isShow?(document.dispatchEvent(h),c.removeClass(b,"open").then(function(){e.isShow=!1,document.dispatchEvent(i)})):(document.dispatchEvent(f),this.isShow=!0,c.removeClass(b,"open").then(function(){c.addClass(b,"open").then(function(){b.focus(),document.dispatchEvent(g)})})):(this.isShow?document.dispatchEvent(h):document.dispatchEvent(f),this.isShow=!this.isShow,this.isShow?(setTimeout(function(){b.focus()},0),document.dispatchEvent(g)):document.dispatchEvent(i))},this.confirm=function(a){document.dispatchEvent(j),this.open(a)},this.cancel=function(a){document.dispatchEvent(k),this.open(a)},document.addEventListener("focus",function(a){var b=d[0].querySelector(".modal");e.isShow&&!b.contains(a.target)&&(a.preventDefault(),b.focus())},!0)};b.$inject=["WF-KEYCODE-CONST","$animate","$element"],a.module("wf.angular.dialog",["wf.angular.constants","ngAnimate"]).component("wfDialog",{transclude:!0,templateUrl:"wf/ng-template/dialog/dialog.html",bindings:{pattern:"<",titleText:"<",subheader:"<",instruction:"<",active:"<?",confirmBtnText:"<",toggleStyle:"<",toggleText:"<",animation:"<?"},controller:b})});