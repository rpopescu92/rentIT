(function() {
    'use strict';

    angular.module('rentITApp')
        .service('AlterPropertyService', AlterPropertyService);


    AlterPropertyService.$inject = ['$http'];

    function AlterPropertyService($http) {
        return {
            rentProperty: rentProperty
        }

        function rentProperty(id, isRented) {
            $http({
                url: '/api/properties/' + id,
                method: 'PATCH',
                data: isRented
            });
        }
    }
})();