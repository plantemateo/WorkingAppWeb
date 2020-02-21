angular.module('frontend').controller('ModalConfirmarCtrl', function (
	$scope,
	$uibModalInstance,
	message,
	titulo
) {
	$scope.titulo = titulo;
	$scope.mensaje = 'Esta seguro/a que desea ' + message + '?';
	$scope.mensaje2 = 'El usuario ' + message + '!';
	$scope.mensaje3 = 'Usuario: ' + message;
	

	// Returns
	$scope.renew = function(renew) {
		$uibModalInstance.close(renew);
	}
	
	$scope.elegir = function (eleccion) {
		$uibModalInstance.close(eleccion);
	};
	$scope.cancelar = function () {
		$uibModalInstance.dismiss('cancel');
	};
});
