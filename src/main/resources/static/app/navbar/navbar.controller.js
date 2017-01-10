(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$rootScope', '$state','PrincipalService', 'AuthorizationService'];

    function NavbarController($scope, $rootScope, $state, PrincipalService, AuthorizationService) {
        $scope.init = init;
        $scope.goEditProfile = goEditProfile;
        $scope.logout = logout;

        $scope.isAuthenticated = false;

        init();

        $rootScope.$on("authenticationSuccess", function(event, data) {
            console.log("emit success");
            $scope.isAuthenticated = true;
        });

        function init() {
            console.log("login: "+ $scope.isAuthenticated);
            $scope.isAuthenticated = PrincipalService.isAuthenticated();

        }

        function goEditProfile() {
            console.log("go profile");
            $state.go('profile');
        }

        function logout() {
            AuthorizationService.logout();
            $scope.isAuthenticated = false;
            $state.go('login');
        }

        function goHome() {
            console.log("go home");
            $state.go('home');
        }
    }
})();