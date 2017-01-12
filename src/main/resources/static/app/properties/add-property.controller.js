(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('AddPropertyController', AddPropertyController);

    AddPropertyController.$inject = ['$state', '$scope', 'AddPropertyService', 'Account'];

    function AddPropertyController($state, $scope, AddPropertyService, Account) {

        $scope.addProperty = addProperty;
        $scope.property = {};
        $scope.property.images = [];
        init();

        function init() {
            Account.getAccount()
                   .then(function(data) {
                      $scope.username = data.username;
                  }, function(error) {

                  });

        }

        function addProperty() {
            AddPropertyService.addProperty($scope.property)
                        .then(function(data){
                            console.log('go to my properties');
                            $state.go('my-properties');
                        },
                        function(error){

                        });
        }


    }
})();