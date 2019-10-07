sap.ui.define([
    "sap/ui/core/mvc/Controller",
    "sap/ui/model/json/JSONModel",
    "com/calculator/webUi/controller/Calculator.controller"
], function (Controller, JSONModel, Calculator) {
    "use strict";

    const limitOfHistoryItems = 10;
    const oCalculator = new Calculator();

    return Controller.extend("com.calculator.webUi.controller.App", {
        onInit : function () {
            this.refreshHistoryListItems(limitOfHistoryItems);
        },
        refreshHistoryListItems : function (iLimit) {
        this.showHistoryLoadingIndicator();

        let oHistory = this;
        let onCalculationResultsDownloaded = function () {
            oHistory.onCalculationResultsDownloaded(this.readyState, this.responseText, oHistory, iLimit);
        };

        let oHttpRequestDetails = {
            resource : oCalculator.calculationResultsResource,
            method: "GET",
            readyStateListener : onCalculationResultsDownloaded,
            data : null,
            xCsrfToken : oCalculator.xCsrfToken
        };

        let oXhr = new XMLHttpRequest();
        oCalculator.runHttpRequest(oXhr, oHttpRequestDetails);
        },
        onCalculationResultsDownloaded : function (oReadyState, sResponseText, oHistory, iLimit) {
        if (oReadyState !== 4) {
            return;
        }

        let aCalculationResults = JSON.parse(sResponseText);
        aCalculationResults = oHistory.getMostRecentCalculationResults(aCalculationResults, iLimit);

        let itemTemplate = new sap.m.StandardListItem({
            title : '{expression}',
            info : "{evaluation}{message}"
            });

        let oModel = new sap.ui.model.json.JSONModel();
        oModel.setData({calculationResults: aCalculationResults});

        let oHistoryList = oHistory.getHistoryList();
        oHistoryList.bindItems("/calculationResults", itemTemplate);
        oHistoryList.setModel(oModel);

        oHistory.hideHistoryLoadingIndicator();
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
        },
        getCurrentView : function () {
            return this.getView();
        }
    });
});