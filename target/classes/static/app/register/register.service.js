(function() {
    'use strict';

    angular.module('rentITApp')
            .service('RegisterService', RegisterService);

    RegisterService.$inject = ['$http'];

    function RegisterService($http) {
        return{
            register: register
        }

        function register(data) {
            return $http({
                url: '/register',
                method: 'POST',
                data: data
            });
        }
    }
})();