!function(a){"function"==typeof define&&define.amd?define(["angular","../const/constants"],a):"object"==typeof module&&module.exports?module.exports=function(b,c){return void 0===c&&(c="undefined"!=typeof window?require("angular"):require("angular")(b)),a(c,require("../const/constants")),c}:a(angular)}(function(a){"use strict";var b=function(a,b,c){var d=this;this.iconClass="","info"===d.pattern?this.iconClass="icon modal-info-icon icon-info modal-icon-message":"error"===d.pattern?this.iconClass="icon modal-info-icon icon-error modal-icon-errorMessage":"success"===d.pattern?this.iconClass="icon modal-info-icon icon-ok modal-icon-message":"warning"===d.pattern?this.iconClass="icon modal-info-icon icon-warning modal-icon-message":d.pattern="info"};b.$inject=["WF-KEYCODE-CONST","$animate","$element"],a.module("wf.angular.inlineinfo",["wf.angular.constants","ngAnimate"]).component("wfInlineinfo",{transclude:!0,templateUrl:"wf/ng-template/inlineinfo/inlineinfo.html",bindings:{pattern:"<",message:"<"},controller:b})});