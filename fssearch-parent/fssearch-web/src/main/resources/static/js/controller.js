'use strict';

var controllers = angular.module("controllers", []);

controllers.controller("AdminController", [ '$scope', '$location', function($scope, $location) {
	adminController($scope, $location);
} ]);

controllers.controller("SearchController", [ '$scope', '$http', function($scope, $http) {
	searchController($scope, $http);
} ]);

controllers.controller("navigation", ['$rootScope', '$scope', '$http', '$location', function($rootScope, $scope, $http, $location) {
	navigationController($rootScope, $scope, $http, $location);
} ]);