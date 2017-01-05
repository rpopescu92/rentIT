(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$rootScope', '$state','PrincipalService'];

    function NavbarController($scope, $rootScope, $state, PrincipalService) {
        $scope.init = init;
        $scope.goEditProfile = goEditProfile;

        $scope.isAuthenticated = false;

        init();

        $rootScope.$on("authenticationSuccess", function(event, data) {
            $scope.isAuthenticated = true;
        });

        function init() {
            $scope.isAuthenticated = PrincipalService.isAuthenticated();

        }

        function goEditProfile() {
            $state.go('/profile');
        }
    }
})();