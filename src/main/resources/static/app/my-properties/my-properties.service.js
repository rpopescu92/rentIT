(function() {
    'use strict';

    angular.module('rentITApp')
            .service('MyPropertiesService', MyPropertiesService);

    MyPropertiesService.$inject = ['$http'];

    function MyPropertiesService($http) {
        return {
            getUserProperties: getUserProperties
        }

        function getUserProperties(username, options) {
            return $http({
                url: '/api/properties/' + username +'?page='+ options.page + '&limit='+ options.limit
                            + '?order=' + options.order,
                method: 'GET'
            });
        }
    }
})();