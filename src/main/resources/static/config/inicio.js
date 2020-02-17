angular.module('frontend')
	.constant('URL_API_BASE', '/api/v1/')
	.constant('URL_BASE', '/')
	.run(function (
		$rootScope,
		$location,
		$uibModal,
		$localStorage,
		coreService,
		userService
	) {

		$rootScope.loginOpen = false;
		$rootScope.openErrorModal = function (err) {
			if (err.status != 401 && err.hasOwnProperty('data')) {
				$uibModal.open({
					templateUrl: 'views/error.modal.html',
					controller: 'ModalErrorCtrl',
					resolve: {
						error: function () {
							return err.data.error;
						},
						message: function () {
							return err.data.message;
						},
						trace: function () {
							return err.data.trace;
						}
					}
				});
			}
		}
		$rootScope.loginData = $localStorage.userdata;
		if ($rootScope.loginData != undefined) {
			userService.cargarPorUsernameOrEmail($rootScope.loginData.username).then(
				function (resp) {							
					user = $rootScope.loginData.username;
					for (var i = 0; i < resp.data.length; i++) {
						if (resp.data[i].username == user) {
							$rootScope.currentLoggedUser = resp.data[i];	
						}
						
					}
					
				},
				function (err) {
					$rootScope.openErrorModal(err);
				}
			);
		}
		
		$rootScope.loggedIn = $localStorage.logged;

		$rootScope.openLoginForm = function (size) {
			if (!$rootScope.loginOpen) {
				$rootScope.loginOpen = true;
				$uibModal.open({
					animation: true,
					backdrop: 'static',
					keyboard: false,
					templateUrl: 'views/login.form.html',
					controller: 'LoginFormController',
					size: 'md'
				});
			}
		};

		$rootScope.cbauth = false;

		$rootScope.inRole = function (rol) {
			if (
				!$localStorage.logged ||
				!$localStorage.userdata.roles ||
				$localStorage.userdata.roles.length == 0
			)
				return false;
			var r = false;
			$localStorage.userdata.roles.forEach(function (o, i) {
				if (o == rol) {
					r = true;
					return false;
				}
			});
			return r;
		};
		$rootScope.authInfo = function (cb, rolif, cbrolif) {
			//Si el usuario está en el rol indicado en rolif, se ejecuta la callback cbrolif
			if (rolif && cbrolif && $rootScope.inRole('ROLE_' + rolif))
				cb = cbrolif;
			if (cb) $rootScope.cbauth = cb;
			if ($rootScope.cbauth && $localStorage.logged) $rootScope.cbauth();
		};

		$rootScope.logout = function () {
			delete $localStorage.userdata;
			$localStorage.logged = false;
			$rootScope.loginOpen = false;
			coreService.logout().then(
				function (resp) {
				},
				function () { }
			);
		};

		$rootScope.oldLoc = false;
		$rootScope.relocate = function (loc) {
			$rootScope.oldLoc = $location.$$path;
			$location.path(loc);
		};
		$rootScope.backLocation = function () {
			if ($rootScope.oldLoc) $location.path($rootScope.oldLoc);
		};
		
		$rootScope.openPerfil = function () {
			$uibModal.open({
				templateUrl: 'views/configure.perfil.html',
				controller: 'ConfigureController',
				animation: true,
				keyboard: false,
				size: 'md'
			});
		};
	});