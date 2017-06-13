(function () {
    'use strict';

    angular.module('rentITApp')
        .controller('RegisterController', RegisterController);

    RegisterController.$inject = ['$scope', 'RegisterService', '$mdDialog'];

    function RegisterController($scope, RegisterService, $mdDialog) {
        $scope.cancel = cancel;
        $scope.register = register;
        $scope.username = null;
        $scope.errorMessage = null;


        function cancel() {
            $mdDialog.hide();
        }

        function register() {
            var data = {
                firstName: $scope.firstName,
                lastName: $scope.lastName,
                username: $scope.username,
                password: $scope.password,
                isOwner: $scope.isOwner,
                isTenant: $scope.isTenant,
                emailAddress: $scope.emailAddress,
                number: $scope.number
            };
            RegisterService.register(data)
                .then(function (data) {
                        $scope.errorMessage = "";
                        $mdDialog.hide();
                    },
                    function (error) {
                        $scope.errorMessage = error.data;
                    });
        }
    }

})();