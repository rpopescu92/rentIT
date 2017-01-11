(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$rootScope', '$state','PrincipalService', 'AuthorizationService', 'NavbarService'];

    function NavbarController($scope, $rootScope, $state, PrincipalService, AuthorizationService, NavbarService) {
        $scope.init = init;
        $scope.goEditProfile = goEditProfile;
        $scope.logout = logout;
        $scope.goToProperties = goToProperties;
        $scope.myProperties = myProperties;
        $scope.goHome = goHome;
        $scope.username;

        $scope.isAuthenticated = false;

        init();

        $rootScope.$on("authenticationSuccess", function(event, data) {
            console.log("emit success");
            $scope.isAuthenticated = true;
        });

        function init() {
            console.log("login: "+ $scope.isAuthenticated);
            $scope.isAuthenticated = PrincipalService.isAuthenticated();

            NavbarService.getAuthenticatedUser()
                            .then(function(data){
                                $scope.username = data.username;
                            });
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

        function goToProperties() {
            console.log("go properties");
            $state.go('properties');
        }

        function myProperties() {

        }
    }
})();