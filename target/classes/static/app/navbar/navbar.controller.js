(function () {
    'use strict';

    angular.module('rentITApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$rootScope', '$state', 'PrincipalService', 'AuthorizationService', 'NavbarService', '$locale'];

    function NavbarController($scope, $rootScope, $state, PrincipalService, AuthorizationService, NavbarService, $locale) {
        $scope.init = init;
        $scope.goEditProfile = goEditProfile;
        $scope.logout = logout;
        $scope.username;
        $scope.goto = goto;
        $scope.localeId = $locale.id;
        $scope.isAuthenticated = false;

        init();

        $rootScope.$on("authenticationSuccess", function (event, data) {
            $scope.isAuthenticated = true;

            getAuthenticatedUser();
        });

        function goto(page) {
            $state.go(page);
        }

        function init() {
            $scope.isAuthenticated = PrincipalService.isAuthenticated();
            getAuthenticatedUser();
        }

        function goEditProfile() {
            $state.go('profile');
        }

        function logout() {
            AuthorizationService.logout();
            $scope.isAuthenticated = false;
            $state.go('login');
        }

        function getAuthenticatedUser() {
            NavbarService.getAuthenticatedUser()
                .then(function (data) {
                    $scope.username = data.username;
                });
        }
    }
})();