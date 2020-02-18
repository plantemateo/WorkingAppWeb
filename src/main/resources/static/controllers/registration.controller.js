angular.module('frontend').controller('ModalRegistrationCtrl',
		function($scope, $rootScope, $uibModalInstance, $uibModal ,userService) {

			$scope.newUser = {
				firstName : '',
				lastName : '',
				email : '',
				username : '',
				password : ''

			};

			$scope.register = function() {
				let modalInstance = $uibModal.open({
					templateUrl : 'views/confirmarMsj.form.html',
					controller : 'ModalConfirmarCtrl',
					resolve : {
						message : function() {
							return 'a sido registrado con exito';
						},
						titulo : function() {
							return 'Registro exitoso';
						}
					}
				});
				modalInstance.result.then(function(eleccion) {
					if (eleccion) {
						userService.agregar($scope.newUser).then(function() {
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
