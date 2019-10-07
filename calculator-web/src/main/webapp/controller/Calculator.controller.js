sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "com/calculator/webUi/controller/App.controller"
], function (Controller, JSONModel, App) {
   "use strict";

   const app = new App();

   return Controller.extend("com.calculator.webUi.controller.Calculator", {
      calculateResource : app.getBaseUrl() + "/api/v1/calculate",
      calculationResultsResource : app.getBaseUrl() + "/api/v1/calculationResults",
      intervalBetweenCompletionChecks: 1000,
      xCsrfToken : null,
      onInit : function () {
         this.fetchXsrfToken();
      },
      fetchXsrfToken : function () {
         let oXhr = new XMLHttpRequest();

         let oCalculator = this;
         let onSuccessfulTokenFetch = function () {
            if (this.readyState == this.HEADERS_RECEIVED) {
               oCalculator.xCsrfToken = oXhr.getResponseHeader("X-Csrf-Token");
            }
         };

         let oExpressionBar = this.getExpressionBar();

         let oHttpRequestDetails = {
            resource : this.calculationResultsResource,
            method: "GET",
            readyStateListener : onSuccessfulTokenFetch,
            data : null,
            xCsrfToken : "Fetch"
         };

         this.runHttpRequest(oXhr, oHttpRequestDetails);
      },
      onTokenPress : function (sToken) {
         let oExpressionBar = this.getExpressionBar();
         let sCurrentExpression = oExpressionBar.getValue();
         oExpressionBar.setValue(sCurrentExpression + sToken);
      },
      onBackspaceButtonPress : function () {
         let oExpressionBar = this.getExpressionBar();
         let sCurrentExpression = oExpressionBar.getValue();
         oExpressionBar.setValue(sCurrentExpression.substring(0, sCurrentExpression.length - 1));
      },
      onDeleteAllButtonPress : function () {
         let oExpressionBar = this.getExpressionBar();
         oExpressionBar.setValue("");
      },
      onCalculateButtonPress : function () {
         this.uploadExpression();
      },
      getExpressionBar : function () {
         let oView = this.getCurrentView();
         return oView.byId("expressionBar");
      },
      getCurrentView : function () {
         return this.getView();
      },
      uploadExpression : function () {
         this.showCalculationLoadingIndicator();

         let oCalculator = this;
         let onSuccessfulExpressionRecording = function () {
            oCalculator.startResultRetrievingJob(this.readyState, this.responseText, oCalculator);
         };

         let oExpressionBar = this.getExpressionBar();

         let oHttpRequestDetails = {
            resource : this.calculateResource,
            method: "POST",
            readyStateListener : onSuccessfulExpressionRecording,
            data : oExpressionBar.getValue(),
            xCsrfToken : this.xCsrfToken
         };
         let oXhr = new XMLHttpRequest();

         this.runHttpRequest(oXhr, oHttpRequestDetails);
      },
      runHttpRequest : function (oXhr, oHttpRequestDetails) {
         oXhr.open(oHttpRequestDetails.method, oHttpRequestDetails.resource);
         oXhr.setRequestHeader("Content-Type", "application/json");
         oXhr.setRequestHeader("cache-control", "no-cache");
         oXhr.addEventListener("readystatechange", oHttpRequestDetails.readyStateListener);
         oXhr.setRequestHeader("X-Csrf-Token", oHttpRequestDetails.xCsrfToken);
         
         oXhr.send(oHttpRequestDetails.data);
      },
      startResultRetrievingJob : function (iReadyState, sResponseText, oCalculator) {
         if (iReadyState === 4) {
            let iCalculationResultId = sResponseText;
            
            let iCalculationResultGettingInterval = setInterval(function () { 
               oCalculator.getCompletedCalculationResult(iCalculationResultId, iCalculationResultGettingInterval, oCalculator);
            }, oCalculator.intervalBetweenCompletionChecks);
         }
      },
      getCompletedCalculationResult : function (oCalculationResultId, iCalculationResultGettingInterval, oCalculator) {
         let checkIfCalculationIsCompleted = function () {
            oCalculator.checkIfCalculationIsCompleted(this.readyState, this.responseText, iCalculationResultGettingInterval);
         };

         let oHttpRequestDetails = {
            resource : oCalculator.calculationResultsResource + "/" + oCalculationResultId,
            method: "GET",
            readyStateListener : checkIfCalculationIsCompleted,
            data : null,
            xCsrfToken : this.xCsrfToken
         };
         let oXhr = new XMLHttpRequest();

         oCalculator.runHttpRequest(oXhr, oHttpRequestDetails);
      },
      checkIfCalculationIsCompleted : function (iReadyState, sResponseText, iCalculationResultGettingInterval) {
         if (iReadyState === 4) {
            let oCalculationResult = JSON.parse(sResponseText);

            if (oCalculationResult.status != 2) {
               return;
            }

            clearInterval(iCalculationResultGettingInterval);

            let oExpressionBar = this.getExpressionBar();

            if (this.isExpressionCorrect(oCalculationResult)) {                  
               oExpressionBar.setValue(oCalculationResult.evaluation);
            } else if (this.isExpressionIncorrect(oCalculationResult)) {
               oExpressionBar.setValue(oCalculationResult.message);
            }

            this.hideCalculationLoadingIndicator();
         }
      },
      isExpressionCorrect : function (oCalculationResult) {
         return oCalculationResult.evaluation !== null && oCalculationResult.message === null;
      },
      isExpressionIncorrect : function (oCalculationResult) {
         return oCalculationResult.evaluation === null && oCalculationResult.message !== null;
      },
      hideCalculationLoadingIndicator : function () {
         let loadingIndicator = this.getCalculationLoadingIndicator();
         loadingIndicator.setVisible(false); 
      },
      showCalculationLoadingIndicator : function () {
         let loadingIndicator = this.getCalculationLoadingIndicator();
         loadingIndicator.setVisible(true);
      },
      getCalculationLoadingIndicator : function () {
         let oView = this.getCurrentView();
         return oView.byId("calculationLoadingIndicator");
      }
   });
});