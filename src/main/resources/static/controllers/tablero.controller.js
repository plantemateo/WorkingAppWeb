angular.module('frontend').controller('TableroController', function ($scope, $rootScope, $uibModal, $routeParams, $location, $timeout, tableroService, listaService, tarjetaService) {
	let editMapList = new WeakMap();
	$rootScope.inBoard = true;

	tableroService.cargar($routeParams.tableroId).then(
		function (resp) {
			$scope.tablero = resp.data;
			$rootScope.colorFondo = $scope.tablero.fondo;
			listaService.listar($scope.tablero.id).then(
				function (resp) {
					$scope.listas = resp.data;
					$scope.listas.forEach(currList => {
						tarjetaService.listar(currList.id).then(
							function (resp) {
								currList.tarjetas = resp.data;
								editMapList.set(currList, false);
							}, function (err) {
								$rootScope.openErrorModal(err);
							}
						);
					});
				},
				function (err) {
					$rootScope.openErrorModal(err);
				}
			);
		}, function (err) {
			$rootScope.openErrorModal(err);
		}
	);
	// # Sidebar
	$scope.toggleSidebar = function () {
		$rootScope.sidebarVisible = $rootScope.sidebarVisible == 'active' ? '' : 'active';
	};
	$scope.openMembers = function () {
		let modalInstance = $uibModal.open({
			templateUrl: 'views/tablero.miembros.modal.html',
			controller: 'ModalBoardMembersCtrl',
			backdrop: 'static',
			resolve: {
				miembros: function () {
					return $scope.tablero.miembros;
				},
				creador: function () {
					return $scope.tablero.creador;
				}
			}
		});
		modalInstance.result.then(
			function (miembros) {
				$scope.tablero.miembros = miembros;
				tableroService.actualizar($scope.tablero).then(
					function () {
					},
					function (err) {
						$rootScope.openErrorModal(err);
					}
				);
			}
		);
	};

	// # Listas
	$scope.addList = function () {
		list = {
			titulo: 'Titulo',
			posicion: $scope.listas.length,
			tablero: $scope.tablero,
		}
		listaService.agregar(list).then(
			function (resp) {
				let newList = resp.data;
				newList.tarjetas = [];
				$scope.listas.push(newList);
			},
			function (err) {
				$rootScope.openErrorModal(err);
			}
		);
	};
	$scope.deleteList = function (list) {
		if (list.tarjetas.length > 0) {
			let modalInstance = $uibModal.open({
				templateUrl: 'views/confirmacion.form.html',
				controller: 'ModalConfirmarCtrl',
				resolve: {
					message: function () {
						return 'borrar la lista "' + list.titulo + '"';
					},
					titulo: function () {
						return 'Borrado de lista "' + list.titulo + '"';
					}
				}
			});
			modalInstance.result.then(
				function (eleccion) {
					if (eleccion) {
						listaService.eliminar(list.id).then(
							function () {
								$scope.listas = $scope.listas.filter(currLis => currLis.id != list.id);
							}, function (err) {
								$rootScope.openErrorModal(err);
							}
						);
					}
				},
				function (err) {
					if (err.hasOwnProperty('data')) {
						$rootScope.openErrorModal(err);
					}
				}
			);
		} else {
			listaService.eliminar(list.id).then(
				function () {
					$scope.listas = $scope.listas.filter(currLis => currLis.id != list.id);
				}, function (err) {
					$rootScope.openErrorModal(err);
				}
			);
		}
	};
	$scope.editList = function (list) {
		editMapList.set(list, true);
	};
	$scope.doneEditing = function (list) {
		editMapList.set(list, false);
		listaService.actualizar(list).then(
			function () {
			}, function (err) {
				$rootScope.openErrorModal(err);
			}
		);
	};
	$scope.editingList = function (list) {
		return editMapList.get(list);
	};
	$scope.onMovedList = function (index) {
		$scope.listas.splice(index, 1);
		for (let i = 0; i < $scope.listas.length; i++) {
			if ($scope.listas[i].posicion != i) {
				$scope.listas[i].posicion = i;
				listaService.actualizar($scope.listas[i]).then(
					function () {
					},
					function (err) {
						$rootScope.openErrorModal(err);
					}
				);
			}
		}
	};

	// # Tarjetas
	$scope.addCard = function (list) {
		card = {
			titulo: 'Nueva tarjeta',
			cuerpo: '',
			posicion: list.tarjetas.length,
			lista: list,
			creadoPor: $scope.tablero.creador,
			miembrosAsignados: []
		}
		tarjetaService.agregar(card).then(
			function (resp) {
				list.tarjetas.push(resp.data);
			},
			function (err) {
				$rootScope.openErrorModal(err);
			}
		);
	};
	$scope.openCard = function (list, card) {
		let modalInstance = $uibModal.open({
			templateUrl: 'views/tarjeta.modal.html',
			controller: 'ModalCardCtrl',
			resolve: {
				tarjeta: function () {
					return card;
				},
				miembrosTablero: function () {
					return $scope.tablero.miembros;
				}
			}
		});
		modalInstance.result.then(
			function (eleccion) {
				if (eleccion == 'delete') {
					list.tarjetas = list.tarjetas.filter(tarj => tarj.id != card.id);
				} else if (eleccion == 'update') {
					tarjetaService.cargar(card.id).then(
						function (resp) {
							card = resp.data;
						},
						function (err) {
							$rootScope.openErrorModal(err);
						}
					);
				}
			},
			function (err) {
				if (err.hasOwnProperty('data')) {
					$rootScope.openErrorModal(err);
				}
			}
		);
	};
	$scope.onInsertedCard = function (pos, list, item) {
		if (item.lista.id != list.id) {
			$scope.listas.forEach(currList => {
				if (currList.id == item.lista.id) {
					currList.tarjetas = currList.tarjetas.filter(currTar => currTar.id != item.id);
					for (let i = 0; i < currList.tarjetas.length; i++) {
						if (currList.tarjetas[i].posicion != i) {
							currList.tarjetas[i].posicion = i;
							tarjetaService.actualizar(currList.tarjetas[i]).then(
								function () {
								},
								function (err) {
									$rootScope.openErrorModal(err);
								}
							);
						}
					}
				}
			});
			item.lista = angular.copy(list);
			delete item.lista.tarjetas;
			for (let i = 0; i < list.tarjetas.length; i++) {
				if (list.tarjetas[i].posicion != i || list.tarjetas[i].id == item.id) {
					list.tarjetas[i].posicion = i;
					tarjetaService.actualizar(list.tarjetas[i]).then(
						function (resp) {
						},
						function (err) {
							$rootScope.openErrorModal(err);
						}
					);
				}
			}
		} else if (pos != item.posicion) {
			let oldPos = item.posicion;
			item.posicion = pos;
			list.tarjetas = list.tarjetas.filter(currTar => (currTar.id != item.id || currTar.posicion == item.posicion));
			item.posicion = oldPos;
			for (let i = 0; i < list.tarjetas.length; i++) {
				if (list.tarjetas[i].posicion != i) {
					list.tarjetas[i].posicion = i;
					tarjetaService.actualizar(list.tarjetas[i]).then(
						function () {
						},
						function (err) {
							$rootScope.openErrorModal(err);
						}
					);
				}
			}
		}
	};

	// # Tablero
	$scope.changeBoardConfig = function () {
		let modalInstance = $uibModal.open({
			templateUrl: 'views/agregar.tablero.modal.html',
			controller: 'ModalBoardCtrl',
			resolve: {
				tablero: function () {
					return $scope.tablero;
				}
			}
		});
		modalInstance.result.then(
			function (board) {
				tableroService.actualizar(board).then(
					function (resp) {
						$scope.tablero = resp.data;
						$rootScope.colorFondo = $scope.tablero.fondo;
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
	};
	$scope.deleteBoard = function () {
		let modalInstance = $uibModal.open({
			templateUrl: 'views/confirmacion.form.html',
			controller: 'ModalConfirmarCtrl',
			resolve: {
				message: function () {
					return 'borrar el tablero "' + $scope.tablero.titulo + '"';
				},
				titulo: function () {
					return 'Eliminar el tablero "' + $scope.tablero.titulo + '"';
				}
			}
		});
		modalInstance.result.then(
			function (eleccion) {
				if (eleccion) {
					tableroService.eliminar($scope.tablero).then(
						function () {
							$location.path('/main');
						}, function (err) {
							$rootScope.openErrorModal(err);
						}
					);
				}
			},
			function (err) {
				if (err.hasOwnProperty('data')) {
					$rootScope.openErrorModal(err);
				}
			}
		);
	};
});