function searchController($scope) {
	$scope.temp = 'Coucou';

	$scope.search = function() {
		$scope.temp = $scope.searchTerm;
	};
}