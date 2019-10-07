sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel"
], function (Controller, JSONModel) {
   "use strict";

   return Controller.extend("com.calculator.webUi.controller.App", {
       getBaseUrl : function () {
           return "https://approuter-i517939.cfapps.sap.hana.ondemand.com";
       }
   });
});