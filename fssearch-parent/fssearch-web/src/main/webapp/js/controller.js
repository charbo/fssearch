'use strict';

var controllers = angular.module("controllers", []);

controllers.controller("HomeController", [ '$scope', function($scope) {
	homeController($scope);
} ]);

controllers.controller("SearchController", [ '$scope', '$http', function($scope, $http) {
	searchController($scope, $http);
} ]);

controllers.controller("navigation", ['$rootScope', '$scope', '$http', '$location', function($rootScope, $scope, $http, $location) {
	navigationController($rootScope, $scope, $http, $location);
} ]);