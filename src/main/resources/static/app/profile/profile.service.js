(function() {
    'use strict';

    angular.module('rentITApp')
            .service('ProfileService', ProfileService);

    ProfileService.$inject = ['$http'];

    function ProfileService($http) {

        return{
            getProfileDetails: getProfileDetails,
            updateProfileDetails: updateProfileDetails
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
                url: '/api//profile/'+ profileDetails.username,
                data: profileDetails
            });
        }
    }
})();