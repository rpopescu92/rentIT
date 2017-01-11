(function(){
    'use strict';

    angular.module('rentITApp')
            .service('NavbarService', NavbarService);

    NavbarService.$inject = ['$http'];

    function NavbarService($http) {
        return  {
            getAuthenticatedUser: getAuthenticatedUser
        }

        function getAuthenticatedUser(){
            return $http.get("/api/account")
                    .then(function(data){
                        return data.data;
                       });
        }
    }
})()