//var x = require('casper').selectXPath;

var casper = require('casper').create({
    pageSettings: {
        loadImages: false, // The WebPage instance used by Casper will
        loadPlugins: false // use these settings
    },
    logLevel: "debug", // Only "info" level messages will be logged
    verbose: true // log messages will be printed out to the console
});


var currentPageURL = 'http://www.amazon.de/Aptamil-Kindermilch-plus-Jahr-Pack/dp/B00BSNACII/';


casper.start(currentPageURL, function() {});


var selector_data = '#priceblock_ourprice';

var data = '';
casper.then(function() {
    casper.waitForSelector(selector_data,
        function then() {
            if (!casper.exists(selector_data)) {
                casper.die("Debug>>> No selector_data selector exists, please check whether selector changed or not .", 1);
            } else {
                this.echo("Debug>>> selector_data object exists!");
                data = this.fetchText(selector_data);
                this.echo("Debug>>> data is "+ data);
                
                var dataInfo = this.getElementsInfo(selector_data);
                require('utils').dump(dataInfo);
            }
        },
        function onTimeout() {
            casper.die("Debug>>> No such selector_data selector, wait time out.", 2);
        },
        10000
    );
});



casper.run(function() {
    //require('utils').dump(JSON.parse(this.getPageContent()));
    this.echo('exit').exit();
});
