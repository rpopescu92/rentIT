(function () {
    'use strict';

    angular.module('rentITApp')
        .controller('AddPropertyController', AddPropertyController);

    AddPropertyController.$inject = ['$state', '$scope', 'AddPropertyService', 'Account', 'CitiesService', 'Upload'];

    function AddPropertyController($state, $scope, AddPropertyService, Account, CitiesService, Upload) {

        $scope.addProperty = addProperty;
        $scope.property = {};
        $scope.property.address = {};
        $scope.property.address.city = {};
        $scope.property.images = [];
        $scope.cities = [];
        $scope.regions = [];
        $scope.resultCities = [];
        $scope.changedValue = changedValue;
        $scope.files = [];
        $scope.photo = {};
        $scope.processFiles = processFiles;
        $scope.images = [];

        init();

        function init() {
            Account.getAccount()
                .then(function (data) {
                    $scope.username = data.username;
                }, function (error) {

                });

            getRegions();
            getAllCities();
        }

        function processFiles(files) {
            var fileReader = new FileReader();
            $scope.files.push(files[0].file);
            console.log($scope.files);
            fileReader.readAsBinaryString(files[0].file);
            fileReader.onload = function (e) {
                var bytes = btoa(e.target.result.toString());
                $scope.images.push({
                    content: bytes,
                    name: files[0].file.name,
                    type: 'jpg'
                });
            };
        }

        function addProperty() {
            if ($scope.files.length > 0) {
                for (var i = 0; i < $scope.files.length; i++) {
                    $scope.property.images = $scope.images;
                    console.log($scope.property.images);
                    AddPropertyService.addProperty($scope.property)
                        .then(function (data) {
                                console.log('go to my properties');
                                $state.go('my-properties');
                            },
                            function (error) {

                            });
                }
            }
        }

        function getRegions() {
            CitiesService.getRegions()
                .then(function (data) {
                    $scope.resultCities = [];
                    $scope.regions = data.data;
                });
        }

        function getAllCities() {
            CitiesService.getAllCities()
                .then(function (data) {
                    $scope.cities = data.data;
                });
        }

        function changedValue(region) {
            $scope.resultCities = [];
            for (var j = 0; j < $scope.cities.length; j++) {
                if ($scope.cities[j].region === region) {
                    $scope.resultCities.push($scope.cities[j].cityName);
                }
            }
        }
    }
})();