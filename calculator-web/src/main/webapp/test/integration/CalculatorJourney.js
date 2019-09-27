/* global QUnit, opaTest */

sap.ui.define([
    "sap/ui/core/util/MockServer",
    "sap/ui/test/opaQunit",
	"./pages/Calculator"
], function (MockServer) {
    "use strict";

    QUnit.module("Calculator");

    opaTest("Verify number pad typing", function (Given, When, Then) {
        let tokens = {
            "one" : "1", "two" : "2", "three" : "3", "four" : "4", "five" : "5",
            "six" : "6", "seven" : "7", "eight" : "8", "nine" : "9", "zero" : "0",
            "openingBracket" : "(", "closingBracket" : ")", "dot" : ".",
            "plus" : "+", "minus" : "-", "product" : "*", "division" : "/", "pow" : "^"
        };

        Object.keys(tokens).map(function (key) {
            Given.iStartMyUIComponent(getComponentConfig());

            When.onTheAppPage.iPressCalculatorButton(key);
            Then.onTheAppPage.iShouldSeeOnExpressionBar(tokens[key]);

            Then.iTeardownMyApp();
        });
    });

    opaTest("Verify deletion of last token", function (Given, When, Then) {
        const dummyExpression = "12";
        const backspacedDummyExpression = "1";
        const backspaceButtonId = "backspace";

        Given.iStartMyUIComponent(getComponentConfig());

        When.onTheAppPage.iSetExpressionBarContent(dummyExpression);
        When.onTheAppPage.iPressCalculatorButton(backspaceButtonId);

        Then.onTheAppPage.iShouldSeeOnExpressionBar(backspacedDummyExpression);
        Then.iTeardownMyApp();
    });

    opaTest("Verify deletion of all tokens", function (Given, When, Then) {
        const dummyExpression = "12";
        const emptyExpression = "";
        const deleteButtonId = "delete";

        Given.iStartMyUIComponent(getComponentConfig());

        When.onTheAppPage.iSetExpressionBarContent(dummyExpression);
        When.onTheAppPage.iPressCalculatorButton(deleteButtonId);

        Then.onTheAppPage.iShouldSeeOnExpressionBar(emptyExpression);
        Then.iTeardownMyApp();
    });

    opaTest("Verify calculate event", function (Given, When, Then) {
        Given.iStartMyUIComponent(getComponentConfig());

        When.onTheAppPage.iSetExpressionBarContent("1+2*3");
        When.onTheAppPage.iPressCalculatorButton("calculate");

        Then.onTheAppPage.iShouldWaitForCalculationToStartLoading();
        Then.onTheAppPage.iShouldSeeOnExpressionBar("7");
        Then.onTheAppPage.iShouldWaitForCalculationToLoad();

        Then.iTeardownMyApp();
    });

    opaTest("Verify history list content", function (Given, When, Then) {
        const refreshButton = "refresh";
        const iExpectedNumberOfCalculationResults = 2;

        Given.iStartMyUIComponent(getComponentConfig());

        Then.onTheAppPage.iShouldWaitForHistoryToLoad();

        When.onTheAppPage.iPressHistoryButton(refreshButton);
        
        Then.onTheAppPage.iShouldWaitForHistoryToStartLoading();
        Then.onTheAppPage.iShouldWaitForHistoryToLoad();
        Then.onTheAppPage.iShouldSeeHistory(iExpectedNumberOfCalculationResults);
        Then.iTeardownMyApp();
    });

    function getComponentConfig() {
        return {
            componentConfig : {
                name : "com.calculator.webUi"
            }
        };
    }
});