var DATE_DISPLAY_FORMAT = "MMMM d, y";
var loginApp = angular.module('loginApp', ['cgBusy']);

var urlBase= window.location.href;
urlBase = urlBase.split("/pages")[0];
localStorage.setItem("urlBase",urlBase);

loginApp.factory('AuthInterceptor', function ($window, $q) {
    return {
        request: function(config) {
            config.headers = config.headers || {};
            if ($window.sessionStorage.getItem('token')) {
                config.headers.Authorization = 'Bearer ' + $window.sessionStorage.getItem('token');
            }
            return config || $q.when(config);
        },
        response: function(response) {
            if (response.status === 401) {
                // TODO: Redirect user to login page.
            }
            return response || $q.when(response);
        }
    };
});

// Register the previously created AuthInterceptor.
loginApp.config(function ($httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
});

loginApp.directive('ngEnter', function () {
    return function (scope, element, attrs) {
        element.bind("keydown keypress", function (event) {
            if(event.which === 13) {
                scope.$apply(function (){
                    scope.$eval(attrs.ngEnter);
                });
 
                event.preventDefault();
            }
        });
    };
});

loginApp.controller('loginAppController', ['$scope', '$rootScope', '$http', '$q', '$window', function ($scope, $rootScope, $http, $q, $window)
{
	$scope.delay = 0;
	$scope.minDuration = 0;
	$scope.message = 'Logging in please wait...';
	$scope.backdrop = true;
	$scope.promise = null;
	
   	$scope.grant_type="password";
   	$scope.client_id="restapp";
   	$scope.client_secret="restapp";
   	$scope.emailAddress = "admin@admin.com";
   	$scope.password = "admin";
   	
   	$scope.loginForm = function()
   	{
	
   		localStorage.setItem("emailAddress", $scope.emailAddress);
   		$scope.promise =  $http({                   
		       method:'post',
		       url:urlBase +'/oauth/token?grant_type='+$scope.grant_type+'&client_id='+$scope.client_id+'&client_secret='+$scope.client_secret+'&username='+$scope.emailAddress+'&password='+$scope.password
		       //headers: {'Content-Type': 'application/json'}
		   })
		   .success(function(data) 
		   {
			   //access_token
			   	localStorage.setItem("access_token", data.access_token);
			   	$window.sessionStorage.setItem('token', data.access_token);
		   		$window.location.href = urlBase +'/pages/home.html';
		   });
   }

}]);