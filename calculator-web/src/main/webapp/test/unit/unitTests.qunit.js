/* Global QUnit */

QUnit.config.autostart = false;

sap.ui.getCore().attachInit(function () {
    "use strict";

    sap.ui.require([
        "com/calculator/webUi/test/unit/controller/Calculator.controller"
    ], function () {
        QUnit.start();
    });
});