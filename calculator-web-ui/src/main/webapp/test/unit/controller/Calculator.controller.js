sap.ui.define([
    "sap/ui/base/ManagedObject",
    "sap/ui/core/mvc/Controller",
    "sap/m/Input",
    "com/calculator/webUi/controller/Calculator.controller",
    "sap/ui/model/json/JSONModel",
    "sap/ui/thirdparty/sinon",
	"sap/ui/thirdparty/sinon-qunit"
], function (ManagedObject, Controller, Input, CalculatorController, JSONModel, sinon, sinonQunit) {
    "use strict";

    QUnit.module("Calculator controller test", {
        beforeEach : function () {
            this._oCalculatorController = new CalculatorController();

            this._oViewStub = new ManagedObject();
            this._oGetViewStub = sinon.stub(Controller.prototype, "getView").returns(this._oViewStub);
        },
        afterEach : function () {
            this._oGetViewStub.restore();
        }
    });

    QUnit.test("Verify token appending on press", function (assert) {
        const expression = "";
        const justAToken = "1";

        let oFakeExpressionBar = getFakeExpressionBar(expression);
        this._oViewStub.byId = sinon.stub().returns(oFakeExpressionBar);
        
        this._oCalculatorController.onTokenPress(justAToken);
        assert.ok(oFakeExpressionBar.setValue.called);
    });

    QUnit.test("Verify deletion of last token", function (assert) {
        const expressionBeforeBackspacing = "12";
        const expressionAfterBackspacing = "1";

        let oFakeExpressionBar = getFakeExpressionBar(expressionBeforeBackspacing);
        this._oViewStub.byId = sinon.stub().returns(oFakeExpressionBar);

        this._oCalculatorController.onBackspaceButtonPress();
        assert.ok(oFakeExpressionBar.setValue.calledWith(expressionAfterBackspacing));
    });

    QUnit.test("Verify deletion of all tokens", function (assert) {
        const expressionBeforeDeletion = "1+1";
        const emptyExpression = "";

        let oFakeExpressionBar = getFakeExpressionBar(expressionBeforeDeletion);
        this._oViewStub.byId = sinon.stub().returns(oFakeExpressionBar);

        this._oCalculatorController.onDeleteAllButtonPress();
        assert.ok(oFakeExpressionBar.setValue.calledWith(emptyExpression));
    });

    function getFakeExpressionBar(sExpressionContent) {
        let oFakeExpressionBar = new Input();
        oFakeExpressionBar.getValue = sinon.stub().returns(sExpressionContent);
        oFakeExpressionBar.setValue = sinon.stub();

        return oFakeExpressionBar;
    }
});