(function () {
    'use strict';

    angular.module('rentITApp')
        .service('PropertiesService', PropertiesService);

    PropertiesService.$inject = ['$http'];

    function PropertiesService($http) {
        return {
            getProperties: getProperties
        };

        function getProperties(options, searchOptions) {
            return $http({
                url: '/api/properties/advanced?page=' + options.page + '&limit=' + options.limit + '&order=' + options.order,
                data: searchOptions,
                method: 'POST'
            });
        }
    }
})();