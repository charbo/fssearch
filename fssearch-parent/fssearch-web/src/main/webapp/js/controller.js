'use strict';

var controllers = angular.module("controllers", []);

controllers.controller("HomeController", [ '$scope', function($scope) {
	homeController($scope);
} ]);

controllers.controller("SearchController", [ '$scope', '$http', function($scope, $http) {
	searchController($scope, $http);
} ]);