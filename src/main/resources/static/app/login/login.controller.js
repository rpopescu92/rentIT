(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$rootScope', '$mdDialog', '$state', 'AuthorizationService', 'Account', 'Principal'];

    function LoginController($scope, $rootScope, $mdDialog, $state, AuthorizationService, Account, Principal) {

        $scope.registerDialog = registerDialog;
        $scope.currentUser = currentUser;

        function registerDialog() {
            $mdDialog.show({
                templateUrl: 'app/register/register.html',
                controller: 'RegisterController'
            });
        }

        function currentUser() {

        }
    }
})();