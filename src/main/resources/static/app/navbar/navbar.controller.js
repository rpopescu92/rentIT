(function() {
    'use strict';

    angular.module('rentITAp')
            .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$rootScope','PrincipalService'];

    function NavbarController($scope, $rootScope, PrincipalService) {
        $scope.init = init;

        init();


        function init() {
            $scope.isAuthenticated = PrincipalService.isAuthenticated();
        }
    }
})();