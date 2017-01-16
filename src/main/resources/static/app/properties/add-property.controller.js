(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('AddPropertyController', AddPropertyController);

    AddPropertyController.$inject = ['$state', '$scope', 'AddPropertyService', 'Account', 'CitiesService'];

    function AddPropertyController($state, $scope, AddPropertyService, Account, CitiesService) {

        $scope.addProperty = addProperty;
        $scope.property = {};
        $scope.property.images = [];
        $scope.cities = [];
        $scope.regions = [];
        $scope.resultCities = [];
        $scope.changedValue = changedValue;
        init();

        function init() {
            Account.getAccount()
                   .then(function(data) {
                      $scope.username = data.username;
                  }, function(error) {

                  });
             getAllCities();
             getRegions();

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

        function getRegions() {
            CitiesService.getRegions()
                        .then(function(data) {
                            $scope.regions = data.data;
                        });
        }

        function getAllCities() {
            CitiesService.getAllCities()
                        .then(function(data) {
                            $scope.cities = data.data;
                        });
        }

        function changedValue(region) {
           for(var j=0; j< cities.length; j++) {
                if(cities[j].region === region) {
                    resultCities.push(cities[j]);
                }
           }
        }
    }
})();