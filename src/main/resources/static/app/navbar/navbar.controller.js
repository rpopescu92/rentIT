(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$rootScope','PrincipalService'];

    function NavbarController($scope, $rootScope, PrincipalService) {
        $scope.init = init;

        init();
        $scope.message;

        function init() {
            $scope.isAuthenticated = PrincipalService.isAuthenticated();
            $scope.message = "hi";
        }
    }
})();