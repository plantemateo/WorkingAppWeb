angular.module('frontend').service('userService', function ($http, URL_API_BASE) {
	var servicio = {
		// Tableros
		listar: function () {
			return $http.get(URL_API_BASE + 'usuarios');
		},
		cargarPorUsernameOrEmail: function (username) {
			return $http.get(URL_API_BASE + 'usuarios?us=' + username);
		},
		agregar: function (usuario) {
			return $http.post(URL_API_BASE + 'usuarios', usuario);
		},
		actualizar: function (usuario) {
			return $http.put(URL_API_BASE + 'usuarios', usuario);
		},
		eliminar: function (usuario) {
			return $http.delete(URL_API_BASE + 'usuarios/' + usuario.id);
		}
	};
	return servicio;
});