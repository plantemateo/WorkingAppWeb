angular.module('frontend').service('tarjetaService', function ($http, URL_API_BASE) {
    var servicio = {
        listar: function (listaId) {
            return $http.get(URL_API_BASE + 'tableros/listas/' + listaId + '/tarjetas');
        },
        cargar: function (tarjetaId) {
            return $http.get(URL_API_BASE + 'tableros/listas/tarjetas/' + tarjetaId);
        },
        agregar: function (tarjeta) {
            return $http.post(URL_API_BASE + 'tableros/listas/tarjetas', tarjeta);
        },
        actualizar: function (tarjeta) {
            return $http.put(URL_API_BASE + 'tableros/listas/tarjetas', tarjeta);
        },
        eliminar: function (tarjetaId) {
            return $http.delete(URL_API_BASE + 'tableros/listas/tarjetas/' + tarjetaId);
        }
    };
    return servicio;
});