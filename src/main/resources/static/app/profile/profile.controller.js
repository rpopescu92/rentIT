(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('ProfileController', ProfileController);

    ProfileController.$inject = ['$scope', '$rootScope', '$state',
                                'ProfileService', 'PrincipalService', '$mdToast',
                                'Upload', 'Account', 'CitiesService'];

    function ProfileController($scope, $rootScope, $state, ProfileService,
                PrincipalService, $mdToast, Upload, Account, CitiesService) {

        $scope.username;
        $scope.updateProfile = updateProfile;
        var last = {
              bottom: false,
              top: true,
              left: false,
              right: true
            };
        $scope.photo = {};
        $scope.cities = [];
        $scope.city = {};

        $scope.$watch('file', function (file) {
             $scope.upload($scope.file);
         });

        $scope.upload = upload;

        init();

        function init() {
           Account.getAccount()
                            .then(function(data){
                                $scope.username = data.username;

                                ProfileService.getProfileDetails($scope.username)
                                               .then(function(data) {
                                                        $scope.id = data.id;
                                                        $scope.firstName = data.firstName;
                                                        $scope.lastName = data.lastName;
                                                        $scope.email = data.emailAddress;
                                                        $scope.phoneNumber = data.phoneNumber;
                                                        $scope.addressId = data.address.id;
                                                        $scope.streetName = data.address.streetName;
                                                        $scope.streetNumber = data.address.streetNumber;
                                                        $scope.apartmentNumber = data.address.apartmentNumber;
                                                        $scope.floorNumber = data.address.floorNumber;
                                                        if(data.address.city != null) {
                                                            $scope.city = data.address.city;
                                                        }

                                                        $scope.otherInfo = data.otherInfo;
                                                        $scope.userId = data.user.id;
                                                        if(data.photo != null) {
                                                            $scope.photo.content = data.photo.content;
                                                            $scope.photo.name = data.photo.name;
                                                        }

                                                     },
                                                 function(error) {
                                         });
                                 getCities();
                            },
                            function(error){
                                console.log("user not authenticated");
                 });
        }

        function upload (file) {
               Upload.upload({
                     url: '/api/upload',
                     fields: {'username': $scope.username}, // additional data to send
                     file: file
               }).progress(function (evt) {
                     var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                }).success(function (data, status, headers, config) {
                     console.log("success");
                     $scope.photo.content = file.content;
                     $scope.photo.name = file.name;
              });
        };
        function updateProfile() {
            var profileDetails = {
                id: $scope.id,
                firstName: $scope.firstName,
                lastName: $scope.lastName,
                emailAddress: $scope.email,
                phoneNumber: $scope.phoneNumber,
                address: {
                    id: $scope.addressId,
                    streetName: $scope.streetName,
                    streetNumber: $scope.streetNumber,
                    apartmentNumber: $scope.apartmentNumber,
                    floorNumber: $scope.floorNumber,
                    city: {
                        cityName: $scope.city.cityName
                    }
                },
                user: {
                    id: $scope.userId,
                    username: $scope.username
                }
            }

            ProfileService.updateProfileDetails(profileDetails)
                            .then(function(data){
                                console.log("update success");
                                $mdToast.show(
                                     $mdToast.simple()
                                      .textContent('Updated successfully!')
                                       .hideDelay(3000));
                            },
                            function(error) {
                                $mdToast.show(
                                      $mdToast.simple()
                                       .textContent('Error occurred!')
                                         .hideDelay(3000));
                   });
        }

         function getCities() {
                    CitiesService.getAllCities()
                                .then(function(data) {
                                    $scope.cities = data.data;
                                });
                }
    }
})();