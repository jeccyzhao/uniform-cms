!function(a){"function"==typeof define&&define.amd?define(["angular","../const/constants"],a):"object"==typeof module&&module.exports?module.exports=function(b,c){return void 0===c&&(c="undefined"!=typeof window?require("angular"):require("angular")(b)),a(c,require("../const/constants")),c}:a(angular)}(function(a){"use strict";function b(){var a,b=[],c=this.utilitySet.length;if(c>=2){for(b[0]=this.utilitySet[0],a=1;c>a;a++)b[2*a]=this.utilitySet[a],b[2*a-1]={label:"|",cssClass:"hidden-xs",link:"#",menuItems:[],disable:!1};for(a=0;a<b.length;a++)this.utilitySet[a]=b[a]}}b.$inject=["WF-KEYCODE-CONST"],a.module("wf.angular.banner",["wf.angular.constants","wf.angular.bannerUtilitySet","wf.angular.bannerNavTabs","wf.angular.bannerActionSet"]).component("wfBanner",{transclude:!0,templateUrl:"wf/ng-template/banner/banner.html",bindings:{productInfo:"<",utilitySet:"<",navTabsInfo:"<",actionSet:"<"},controller:b})});