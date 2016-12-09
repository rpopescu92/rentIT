(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$rootScope','PrincipalService'];

    function NavbarController($scope, $rootScope, PrincipalService) {
        $scope.init = init;

        $scope.isAuthenticated = false;

        init();

        $rootScope.$on("authenticationSuccess", function(event, data) {
            $scope.isAuthenticated = true;
        });

        function init() {
            $scope.isAuthenticated = PrincipalService.isAuthenticated();

        }
    }
})();