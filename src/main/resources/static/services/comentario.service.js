angular.module('frontend').service('comentarioService', function ($http, URL_API_BASE) {
    var servicio = {
        listar: function (tarjetaId) {
            return $http.get(URL_API_BASE + 'tableros/listas/tarjetas/' + tarjetaId + '/comentarios');
        },
        cargar: function (comentarioId) {
            return $http.get(URL_API_BASE + 'tableros/listas/tarjetas/comentarios/' + comentarioId);
        },
        agregar: function (comentario) {
            return $http.post(URL_API_BASE + 'tableros/listas/tarjetas/comentarios', comentario);
        },
        actualizar: function (comentario) {
            return $http.put(URL_API_BASE + 'tableros/listas/tarjetas/comentarios', comentario);
        },
        eliminar: function (comentarioId) {
            return $http.delete(URL_API_BASE + 'tableros/listas/tarjetas/comentarios/' + comentarioId);
        }
    };
    return servicio;
});