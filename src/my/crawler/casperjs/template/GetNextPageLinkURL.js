
function getLinks(linksSelector) {
    var links = document.querySelectorAll(linksSelector);
    return Array.prototype.map.call(links, function(e) {
        return e.getAttribute('href');
    });
}

var x = require('casper').selectXPath;

var casper = require('casper').create({
    pageSettings: {
        loadImages: false, // The WebPage instance used by Casper will
        loadPlugins: false // use these settings
    },
    logLevel: "debug", // Only "info" level messages will be logged
    verbose: true // log messages will be printed out to the console
});


var currentPageURL = 'http://www.amazon.de';


casper.start(currentPageURL, function() {});

/*
var selector_searchInputBox = 'input#twotabsearchtextbox'; //'input[name = "field-keywords"]';   //;twotabsearchtextbox';
var selector_searchInputBoxForm = 'form#nav-searchbar';
var searchItemName = 'Aptamil';
casper.then(function() {

    
    //wait for search input text box
    casper.waitForSelector(selector_searchInputBox,
        function then() {
            if (!casper.exists(selector_searchInputBox)) {
                casper.die("Debug>>> No such selector_searchInputBox exists, please check whether selector_searchInputBox changed or not .", 1);
            } else {
                this.echo("Debug>>> selector_searchInputBox exists!");
                //require('utils').dump(this.getElementInfo(selector_searchInputBox));
                //this.echo('Debug>>> at this point, selector_searchInputBox is '+ selector_searchInputBox);
                var obj = {};
                obj[selector_searchInputBox] = searchItemName; // here the value of selector_searchInputBox is used, not its name
                this.fillSelectors(selector_searchInputBoxForm, obj, true);
            }
        },
        function onTimeout() {
            casper.die("Debug>>> No such selector exists, please check whether selector_searchInputBox changed or not .", 2);
        },
        10000
    );


});
*/

var selector_PagnNextLink = '#pagnNextLink';

var links = new Array();
casper.then(function() {
    casper.waitForSelector(selector_PagnNextLink,
        function then() {
            if (!casper.exists(selector_PagnNextLink)) {
                casper.die("Debug>>> No such search result links selector exists, please check whether selector changed or not .", 1);
            } else {
                this.echo("Debug>>> search result links object exists!");
                links = this.evaluate(getLinks, selector_PagnNextLink);

                if (links.length < 1) {
                    this.echo("Debug>>> no links can be found in page");
                } else {
                    this.echo("Debug>>>" + links.length);
                    for (var i = 0; i < links.length; i++) {
                        this.echo("Debug>>> " + links[i]);
                    }

                    //var linksInfo = this.getElementsInfo(selector_link);
                    //require('utils').dump(links[0]);
                }
            }
        },
        function onTimeout() {
            casper.die("Debug>>> No such search result links selector, wait time out.", 2);
        },
        10000
    );
});



casper.run(function() {
    //require('utils').dump(JSON.parse(this.getPageContent()));
    //ßthis.echo("Debug>>> " + this.getCurrentUrl());
    this.echo('exit').exit();
});