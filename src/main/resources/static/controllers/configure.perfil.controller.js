angular.module('frontend').controller('ConfigureController',
		function($scope, userService, $rootScope, $uibModalInstance, $uibModal) {
				
		$scope.user = $rootScope.currentLoggedUser;
		
		$scope.editUser	= {	
					idUser : $scope.user.idUser,
					username : $scope.user.username,
					firstName : $scope.user.firstName,
					lastName : $scope.user.lastName,
					email : $scope.user.email,
					password : ''
			};
		
		$scope.modPerfil = function() {
			let modalInstance = $uibModal.open({
				templateUrl : 'views/confirmarMsj1.form.html',
				controller : 'ModalConfirmarCtrl',
				resolve : {
					message : function() {
						return 'a sido modificado con exito, por favor ingrese nuevamente!';
					},
					titulo : function() {
						return 'Modificacion exitosa';
					}
				}
			});
			modalInstance.result.then(function(eleccion) {
				if (eleccion) {
					userService.actualizar($scope.editUser).then(function() {
								$uibModalInstance.dismiss(true);
								$rootScope.logout();		
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
