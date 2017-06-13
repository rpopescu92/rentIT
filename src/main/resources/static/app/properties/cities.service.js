(function () {
    'use strict';

    angular.module('rentITApp')
        .service('CitiesService', CitiesService);

    CitiesService.$inject = ['$http'];

    function CitiesService($http) {
        return {
            getAllCities: getAllCities,
            getRegions: getRegions,
            getCity: getCity
        };

        function getCity(name) {
            return $http({
                url: '/api/cities/search?name=' + name,
                method: 'GET'
            });
        }

        function getAllCities() {
            return $http({
                url: '/api/cities',
                method: 'GET'
            });
        }

        function getRegions() {
            return $http({
                url: '/api/cities/regions',
                method: 'GET'
            });
        }
    }
})();