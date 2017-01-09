(function(){
    'use strict';

    angular.module('rentITApp')
            .factory('PrincipalService', PrincipalService);

    PrincipalService.$inject = ['$q', 'Account'];

    function PrincipalService($q, Account) {
        var _identity,
            _authenticated = false;

        return {
            identity: identity,
            isAuthenticated: isAuthenticated,
            isIdentityResolved: isIdentityResolved,
            authenticate: authenticate
        }

        function authenticate(identity) {
            _identity = identity;
            _authenticated = identity!== null;
        }

        function identity(force) {
            var deferred = $q.defer();

            if(force === true) {
                _identity = undefined;
            }
            if(angular.isDefined(_identity)) {
                deferred.resolve(_identity);
                return deferred.promise;
            }

             Account.getAccount()
                            .then(function(data) {
                                _identity = data.data;
                                _authenticated = true;
                                deferred.resolve(_identity);
                                console.log("get account");
                            }, function(error) {
                                _identity = null;
                                _authenticated = false;
                                deferred.resolve(_identity);
                            });

             return deferred.promise;
        }

        function isAuthenticated() {
            return _authenticated;
        }

        function isIdentityResolved() {
            return angular.isDefined(_identity);
        }
    }
})();