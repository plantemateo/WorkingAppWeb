angular.module('frontend').controller('ModalCardCtrl', function ($scope, $rootScope, $uibModalInstance, $uibModal, tarjetaService, comentarioService, userService, tarjeta, miembrosTablero) {
	let editComentarioList = new WeakMap();
	
	$scope.tarjeta = tarjeta;
	$scope.miembrosTablero = miembrosTablero;
	$scope.makeNewComment = false;
	$scope.newComment = '';
	$scope.action = 'nothing';
	
	comentarioService.listar(tarjeta.id).then(
		function (resp) {
			$scope.comentarios = resp.data;
			$scope.comentarios.forEach(comment => {
				editComentarioList.set(comment, false);
			});
		},
		function (err) {
			$rootScope.openErrorModal(err);
		}
	);
	// # Tarjeta
	$scope.deleteCard = function () {
		let modalInstance = $uibModal.open({
			templateUrl: 'views/confirmacion.form.html',
			controller: 'ModalConfirmarCtrl',
			resolve: {
				message: function () {
					return 'borrar la tarjeta';
				},
				titulo: function () {
					return 'Borrado de tarjeta';
				}
			}
		});
		modalInstance.result.then(
			function (eleccion) {
				if (eleccion) {
					tarjetaService.eliminar($scope.tarjeta.id).then(
						function () {
							$uibModalInstance.close('delete');
						}, function (err) {
							$rootScope.openErrorModal(err);
							$uibModalInstance.dismiss('cancel');
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

	// Titulo
	$scope.editCardTitle = function () {
		$scope.editingCardTitle = false;
		tarjetaService.actualizar($scope.tarjeta).then(
			function () {
			}, function (err) {
				$rootScope.openErrorModal(err);
			}
		);
	}

	// Descripcion
	$scope.editBody = function () {
		$scope.tarjeta.cuerpo = $scope.tempCuerpo;
		tarjetaService.actualizar($scope.tarjeta).then(
			function () {
				$scope.editingCardBody = false;
			},
			function (err) {
				$rootScope.openErrorModal(err);
			}
		);
	};
	$scope.cancelEditBody = function () {
		$scope.tempCuerpo = $scope.tarjeta.cuerpo;
		$scope.editingCardBody = false;
	};


	// # Comentarios
	$scope.addComment = function () {
		let comentario = {
			creadoPor: tarjeta.creadoPor,
			cuerpo: $scope.newComment,
			tarjeta: $scope.tarjeta
		}
		comentarioService.agregar(comentario).then(
			function (resp) {
				$scope.comentarios.splice(0, 0, resp.data);
				$scope.makeNewComment = false;
				$scope.newComment = '';
			},
			function (err) {
				$rootScope.openErrorModal(err);
			}
		);
	};
	$scope.deleteComment = function (comment) {
		let modalInstance = $uibModal.open({
			templateUrl: 'views/confirmacion.form.html',
			controller: 'ModalConfirmarCtrl',
			resolve: {
				message: function () {
					return 'borrar el comentario';
				},
				titulo: function () {
					return 'Borrado de comentario';
				}
			}
		});
		modalInstance.result.then(
			function (eleccion) {
				if (eleccion) {
					comentarioService.eliminar(comment.id).then(
						function () {
							$scope.comentarios = $scope.comentarios.filter(com => com.id != comment.id);
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
	$scope.editComment = function (comment) {
		editComentarioList.set(comment, true);
	};
	$scope.editingComment = function (comment) {
		return editComentarioList.get(comment);
	};
	$scope.updateComment = function (comment) {
		comment.cuerpo = comment.tempCuerpo;
		comentarioService.actualizar(comment).then(
			function () {
				editComentarioList.set(comment, false);
			},
			function (err) {
				$rootScope.openErrorModal(err);
			}
		);
	};
	$scope.cancelEditComent = function (comment) {
		comment.tempCuerpo = comment.cuerpo;
		editComentarioList.set(comment, false);
	};
	
	
	// # Popover
	$scope.membersPopoverTemplateUrl = 'views/miembro.popover.html';
	$scope.newMemberPopoverTemplateUrl = 'views/nuevo.miembro.popover.html';
	$scope.addMember = function (member) {
		$scope.tarjeta.miembrosAsignados.push(member);
		tarjetaService.actualizar($scope.tarjeta).then(
			function () {
			},
			function (err) {
				$rootScope.openErrorModal(err);
			}
		);
	};
	$scope.removeAssignedMember = function (member) {
		$scope.isPopoverOpen = false;
		let tempCard = $scope.tarjeta;
		tempCard.miembrosAsignados = tempCard.miembrosAsignados.filter(mem => mem.idUser != member.idUser);
		tarjetaService.actualizar(tempCard).then(
			function (resp) {
				$scope.tarjeta = resp.data;
			},
			function (err) {
				$rootScope.openErrorModal(err);
			}
		);
	};
	$scope.assigned = function (member) {
		let r = false;
		$scope.tarjeta.miembrosAsignados.forEach(mem => {
			r = r || mem.idUser == member.idUser;
		});
		return r;
	};

	// # Otros
	$scope.timeSince = function (date) {
		return moment(date).fromNow();
	}
	$scope.elegir = function () {
		$uibModalInstance.close($scope.accion);
	};
	$scope.cancelar = function () {
		$uibModalInstance.dismiss('cancel');
	};
});