angular.module('frontend').controller('ModalConfirmarCtrl', function (
	$scope,
	$uibModalInstance,
	message,
	titulo
) {
	$scope.titulo = titulo;
	$scope.mensaje = 'Esta seguro/a que desea ' + message + '?';

	// Returns
	$scope.elegir = function (eleccion) {
		$uibModalInstance.close(eleccion);
	};
	$scope.cancelar = function () {
		$uibModalInstance.dismiss('cancel');
	};
});
