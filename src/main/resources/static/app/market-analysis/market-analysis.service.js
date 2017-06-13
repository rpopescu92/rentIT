(function () {
    'use strict';

    angular.module('rentITApp')
        .service('MarketAnalysisService', MarketAnalysisService);

    MarketAnalysisService.$inject = ['$http'];

    function MarketAnalysisService($http) {
        return {
            get: get,
            getForCities: getForCities
        };

        function getForCities() {
            return $http({
                url: '/api/analysis/cities',
                method: 'GET'
            });
        }

        function get() {
            return $http({
                url: '/api/analysis/bucharest',
                method: 'GET'
            });
        }
    }
})();