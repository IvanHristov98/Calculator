sap.ui.define([
    "sap/ui/core/UIComponent"
], function (UIComponent) {
    "use strict";

    return UIComponent.extend("com.calculator.webUi.Component", {
        metadata : {
            manifest: "json"
        },
        init : function () {
            UIComponent.prototype.init.apply(this, arguments);

            //create the view based on the url/hash
            this.getRouter().initialize();
        },
        exit : function () {
        }
    });
});