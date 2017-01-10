(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('ProfileController', ProfileController);

    ProfileController.$inject = ['$scope', '$rootScope', '$state',
                                'ProfileService', 'PrincipalService', '$mdToast'];

    function ProfileController($scope, $rootScope, $state, ProfileService, PrincipalService, $mdToast) {
        console.log("profile");
        $scope.username;
        $scope.updateProfile = updateProfile;
         var last = {
              bottom: false,
              top: true,
              left: false,
              right: true
            };

        init();

        function init() {
            $scope.username = PrincipalService.getUsername();

            ProfileService.getProfileDetails(PrincipalService.getUsername())
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
                                $scope.city = data.address.city;
                                $scope.otherDirections = data.address.otherDirections;
                                $scope.userId = data.user.id;
                                $scope.userName = PrincipalService.getUsername();
                            },
                            function(error) {

                            });

        }

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
                    city: $scope.city,
                    otherDirections: $scope.otherDirections
                },
                user: {
                    id: $scope.userId,
                    username: $scope.userName
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