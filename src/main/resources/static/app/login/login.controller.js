(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('LoginController', LoginController);

    LoginController.$inject = ['$scope', '$rootScope', '$mdDialog', '$state', 'AuthorizationService', 'Account', 'PrincipalService'];

    function LoginController($scope, $rootScope, $mdDialog, $state, AuthorizationService, Account, PrincipalService) {

        $scope.registerDialog = registerDialog;
        $scope.currentUser;
        $scope.login = login;
        $scope.authenticationError = false;

        function registerDialog() {
            $scope.username = "";
            $scope.password = "";
            $mdDialog.show({
                templateUrl: 'app/register/register.html',
                controller: 'RegisterController'
            });
        }

        function currentUser() {

        }

        function login() {
            var data = {
                username: $scope.username,
                password: $scope.password
            }
            AuthorizationService.login(data)
                        .then(function(data){
                            $state.go('home');
                            PrincipalService.theUsername($scope.username);
                            $scope.$emit("authenticationSuccess");
                            if(AuthorizationService.getPreviousState()) {
                                var previousState = AuthorizationService.getPreviousState();
                                AuthorizationService.resetPreviousState();
                                $state.go(previousState.name, previousState.params);
                            }
                         }).catch(function(){
                            $scope.authenticationError = true;
                  });
        }
    }
})();