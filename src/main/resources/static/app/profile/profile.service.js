(function() {
    'use strict';

    angular.module('rentITApp')
            .service('ProfileService', ProfileService);

    ProfileService.$inject = ['$http'];

    function ProfileService($http) {

    }
})();