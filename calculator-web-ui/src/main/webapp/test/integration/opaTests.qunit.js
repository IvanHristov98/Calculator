/* Global QUnit */

QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
    "use strict";

    sap.ui.require([
        "com/calculator/webUi/test/initMockServer",
        "com/calculator/webUi/test/integration/CalculatorJourney"
    ], function () {
        QUnit.start();
    });
});