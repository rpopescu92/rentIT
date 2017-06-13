(function() {
    'use strict';

    angular.module('rentITApp')
            .service('ProfileService', ProfileService);

    ProfileService.$inject = ['$http'];

    function ProfileService($http) {

        return{
            getProfileDetails: getProfileDetails,
            updateProfileDetails: updateProfileDetails,
            getAuthenticatedUser: getAuthenticatedUser,
            uploadPhoto: uploadPhoto
        }

        function getProfileDetails(username) {
            return $http.get("/api/profile/" + username)
                   .then(function(data){
                            return data.data;
                   });
        }

        function updateProfileDetails(profileDetails) {
            return $http({
                method: 'post',
                url: '/api/profile/'+ profileDetails.user.username,
                data: profileDetails
            });
        }

        function getAuthenticatedUser() {
            return $http.get("/api/account")
                    .then(function(data) {
                        return data.data;
                    });
        }

        function uploadPhoto(username, photo) {
            return $http({
                method: 'post',
                url:'/api/profile/'+ username+'/photo',
                data: photo
            });
        }
    }
})();