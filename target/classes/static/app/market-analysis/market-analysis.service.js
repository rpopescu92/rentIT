(function () {
    'use strict';

    angular.module('rentITApp')
        .service('MarketAnalysisService', MarketAnalysisService);

    MarketAnalysisService.$inject = ['$http'];

    function MarketAnalysisService($http) {
        return {
            get: get,
            getForCities: getForCities,
            getLast3Months: getLast3Months
        };

        function getLast3Months() {
            return $http({
                url: '/api/analysis/bucharest/last-months',
                method: 'GET'
            });
        }

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