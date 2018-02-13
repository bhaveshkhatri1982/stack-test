var restApp = angular.module('restApp',['ngRoute', 'ngTouch', 'ngAnimate']);

var urlBase= window.location.href;
urlBase = urlBase.split("/pages")[0];
restApp.factory('AuthInterceptor', function ($window, $q) {
    return {
        request: function(config) {
            config.headers = config.headers || {};
            if ($window.sessionStorage.getItem('token')) {
                config.headers.Authorization = 'Bearer ' + $window.sessionStorage.getItem('token');
            }
            return config || $q.when(config);
        },
        response: function(response) {
            if (response.status === 401 || response.status === 403) {
                // TODO: Redirect user to login page.
            	$window.location.href = urlBase +'/pages/index.html';
            }
            return response || $q.when(response);
        }
    };
});
// Register the previously created AuthInterceptor.
restApp.config(function ($httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
});
restApp.filter('reverse', function() {
	  return function(items) {
		    return items.slice().reverse();
	  };
});
restApp.controller('stackController', ['$scope', '$rootScope','$http', '$q', '$window','$timeout', function ($scope, $rootScope, $http, $q, $window,$timeout)
{
	$scope.number = "";
	$scope.peekedNumber = "";
	$scope.stack=[];

	$scope.getAuthenticatedUser = function()
	{
		var a = $http.get(urlBase + '/stack/getAuthenticatedUser')
		a.success(function(jsonData) 
		{	  
			
		});
		a.error(function() 
		{	  
			$window.location.href = urlBase +'/pages/index.html';
		});
	};
	$scope.getAuthenticatedUser();
	
	$scope.display = function()
	{
		$scope.promise = $http.get(urlBase + '/stack/display')
		.success(function(stack) 
		{	
			$scope.stack = stack;
		});
	};
	$scope.display();
	
	$scope.push = function()
	{
		$scope.promise = $http.get(urlBase + '/stack/push/'+$scope.number)
		.success(function(stack) 
		{	
			$scope.stack = stack;
			$scope.number = "";
		});
	};
	
	$scope.pop = function()
	{
		$scope.peek();
		$scope.promise = $http.get(urlBase + '/stack/pop')
		.success(function(stack) 
		{	
			$scope.stack = stack;
		});
	};
	
	$scope.peek = function()
	{
		$scope.promise = $http.get(urlBase + '/stack/peek')
		.success(function(peekStatus) 
		{	
			if(peekStatus.message == 'Empty')
			{
				$scope.peekedNumber = "";
				alert("Stack is empty");
			}	
			else
			{	
				$scope.peekedNumber = peekStatus.peekedNumber;
			}	
		});
	};
	
	$scope.logout = function()
	{
		$scope.promise = $http.get(urlBase + '/stack/logout')
		.success(function() 
		{	
			$scope.number = "";
			$scope.peekedNumber = "";
			$scope.stack=[];
		});
		sessionStorage.clear();
		localStorage.clear();
		$window.location.href = urlBase +'/pages/index.html';
	};
	
}]);
