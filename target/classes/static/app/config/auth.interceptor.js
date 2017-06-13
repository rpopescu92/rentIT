(function () {
    'use strict';

    angular
        .module('rentITApp')
        .factory('authInterceptor', authInterceptor);

    authInterceptor.$inject = ['$rootScope', '$q', '$location','$sessionStorage'];

    function authInterceptor($rootScope, $q, $location, $sessionStorage) {
        var service = {
            request: request
        };

        return service;

        function request(config) {
            config.headers = config.headers || {};
            var token = $sessionStorage.authenticationToken;

            if (token) {
                config.headers.Authorization = 'Bearer ' + token;
            }

            return config;
        }
    }

})();