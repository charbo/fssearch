'use strict';

var app = angular.module('app', [ 'ngRoute', 'controllers', 'zeroclipboard' ]);

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/admin', {
		templateUrl : 'admin/admin.html',
		controller : 'AdminController'
	}).when('/search', {
		templateUrl : 'search/search_panel.html',
		controller : 'SearchController'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation'
	}).otherwise({
		redirectTo : '/search'
	});
	
} ]);

app.config(['uiZeroclipConfigProvider', function(uiZeroclipConfigProvider) {

    // config ZeroClipboard
    uiZeroclipConfigProvider.setZcConf({
      swfPath: '../vendor/zeroclipboard/dist/ZeroClipboard.swf'
    });

  }]);