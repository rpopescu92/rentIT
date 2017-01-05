(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('ProfileController', ProfileController);

    ProfileController.$inject = ['$scope', '$rootScope', '$state', 'ProfileService'];

    function ProfileController($scope, $rootScope, $state, ProfileService) {

    }
})();