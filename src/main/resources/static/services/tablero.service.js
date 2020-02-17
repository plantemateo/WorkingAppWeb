angular.module('frontend').service('tableroService', function ($http, URL_API_BASE) {
    var servicio = {
        listar: function () {
            return $http.get(URL_API_BASE + 'tableros');
        },
        cargar: function (tableroId) {
            return $http.get(URL_API_BASE + 'tableros/' + tableroId);
        },
        agregar: function (tablero) {
            return $http.post(URL_API_BASE + 'tableros', tablero);
        },
        actualizar: function (tablero) {
            return $http.put(URL_API_BASE + 'tableros', tablero);
        },
        eliminar: function (tablero) {
            return $http.delete(URL_API_BASE + 'tableros/' + tablero.id);
        }
    };
    return servicio;
});