angular.module('frontend').controller('ConfigureController',
		function($scope, userService, $rootScope, $uibModalInstance, $uibModal) {
				
		$scope.modifiUser = {
				firstName : '',
				lastName : '',
				email : '',
				username : '',
				password : ''

			};
		$scope.modPerfil = function() {
			let modalInstance = $uibModal.open({
				templateUrl : 'views/confirmarMsj.form.html',
				controller : 'ModalConfirmarCtrl',
				resolve : {
					message : function() {
						return 'a sido modificado con exito, por favor ingrese nuevamente';
					},
					titulo : function() {
						return 'Modificacion exitosa';
					}
				}
			});
			modalInstance.result.then(function(eleccion) {
				if (eleccion) {
					userService.actualizar($scope.modifiUser).then(function() {
						$rootScope.logout();
						$uibModalInstance.dismiss(true);
					}, function(err) {
						$rootScope.openErrorModal(err);
						$uibModalInstance.dismiss('cancel');
					});
				}
			}, function(err) {
				if (err.hasOwnProperty('data')) {
					$rootScope.openErrorModal(err);
				}
			});

		};
		
		$scope.cancelar = function () {
			$uibModalInstance.dismiss('cancel');
		};
});
