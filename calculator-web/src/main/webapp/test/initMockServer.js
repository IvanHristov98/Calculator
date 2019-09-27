sap.ui.define([
    "sap/ui/core/util/MockServer",
	"sap/base/util/UriParameters"
], function (MockServer, UriParameters) {
    "use strict";

    let oCalculate = {
        method : "POST",
        path : new RegExp("calculate"),
        response : function (oXhr) {
            oXhr.respondJSON(202, {
                "Access-Control-Allow-Origin" : "*",
                "Content-Type" : "application/json"
            }, "1");
        }
    };

    let sCalculationResultData = '{"requestId":1,"expression":"1+2*3","moment":1568291169665,"evaluation":7.0,"message":null,"status":2}';

    let oCalculationResult = {
        method : "GET",
        path : new RegExp("calculationResults/1"),
        response : function (oXhr) {
            oXhr.respondJSON(200, {
                "Access-Control-Allow-Origin" : "*",
                "Content-Type" : "application/json"
            }, sCalculationResultData);
        }
    };
    
    let oMockServer = new MockServer({
        rootUri : "https://calculator.cfapps.sap.hana.ondemand.com/api/v1/",
        requests : [oCalculate, oCalculationResult]
    });

    let oUriParameters = new UriParameters(window.location.href);

    // configure mock server with a delay
    MockServer.config({
        autoRespond : true,
        autoRespondAfter : oUriParameters.get("serverDelay") || 500
    });

    oMockServer.start();
});