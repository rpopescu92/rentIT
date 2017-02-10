(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('ProfileController', ProfileController);

    ProfileController.$inject = ['$scope', '$rootScope', '$state',
                                'ProfileService', 'PrincipalService', '$mdToast',
                                'Upload', 'Account'];

    function ProfileController($scope, $rootScope, $state, ProfileService,
                PrincipalService, $mdToast, Upload, Account) {

        $scope.username;
        $scope.updateProfile = updateProfile;
        var last = {
              bottom: false,
              top: true,
              left: false,
              right: true
            };


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
                                                        $scope.city = data.address.city.cityName;
                                                        $scope.otherDirections = data.address.otherDirections;
                                                        $scope.userId = data.user.id;
                                                     },
                                                 function(error) {
                                         });
                            },
                            function(error){
                                console.log("user not authenticated");
                 });
        }

        function upload (file) {
                        Upload.upload({
                                url: '/upload',
                                fields: {'username': $scope.username}, // additional data to send
                                file: file
                            }).progress(function (evt) {
                                var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                                console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                            }).success(function (data, status, headers, config) {
                                console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
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
                        cityName: $scope.cityName
                    },
                    otherDirections: $scope.otherDirections
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
    }
})();