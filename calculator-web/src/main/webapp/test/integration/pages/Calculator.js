sap.ui.define([
    "sap/ui/test/Opa5",
	"sap/ui/test/actions/Press"
], function (Opa5, Press) {
    "use strict";
    
    const sCalculatorViewName = "com.calculator.webUi.view.Calculator";
    const sHistoryViewName = "com.calculator.webUi.view.History";
    const sExpressionBarId = "expressionBar";
    const sHistoryListId = "historyList";
    const sHistoryLoadingIndicatorId = "historyLoadingIndicator";
    const sCalculationLoadingIndicatorId = "calculationLoadingIndicator";

    Opa5.createPageObjects({
        onTheAppPage : {
            actions : {
                iPressCalculatorButton : function (sButtonId) {
                    this.iPressButton(sButtonId, sCalculatorViewName);
                },
                iPressHistoryButton : function (sButtonId) {
                    this.iPressButton(sButtonId, sHistoryViewName);
                },
                iPressButton : function (sButtonId, sViewName) {
                    return this.waitFor({
                        id : sButtonId,
                        viewName : sViewName,
                        actions : new Press(),
                        errorMessage : "Did not find the 'Button " + sButtonId + "' on the Calculator view."
                    });
                },
                iSetExpressionBarContent : function (sExpressionBarContent) {
                    return this.waitFor({
                        id : sExpressionBarId,
                        viewName : sCalculatorViewName,
                        actions : function (oExpressionBar) {
                            oExpressionBar.setValue(sExpressionBarContent);
                        }
                    });
                }
            },
            assertions : {
                iShouldSeeOnExpressionBar : function (sExpectedExpressionBarContent) {
                    return this.waitFor({
                        id : sExpressionBarId,
                        viewName : sCalculatorViewName,
                        matchers : function (oInput) {
                            return oInput.getValue() == sExpectedExpressionBarContent;
                        },
                        success : function (oExpressionBar) {
                            Opa5.assert.ok(
                                true, 
                                "Matched '" + sExpectedExpressionBarContent + "'."
                            );
                        },
                        errorMessage : "Expected '" + sExpectedExpressionBarContent + "'."
                    });
                },
                iShouldWaitForCalculationToStartLoading : function () {
                    this.iShouldWaitForIndicatorToStartLoading(sCalculationLoadingIndicatorId, sCalculatorViewName);
                },
                iShouldWaitForCalculationToLoad : function () {
                    this.iShouldWaitForIndicatorToLoad(sCalculationLoadingIndicatorId, sCalculatorViewName);
                },
                iShouldWaitForHistoryToStartLoading : function () {
                    this.iShouldWaitForIndicatorToStartLoading(sHistoryLoadingIndicatorId, sHistoryViewName);
                },
                iShouldWaitForHistoryToLoad : function () {
                    this.iShouldWaitForIndicatorToLoad(sHistoryLoadingIndicatorId, sHistoryViewName);
                },
                iShouldWaitForIndicatorToStartLoading : function (iIndicatorId, sViewName) {
                    return this.waitFor({
                        id : iIndicatorId,
                        viewName : sViewName,
                        visible: true,
                        success : function () {
                            Opa5.assert.ok(true, "History started loading.");
                        },
                        errorMessage : "Wait for history exceeded maximum possible time."
                    });
                },
                iShouldWaitForIndicatorToLoad : function (iIndicatorId, sViewName) {
                    return this.waitFor({
                        id : iIndicatorId,
                        viewName : sViewName,
                        visible : false,
                        matchers : function (oHistoryListIndicator) {
                            return !oHistoryListIndicator.getVisible();
                        },
                        success : function () {
                            Opa5.assert.ok(true, "Waited for history to load.");
                        },
                        errorMessage : "Wait for history exceeded maximum possible time."
                    });
                },
                iShouldSeeHistory : function (iExpectedNumberOfCalculationResults) {
                    return this.waitFor({
                        id : sHistoryListId,
                        viewName : sHistoryViewName,
                        success : function (oHistoryList) {
                            let aItems = oHistoryList.getItems();

                            Opa5.assert.strictEqual(
                                aItems.length, 
                                iExpectedNumberOfCalculationResults, 
                                "Correct number of calculation results.");
                        }
                    });
                }
            }
        }
    });
});
