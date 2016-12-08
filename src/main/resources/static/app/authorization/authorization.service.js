(function(){

    'use strict';

    angular.module('rentITApp')
            .service('AuthorizationService', AuthorizationService);

    AuthorizationService.$inject = ['$http', '$rootScope', 'PrincipalService','$state',
                        '$sessionStorage','$q', 'AuthServerProvider'];

    function AuthorizationService($http, $rootScope, PrincipalService, $state,
                    $sessionStorage, $q, AuthServerProvider) {

        return {
            authorize: authorize,
            login: login,
            logout: logout,
            loginWithToken: loginWithToken,
            getPreviousState: getPreviousState,
            resetPreviousState: resetPreviousState,
            storePreviousState: storePreviousState
        }

        function authorize(force) {
            PrincipalService.identity(force).then(authThen);

            function authThen() {
                var isAuthenticated = PrincipalService.isAuthenticated();

                if(isAuthenticated && $rootScope.toState.name === 'admin') {
                    $state.go('admin');
                }

                if(isAuthenticated && $rootScope.toState.name === 'login') {
                    $state.go('home')
                }

                if(isAuthenticated && $rootScope.toState.name !== 'login') {
                    $state.go($rootScope.toState.name);
                 }

                 if(isAuthenticated && !$rootScope.fromState.name && getPreviousState()) {
                      var previousState = getPreviousState();
                      resetPreviousState();
                     $state.go(previousState.name, previousState.params);
                  }
                  storePreviousState($rootScope.toState.name, $rootScope.toStateParams);

                  if(!isAuthenticated && $rootScope.toState.name !== 'login') {
                        $state.go('login');
                   }
            }
        }

        function login(credentials, callback) {
            var cb = callback || angular.noop;
            var deferred = $q.defer();
            AuthServerProvider.login(credentials)
                                .then(loginThen)
                                .catch(function(err){
                                    this.logout();
                                    deferred.reject(err);
                                    return cb(err);
                                }.bind(this));
             function loginThen(data) {
                PrincipalService.identity(true).then(function(account){
                    deferred.resolve(data);
                });
                return cb();
             }
             return deferred.promise;
        }

        function logout() {
            AuthServerProvider.logout();
            PrincipalService.authenticate(null);
        }
        function loginWithToken(jwt) {
            return AuthServerProvider.loginWithToken(jwt);
        }

        function resetPreviousState() {
            delete $sessionStorage.previousState;
        }

        function getPreviousState() {
            return $sessionStorage.previousState;
        }
        function storePreviousState(previousStateName, previousStateParams){
            $sessionStorage.previousState = {"name": previousStateName, "params": previousStateParams};
        }
    }
})();