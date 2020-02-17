angular.module('frontend').controller('ModalRegistrationCtrl',
		function($scope, $rootScope, $uibModalInstance, userService) {
			
				$scope.newUser = {
				firstName : '',
				lastName : '',
				email : '',
				username : '',
				password : ''

			};

			$scope.register = function() {
				userService.agregar($scope.newUser).then(function() {
					$uibModalInstance.dismiss(true);
					alert("EL REGISTRO A SIDO EXITOSO!");
				}, function(err) {
					$rootScope.openErrorModal(err);
				});
			};

			$scope.cancelar = function() {
				$uibModalInstance.dismiss('cancel');
			};
		});
