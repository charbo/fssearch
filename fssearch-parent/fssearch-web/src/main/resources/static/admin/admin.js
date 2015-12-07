function adminController($scope, $location) {
	try {
	if (!$scope.authenticated) {
		$location.path( "/login" );
	}
	$scope.title = 'Hello';
	} catch (e) {
		alert(e);
	}
}