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

    let sandbox;

    QUnit.module("Calculator controller test", {
        beforeEach : function () {
            sandbox = sinon.sandbox.create();

            this._oCalculatorController = new CalculatorController();

            this._oViewStub = new ManagedObject();
            this._oGetViewStub = sinon.stub(Controller.prototype, "getView").returns(this._oViewStub);
        },
        afterEach : function () {
            sandbox.restore();

            this._oGetViewStub.restore();
        }
    });

    QUnit.test("Verify token appending on press", function (assert) {
        const expression = "";
        const justAToken = "1";

        let oFakeExpressionBar = getFakeExpressionBar(expression);
        this._oViewStub.byId = sandbox.stub().returns(oFakeExpressionBar);
        
        this._oCalculatorController.onTokenPress(justAToken);
        assert.ok(oFakeExpressionBar.setValue.called);
    });

    QUnit.test("Verify deletion of last token", function (assert) {
        const expressionBeforeBackspacing = "12";
        const expressionAfterBackspacing = "1";

        let oFakeExpressionBar = getFakeExpressionBar(expressionBeforeBackspacing);
        this._oViewStub.byId = sandbox.stub().returns(oFakeExpressionBar);

        this._oCalculatorController.onBackspaceButtonPress();
        assert.ok(oFakeExpressionBar.setValue.calledWith(expressionAfterBackspacing));
    });

    QUnit.test("Verify deletion of all tokens", function (assert) {
        const expressionBeforeDeletion = "1+1";
        const emptyExpression = "";

        let oFakeExpressionBar = getFakeExpressionBar(expressionBeforeDeletion);
        this._oViewStub.byId = sandbox.stub().returns(oFakeExpressionBar);

        this._oCalculatorController.onDeleteAllButtonPress();
        assert.ok(oFakeExpressionBar.setValue.calledWith(emptyExpression));
    });

    QUnit.test("Verify XmlHttpRequest sending", function (assert) {
        let httpRequestDetails = getFakeHttpRequestDetails();
        let fakeXhr = getFakeXhr();
        this._oCalculatorController.runHttpRequest(fakeXhr, httpRequestDetails);

        assert.ok(fakeXhr.send.calledWith(httpRequestDetails.data));
    });

    QUnit.test("Verify XmlHttpRequest opening", function (assert) {
        let httpRequestDetails = getFakeHttpRequestDetails();
        let fakeXhr = getFakeXhr();
        this._oCalculatorController.runHttpRequest(fakeXhr, httpRequestDetails);

        assert.ok(fakeXhr.open.calledWith(httpRequestDetails.method, httpRequestDetails.resource));
    });

    QUnit.test("Verify XmlHttpRequest setting of ready state listener", function (assert) {
        let httpRequestDetails = getFakeHttpRequestDetails();
        let fakeXhr = getFakeXhr();
        this._oCalculatorController.runHttpRequest(fakeXhr, httpRequestDetails);

        assert.ok(fakeXhr.addEventListener.calledWith("readystatechange", httpRequestDetails.readyStateListener));
    });

    QUnit.test("Verify behaviour with correct expression", function (assert) {
        let fakeCalculationResult = {
            evaluation : 1.0,
            message : null
        };

        assert.ok(this._oCalculatorController.isExpressionCorrect(fakeCalculationResult));
        assert.notOk(this._oCalculatorController.isExpressionIncorrect(fakeCalculationResult));
    });

    QUnit.test("Verify behaviour with incorrect expression", function (assert) {
        let fakeCalculationResult = {
            evaluation : null,
            message : "Error."
        };

        assert.notOk(this._oCalculatorController.isExpressionCorrect(fakeCalculationResult));
        assert.ok(this._oCalculatorController.isExpressionIncorrect(fakeCalculationResult));
    });

    QUnit.test("Verify no actions at expression pending status", function (assert) {
        let iReadyState = 4;
        let sResponseText = '{"status" : 1}';
        let oCalculationResultGettingInterval = {};

        let oFakeExpressionBar = getFakeExpressionBar("");
        this._oViewStub.byId = sandbox.stub().returns(oFakeExpressionBar);

        this._oCalculatorController.checkIfCalculationIsCompleted(iReadyState, sResponseText, oCalculationResultGettingInterval);

        assert.notOk(oFakeExpressionBar.setValue.called);
    });

    QUnit.test("Verify expression bar setting with correct value", function (assert) {
        let iReadyState = 4;
        let iExpectedEvaluation = 1.0;
        let sResponseText = '{"status" : 2, "message" : null, "evaluation" : ' + iExpectedEvaluation + '}';
        let oCalculationResultGettingInterval = {};

        let oFakeExpressionBar = getFakeExpressionBar("");
        this._oViewStub.byId = sandbox.stub().returns(oFakeExpressionBar);
        
        this._oCalculatorController.checkIfCalculationIsCompleted(iReadyState, sResponseText, oCalculationResultGettingInterval);

        assert.ok(oFakeExpressionBar.setValue.calledWith(iExpectedEvaluation));
    });

    QUnit.test("Verify expression bar setting with incorrect value", function (assert) {
        let iReadyState = 4;
        let sExpectedError = "ExpectedError";
        let sResponseText = '{"status" : 2, "message" : "' + sExpectedError + '", "evaluation" : null}';
        let oCalculationResultGettingInterval = {};
        
        let oFakeExpressionBar = getFakeExpressionBar("");
        this._oViewStub.byId = sandbox.stub().returns(oFakeExpressionBar);
        
        this._oCalculatorController.checkIfCalculationIsCompleted(iReadyState, sResponseText, oCalculationResultGettingInterval);

        assert.ok(oFakeExpressionBar.setValue.calledWith(sExpectedError));
    });

    QUnit.test("Verify the start of retrieving job", function (assert) {
        const clock = sinon.useFakeTimers();
        const expectedIntervalBetweenCalls = 1000;

        let iReadyState = 4;
        let sResponseText = "1";

        const oCalculator = new CalculatorController();
        oCalculator.getCompletedCalculationResult = sandbox.stub();

        oCalculator.startResultRetrievingJob(iReadyState, sResponseText, oCalculator);

        clock.tick(expectedIntervalBetweenCalls);
        assert.ok(oCalculator.getCompletedCalculationResult.calledOnce);

        clock.restore();
    });

    QUnit.test("Verify expression uploading", function (assert) {
        let oFakeExpressionBar = getFakeExpressionBar("");
        this._oViewStub.byId = sandbox.stub().returns(oFakeExpressionBar);

        this._oCalculatorController.runHttpRequest = sandbox.stub();
        this._oCalculatorController.uploadExpression();

        assert.ok(this._oCalculatorController.runHttpRequest.calledOnce);
    });

    QUnit.test("Verify getting of completed calculation results.", function (assert) {
        let oFakeExpressionBar = getFakeExpressionBar("");
        this._oViewStub.byId = sandbox.stub().returns(oFakeExpressionBar);

        const oCalculationResultId = "1";
        const iCalculationResultGettingInterval = 1;

        const oCalculator = {};
        oCalculator.runHttpRequest = sandbox.stub();

        this._oCalculatorController.getCompletedCalculationResult(oCalculationResultId, iCalculationResultGettingInterval, oCalculator);

        assert.ok(oCalculator.runHttpRequest.calledOnce);
    });

    function getFakeExpressionBar(sExpressionContent) {
        let oFakeExpressionBar = new Input();
        oFakeExpressionBar.getValue = sandbox.stub().returns(sExpressionContent);
        oFakeExpressionBar.setValue = sandbox.stub();

        return oFakeExpressionBar;
    }

    function getFakeHttpRequestDetails() {
        return {
            resource : "nowhere",
            method: "GET",
            readyStateListener : function () {},
            data : null
        };
    }

    function getFakeXhr() {
        let fakeXhr = {};
        fakeXhr.open = sandbox.stub();
        fakeXhr.setRequestHeader = sandbox.stub();
        fakeXhr.addEventListener = sandbox.stub();
        fakeXhr.send = sandbox.stub();

        return fakeXhr;
    }
});