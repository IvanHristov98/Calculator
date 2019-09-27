sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel"
], function (Controller, JSONModel, Calculator) {
   "use strict";

   const limitOfHistoryItems = 10;

   return Controller.extend("com.calculator.webUi.controller.Calculator", {
      calculateResource : "https://calculator.cfapps.sap.hana.ondemand.com/api/v1/calculate",
      calculationResultsResource : "https://calculator.cfapps.sap.hana.ondemand.com/api/v1/calculationResults",
      intervalBetweenCompletionChecks: 1000,
      onInit : function () {
         this.refreshHistoryListItems(limitOfHistoryItems);
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
            data : oExpressionBar.getValue()
         };
         let oXhr = new XMLHttpRequest();

         this.runHttpRequest(oXhr, oHttpRequestDetails);
      },
      runHttpRequest : function (oXhr, oHttpRequestDetails) {
         oXhr.open(oHttpRequestDetails.method, oHttpRequestDetails.resource);
         oXhr.setRequestHeader("Content-Type", "application/json");
         oXhr.setRequestHeader("cache-control", "no-cache");
         oXhr.addEventListener("readystatechange", oHttpRequestDetails.readyStateListener);
         
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
            data : null
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
      refreshHistoryListItems : function (iLimit) {
         this.showHistoryLoadingIndicator();

         let oCalculator = this;
         let onCalculationResultsDownloaded = function () {
            oCalculator.onCalculationResultsDownloaded(this.readyState, this.responseText, oCalculator, iLimit);
         };

         let oHttpRequestDetails = {
            resource : this.calculationResultsResource,
            method: "GET",
            readyStateListener : onCalculationResultsDownloaded,
            data : null
         };

         let oXhr = new XMLHttpRequest();
         oCalculator.runHttpRequest(oXhr, oHttpRequestDetails);
      },
      onCalculationResultsDownloaded : function (oReadyState, sResponseText, oCalculator, iLimit) {
         if (oReadyState !== 4) {
            return;
         }

         let aCalculationResults = JSON.parse(sResponseText);
         aCalculationResults = oCalculator.getMostRecentCalculationResults(aCalculationResults, iLimit);

         let itemTemplate = new sap.m.StandardListItem({
            title : '{expression}',
            info : "{evaluation}{message}"
            });

         let oModel = new sap.ui.model.json.JSONModel();
         oModel.setData({calculationResults: aCalculationResults});

         let oHistoryList = oCalculator.getHistoryList();
         oHistoryList.bindItems("/calculationResults", itemTemplate);
         oHistoryList.setModel(oModel);

         oCalculator.hideHistoryLoadingIndicator();
      },
      getMostRecentCalculationResults : function (aCalculationResults, iLimit) {
         aCalculationResults.sort(function (left, right) {
            return right.moment - left.moment;
         });

         return aCalculationResults.slice(0, iLimit);
      },
      getHistoryList : function () {
         let oView = this.getCurrentView();
         return oView.byId("historyList");
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
      },
      hideHistoryLoadingIndicator : function () {
         let loadingIndicator = this.getHistoryLoadingIndicator();
         loadingIndicator.setVisible(false); 
      },
      showHistoryLoadingIndicator : function () {
         let loadingIndicator = this.getHistoryLoadingIndicator();
         loadingIndicator.setVisible(true);
      },
      getHistoryLoadingIndicator : function () {
         let oView = this.getCurrentView();
         return oView.byId("historyLoadingIndicator");
      }
   });
});