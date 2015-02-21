'use strict';

var app = angular.module('app', [ 'ngRoute', 'controllers' ]);

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/home', {
		templateUrl : 'home/home.html',
		controller : 'HomeController'
	}).when('/search', {
		templateUrl : 'search/search_panel.html',
		controller : 'SearchController'
	}).when('/login', {
		templateUrl : 'login.html',
		controller : 'navigation'
	}).otherwise({
		redirectTo : '/'
	});
	
} ]);