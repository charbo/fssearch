function navigationController($rootScope, $scope, $http, $location) {
	var authenticate = function(callback) {

	    $http.get('user').success(function(data) {
	      if (data.name) {
	        $rootScope.authenticated = true;
	      } else {
	        $rootScope.authenticated = false;
	      }
	      callback && callback();
	    }).error(function() {
	      $rootScope.authenticated = false;
	      callback && callback();
	    });

	  }
		    
		    authenticate();
		    $scope.credentials = {};
		    $scope.login = function() {
		    	try {
		      $http.post('login', 'username='+$scope.credentials.username+'&password='+$scope.credentials.password, {
		        headers : {
		          "content-type" : "application/x-www-form-urlencoded"
		        }
		      }).success(function(data) {
		        authenticate(function() {
		          if ($rootScope.authenticated) {
		            $location.path("/admin");
		            $scope.error = false;
		          } else {
		            $location.path("/login");
		            $scope.error = true;
		          }
		        });
		      }).error(function(data) {
		        $location.path("/login");
		        $scope.error = true;
		        $rootScope.authenticated = false;
		      })
		    	} catch (e) {
		    		alert(e);
		    	}
		    };
}