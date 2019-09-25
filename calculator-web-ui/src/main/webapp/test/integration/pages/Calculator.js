sap.ui.define([
    "sap/ui/test/Opa5",
	"sap/ui/test/actions/Press"
], function (Opa5, Press) {
    "use strict";
    
    const sViewName = "com.calculator.webUi.view.Calculator";
    const sExpressionBarId = "expressionBar";

    Opa5.createPageObjects({
        onTheAppPage : {
            actions : {
                iPressButton : function (sButtonId) {
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
                        viewName : sViewName,
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
                        viewName : sViewName,
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
                } 
            }
        }
    });
});
