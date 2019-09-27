sap.ui.define([
    "sap/ui/core/util/MockServer",
	"sap/base/util/UriParameters"
], function (MockServer, UriParameters) {
    "use strict";

    const sCalculateData = "1";
    const sCalculationResultData = '{"requestId":1,"expression":"1+2*3","moment":1568291169665,"evaluation":7.0,"message":null,"status":2}';
    const sCalculationResultsData = '['
        +'{"requestId":1,"expression":"1","moment":1568291169665,"evaluation":1.0,"message":null,"status":2},'
        +'{"requestId":2,"expression":"2","moment":1568291169666,"evaluation":2.0,"message":null,"status":2}'
        +']';

    let oCalculate = getMockedCalculateResource(sCalculateData);
    let oCalculationResult = getMockedCalculationResultResource(sCalculationResultData);
    let oCalculationResults = getMockedCalculationResultsResource(sCalculationResultsData);
    
    let oMockServer = new MockServer({
        rootUri : "https://calculator.cfapps.sap.hana.ondemand.com/api/v1/",
        requests : [oCalculate, oCalculationResult, oCalculationResults]
    });

    let oUriParameters = new UriParameters(window.location.href);

    // configure mock server with a delay
    MockServer.config({
        autoRespond : true,
        autoRespondAfter : oUriParameters.get("serverDelay") || 500
    });

    oMockServer.start();

    function getMockedCalculateResource(content) {
        return {
            method : "POST",
            path : new RegExp("calculate"),
            response : function (oXhr) {
                oXhr.respondJSON(202, {
                    "Access-Control-Allow-Origin" : "*",
                    "Content-Type" : "application/json"
                }, content);
            }
        };
    }

    function getMockedCalculationResultResource(content) {
        return getMockedGetResource("calculationResults/1", content);
    }

    function getMockedCalculationResultsResource(content) {
        return getMockedGetResource("calculationResults", content);
    }

    function getMockedGetResource (path, content) {
        return {
            method : "GET",
            path : new RegExp(path),
            response : function (oXhr) {
                oXhr.respondJSON(200, {
                    "Access-Control-Allow-Origin" : "*",
                    "Content-Type" : "application/json"
                }, content);
            }
        }
    }
});