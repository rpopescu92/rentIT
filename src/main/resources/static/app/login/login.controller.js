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
        $scope.isOwner = false;

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
                        .then(function(data, status){
                            PrincipalService.theUsername($scope.username);
                            $scope.$emit("authenticationSuccess");
                            if(AuthorizationService.getPreviousState()) {
                                var previousState = AuthorizationService.getPreviousState();
                                AuthorizationService.resetPreviousState();
                                console.log("bla");
                                $state.go(previousState.name, previousState.params);
                                getAccount();
                            }
                            if(status === 401) {
                                $scope.authenticationError = true;
                            }
                         },
                        function (response) {
                            $scope.authenticationError = true;
                            console.log("login error");
                        });
        }

        function getAccount() {
            Account.getAccount()
                .then(function(data){
                    $scope.username = data.username;
                    $scope.isOwner = data.isOwner;
                    if($scope.isOwner) {
                        console.log("is owner");
                        $state.go('my-properties');
                    } else {
                        $state.go('home');
                    }
                });
        }
    }
})();