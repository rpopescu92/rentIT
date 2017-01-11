(function() {
    'use strict';

    angular.module('rentITApp')
            .service('AuthServerProvider', AuthServerProvider);

    AuthServerProvider.$inject = ['$http', '$sessionStorage'];

    function AuthServerProvider($http, $sessionStorage) {

        return {
            getToken: getToken,
            login: login,
            logout: logout,
            loginWithToken: loginWithToken
        }

        function getToken() {
            return $sessionStorage.authenticationToken;
        }

        function login(credentials) {
            var data = {
                username: credentials.username,
                password: credentials.password
            }

            return $http.post('/api/authenticate',data
                    ).then(function successCallback(response) {
                            var bearerToken = response.headers('Authorization');
                            if(angular.isDefined(bearerToken) && bearerToken.slice(0,7) === 'Bearer ') {
                                var jwt = bearerToken.slice(7, bearerToken.length);
                                $sessionStorage.authenticationToken = jwt;
                                console.log(jwt);
                                return jwt;
                            }
                        },
                        function errorCallback(response) {
                            console.log(response.status);
                        }
                        );

        }

        function logout() {
            delete $sessionStorage.authenticationToken;
        }

        function loginWithToken(jwt) {
            var deferred = $q.defer();

            if(angular.isDefined(jwt)) {
                $sessionStorage.authenticationToken = jwt;
                deferred.resolve(jwt);
            }
            else {
                deferred.reject();
            }
            return deferred.promise;
        }
    }
})();