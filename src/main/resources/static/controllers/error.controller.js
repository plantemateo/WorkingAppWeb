angular.module('frontend')
	.controller('ModalErrorCtrl', function ($scope, $uibModalInstance, error, message, trace) {
		$scope.error = error;
		$scope.message = message;
		$scope.trace = trace;

		// Returns
		$scope.ok = function () {
			$uibModalInstance.close();
		};
		$scope.cancelar = function () {
			$uibModalInstance.dismiss();
		};
	});
