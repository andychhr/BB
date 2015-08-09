
var casper = require('casper').create({
    pageSettings: {
        loadImages: false, // The WebPage instance used by Casper will
        loadPlugins: false // use these settings
    },
    logLevel: "debug", // Only "info" level messages will be logged
    verbose: true // log messages will be printed out to the console
});

var currentPageURL = '$$currentPageURL$$';

casper.start(currentPageURL, function() {});


var selector_searchInputBox = '$$selector_searchInputBox$$'; 
var selector_searchInputBoxForm = '$$selector_searchInputBoxForm$$';
var searchItemName = '$$itemName$$';

casper.then(function() {

    /* 
    wait for search input text box
	*/
    casper.waitForSelector(selector_searchInputBox,
        function then() {
            if (!casper.exists(selector_searchInputBox)) {
                casper.die("Debug>>> No such selector_searchInputBox exists, please check whether selector_searchInputBox changed or not .", 1);
            } else {
            	var attribute_name = this.getElementAttribute(selector_searchInputBox, 'name');
                var obj = {};
                obj[attribute_name] = searchItemName; 
                this.fill(selector_searchInputBoxForm, obj, true);
            }
        },
        function onTimeout() {
            casper.die("Debug>>> No such selector exists, please check whether selector_searchInputBox changed or not .", 2);
        },
        10000
    );


});


casper.run(function() {
    this.echo("Debug>>> "+this.getCurrentUrl());
    this.exit();
});
