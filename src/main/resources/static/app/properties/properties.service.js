(function(){
    'use strict';

    angular.module('rentITApp')
            .service('PropertiesService', PropertiesService);

    PropertiesService.$inject = ['$http'];

    function PropertiesService($http) {
        return {
            getProperties: getProperties
        }

        function getProperties(options) {
            return $http({
                url: '/api/properties?page='+options.page+'&limit='+options.limit+'&order='+options.order,
                method: 'GET'
            });
        }
    }
})();