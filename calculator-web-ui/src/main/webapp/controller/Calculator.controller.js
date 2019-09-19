sap.ui.define([
    "sap/ui/core/mvc/Controller",
], function (Controller) {
   "use strict";

   return Controller.extend("com.calculator.webUi.controller.Calculator", {

      onTokenPress : function (token) {
         let expressionBar = this.getExpressionBar();
         let currentExpression = expressionBar.getValue();
         expressionBar.setValue(currentExpression + token);
      },
      onBackspaceButtonPress : function () {
         let expressionBar = this.getExpressionBar();
         let currentExpression = expressionBar.getValue();
         expressionBar.setValue(currentExpression.substring(0, currentExpression.length - 1));
      },
      onDeleteAllButtonPress : function () {
         let expressionBar = this.getExpressionBar();
         expressionBar.setValue("");
      },
      getExpressionBar : function () {
         let view = this.getCurrentView();
         return view.byId("expressionBar");
      },
      getCurrentView : function () {
         return this.getView();
      }
   });
});