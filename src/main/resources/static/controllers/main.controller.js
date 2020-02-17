angular.module('frontend').controller('MainController', function ($scope, $rootScope, $uibModal, $location, tableroService) {
	$rootScope.inBoard = false;
	$rootScope.sidebarVisible = false;
	$rootScope.colorFondo = 'ffffff';
	
	tableroService.listar().then(
		function (resp) {
			$scope.tableros = resp.data;
		},
		function (err) {
			$rootScope.openErrorModal(err);
		}
	);
	$scope.changeFav = function (board) {
		board.marcado = !board.marcado;
		tableroService.actualizar(board).then(
			function () {
			}, function (err) {
				$rootScope.openErrorModal(err);
			}
		);
	};
	$scope.enterBoard = function (tablero) {
		$location.path('/tablero/' + tablero.id);
	};
	$scope.createBoard = function () {
		let board = {
			posicion: $scope.tableros.length
		}
		let modalInstance = $uibModal.open({
			templateUrl: 'views/agregar.tablero.modal.html',
			controller: 'ModalBoardCtrl',
			resolve: {
				tablero: function () {
					return board;
				}
			}
		});
		modalInstance.result.then(
			function (board) {
				tableroService.agregar(board).then(
					function (resp) {
						$scope.tableros.push(resp.data);
					},
					function (err) {
						$rootScope.openErrorModal(err);
					}
				);
			},
			function (err) {
				if (err.hasOwnProperty('data')) {
					$rootScope.openErrorModal(err);
				}
			}
		);
	}

});
// # Modal agregar
angular.module('frontend').controller('ModalBoardCtrl', function ($scope, $rootScope, $uibModalInstance, userService, tablero) {
	let isUpdate = true;
	$scope.tablero = tablero;
	if (!tablero.hasOwnProperty('id')) {
		isUpdate = false;
		$scope.tablero.titulo = '';
		$scope.tablero.fondo = '#FF0000';
		$scope.tablero.creador = null;
		$scope.tablero.miembros = [];
		$scope.tablero.marcado = false;
		$scope.tablero.listas = null;
	}
	$scope.action = isUpdate ? 'Cambiar' : 'Añadir';
	$scope.tablero.creador = $rootScope.currentLoggedUser;
	$scope.tablero.miembros[0] = $rootScope.currentLoggedUser;
	$scope.colorOptions = {
		// # Validation
		restrictToFormat: true,
		preserveInputFormat: true,
		allowEmpty: false,

		// # color
		format: 'hex',
		case: 'upper',

		// # Sliders
		hue: true,
		saturation: true,
		lightness: true, // Note: In the square mode this is HSV and in round
							// mode this is HSL

		// # Swatch
		swatch: true,
		swatchPos: 'left',
		swatchBootstrap: false,
		//swatchOnly: true,

		// # Popup
		round: false,
		inline: true,
		horizontal: false
	};
	// Returns
	$scope.guardar = function () {
		$uibModalInstance.close($scope.tablero);
	};
	$scope.cancelar = function () {
		$uibModalInstance.dismiss('cancel');
	};
});