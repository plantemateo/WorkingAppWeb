angular.module('frontend')
	.config(function ($routeProvider, $httpProvider, $localStorageProvider) {
		$localStorageProvider.setKeyPrefix('frontend/');
		$httpProvider.defaults.withCredentials = true;
		$httpProvider.interceptors.push('APIInterceptor');

		$routeProvider
			.when('/main', {
				templateUrl: 'views/main.html',
				controller: 'MainController'
			})
			.when('/tablero/:tableroId', {
				templateUrl: 'views/tablero.html',
				controller: 'TableroController'
			})
			.otherwise({
				redirectTo: '/main'
			});
	});
