(function() {
    'use strict';

    angular.module('rentITApp')
            .service('AddPropertyService', AddPropertyService);

    AddPropertyService.$inject = ['$http'];

    function AddPropertyService($http) {
        return {
            addProperty: addProperty
        }

        function addProperty(property){
            return $http({
                url: '/api/properties',
                method: 'POST',
                data: property
            });
        }
    }
})();