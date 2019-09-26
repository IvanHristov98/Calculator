module.exports = function(config) {
    config.set({
        basePath: '../../main',
        frameworks : ['ui5'],
        ui5 : {
            mode : "html",
            url : "http://localhost:7777",
            testpage : "webapp/test/integration/opaTests.qunit.html"
        },
        browsers : ['ChromeHeadless'],
        singleRun : true
    });
};