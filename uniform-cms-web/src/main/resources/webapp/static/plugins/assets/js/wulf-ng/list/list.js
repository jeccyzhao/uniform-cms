!function(a){"function"==typeof define&&define.amd?define(["angular","../const/constants"],a):"object"==typeof module&&module.exports?module.exports=function(b,c){return void 0===c&&(c="undefined"!=typeof window?require("angular"):require("angular")(b)),a(c,require("../const/constants")),c}:a(angular)}(function(a){"use strict";var b=function(b,c){var d=this,e=[],f=-1;d.focusedIndex=f,d.selectable="selection"===c.type,d.addItem=function(a,b){e.push(a),b&&(f=e.length-1,d.focusedIndex=f)};var g=function(a){for(var b=null,c=0;c<e.length;c++)e[c]===a&&(b=c);return b};d.setSelectedItemByIndex=function(a){-1!==f&&e[f].toggleSelect(!1),e[a].toggleSelect(!0),f=a,d.focusedIndex=f},d.setSelectedItem=function(a){var b=g(a);d.setSelectedItemByIndex(b)},d.$onChanges=function(a){for(var b=0;b<e.length;b++)e[b].onChange(a)},d.handleKeypress=function(c){var f=a.element(c.target).parent().parent().parent();c.keyCode===b.DOWN&&d.focusedIndex<e.length-1&&(d.focusedIndex++,f.find("li")[d.focusedIndex].focus()),c.keyCode===b.UP&&d.focusedIndex>0&&(d.focusedIndex--,f.find("li")[d.focusedIndex].focus()),c.keyCode!==b.SPACE&&c.keyCode!==b.ENTER||-1===d.focusedIndex||d.setSelectedItemByIndex(d.focusedIndex),c.preventDefault()}};b.$inject=["WF-KEYCODE-CONST","$attrs"];var c=function(){var a=this;a.$onInit=function(){a.isSelectable=a.listCtrl.selectable,a.isDisabled=a.listCtrl.disable,a.isFocusable=a.isSelectable&&!a.isDisabled,a.listCtrl.addItem(this,a.select)},a.onChange=function(b){void 0!==b.disable&&(a.isDisabled=b.disable.currentValue,a.isFocusable=a.isSelectable&&!a.isDisabled)},a.selectThis=function(){a.isDisabled!==!1&&void 0!==a.isDisabled||a.isSelectable!==!0||a.listCtrl.setSelectedItem(this)},a.toggleSelect=function(b){a.select=b}};a.module("wf.angular.list",["wf.angular.constants"]).component("wfList",{transclude:!0,templateUrl:"wf/ng-template/list/list.html",controller:b,bindings:{disable:"<?",design:"@",type:"<",heading:"@"}}).component("wfListItem",{transclude:!0,templateUrl:"wf/ng-template/list/list-item.html",bindings:{select:"<?"},require:{listCtrl:"^wfList"},controller:c})});