angular.module('frontend').service('listaService', function ($http, URL_API_BASE) {
    var servicio = {
        listar: function (tableroId) {
            return $http.get(URL_API_BASE + 'tableros/' + tableroId + '/listas');
        },
        cargar: function (listaId) {
            return $http.get(URL_API_BASE + 'tableros/listas/' + listaId);
        },
        agregar: function (lista) {
            return $http.post(URL_API_BASE + 'tableros/listas', lista);
        },
        actualizar: function (lista) {
            return $http.put(URL_API_BASE + 'tableros/listas', lista);
        },
        eliminar: function (listaId) {
            return $http.delete(URL_API_BASE + 'tableros/listas/' + listaId);
        }
    };
    return servicio;
});