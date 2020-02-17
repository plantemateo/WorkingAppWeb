angular.module('frontend')
  .factory('APIInterceptor', function ($q, $rootScope, $localStorage) {

    return {

      request: function (config) {
        if ($localStorage.logged && $localStorage.userdata) {
          userdata = $localStorage.userdata;
          config.headers['X-AUTH-TOKEN'] = userdata.authtoken;
        } else {
          $rootScope.openLoginForm();
        }
        return config || $q.when(config);
      },

      responseError: function (response) {
        if (response.status == 401) {
          $rootScope.openLoginForm();
        }
        return $q.reject(response);
      }
    };
  })