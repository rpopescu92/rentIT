(function() {
    'use strict';

    angular.module('rentITApp')
            .service('Account',Account);

    Account.$inject = ['$http'];

    function Account($http) {
        return: {
            getAccount: getAccount
        }

        function getAccount() {
            return $http.get("/api/account")
                        .then(function(data){
                            return data.data;
                        });
        }
    }
})();