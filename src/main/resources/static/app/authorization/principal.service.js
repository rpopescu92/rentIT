(function(){
    'use strict';

    angular.module('rentITApp')
            .factory('PrincipalService', PrincipalService);

    PrincipalService.$inject = ['$q', 'Account'];

    function PrincipalService($q, Account) {
        var _identity,
            _username,
            _authenticated = false;

        return {
            identity: identity,
            isAuthenticated: isAuthenticated,
            isIdentityResolved: isIdentityResolved,
            authenticate: authenticate,
            theUsername: theUsername,
            getUsername: getUsername
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

        function theUsername(username) {
            console.log(username);
            _username = username;
        }

        function getUsername() {
            console.log("principal user "+ _username);
            return _username;
        }
    }
})();