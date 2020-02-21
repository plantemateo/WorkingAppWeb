angular.module('frontend').controller('ModalBoardMembersCtrl', function ($scope, $rootScope, $uibModalInstance, miembros, creador, userService) {
    $scope.miembros = miembros;
    $scope.creador = creador;
    $scope.username = "";
    $scope.found = true;

    $scope.search = function () {
        if ($scope.username != "") {
            let contains = false;
            $scope.miembros.forEach(mem => {
                contains = contains || mem.username == $scope.username;
            });
            if (!contains) {
                userService.cargarPorUsernameOrEmail($scope.username).then(
                    function (resp) {
                    	user = $scope.username;
                    	for (var i = 0; i < resp.data.length; i++){
                    		if (resp.data[i].username == user){
                    			$scope.miembros.push(resp.data[i]);
                                $scope.found = true;
                    		}
                    	}
                    	
                    },
                    function (err) {
                    	$rootScope.openErrorModal(err);
                    }
                );
            }
            $scope.found = false;
        }
    };
    $scope.eliminateMember = function (member) {
        $scope.miembros = $scope.miembros.filter(mem => mem.idUser != member.idUser);
    };

    $scope.done = function () {
        $uibModalInstance.close($scope.miembros);
    };
});