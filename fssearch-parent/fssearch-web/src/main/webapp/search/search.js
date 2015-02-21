function searchController($scope, $http) {
	$scope.temp = 'Coucou';

	$scope.search = function() {
		var query = $scope.searchTerm;
		
		$http.get('search?query=' + query).success(function(data, status, headers, config) {
			$scope.documents = data;
		});
		
	};
}