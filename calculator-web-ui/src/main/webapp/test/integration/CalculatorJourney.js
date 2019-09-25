/* global QUnit, opaTest */

sap.ui.define([
    "sap/ui/core/util/MockServer",
    "sap/ui/test/opaQunit",
	"./pages/Calculator"
], function (MockServer) {
    "use strict";

    QUnit.module("Calculator", {
        beforeEach : function () {
        },
        afterEach : function () {

        }
    });

    opaTest("Verify number pad typing", function (Given, When, Then) {
        let tokens = {
            "one" : "1", "two" : "2", "three" : "3", "four" : "4", "five" : "5",
            "six" : "6", "seven" : "7", "eight" : "8", "nine" : "9", "zero" : "0",
            "openingBracket" : "(", "closingBracket" : ")", "dot" : ".",
            "plus" : "+", "minus" : "-", "product" : "*", "division" : "/", "pow" : "^"
        };

        Object.keys(tokens).map(function (key) {
            Given.iStartMyUIComponent(getComponentConfig());

            When.onTheAppPage.iPressButton(key);
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
        When.onTheAppPage.iPressButton(backspaceButtonId);

        Then.onTheAppPage.iShouldSeeOnExpressionBar(backspacedDummyExpression);

        Then.iTeardownMyApp();
    });

    opaTest("Verify deletion of all tokens", function (Given, When, Then) {
        const dummyExpression = "12";
        const emptyExpression = "";
        const deleteButtonId = "delete";

        Given.iStartMyUIComponent(getComponentConfig());

        When.onTheAppPage.iSetExpressionBarContent(dummyExpression);
        When.onTheAppPage.iPressButton(deleteButtonId);

        Then.onTheAppPage.iShouldSeeOnExpressionBar(emptyExpression);

        Then.iTeardownMyApp();
    });

    opaTest("Verify calculate event", function (Given, When, Then) {
        Given.iStartMyUIComponent(getComponentConfig());

        When.onTheAppPage.iSetExpressionBarContent("1+2*3");
        When.onTheAppPage.iPressButton("calculate");
        
        Then.onTheAppPage.iShouldSeeOnExpressionBar("7");
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