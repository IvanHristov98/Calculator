sap.ui.define([
    "../localService/mockserver"
], function (mockserver) {
    "use strict";

    // initialize the mock server
    mockserver.init();

    // initialize the embedded component on the html page
    sap.ui.require(["sap/ui/core/ComponentSupport"]);
});