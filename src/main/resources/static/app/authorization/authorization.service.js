(function(){

    'use strict';

    angular.module('rentITApp')
            .service('AuthorizationService', AuthorizationService);

    AuthorizationService.$inject = ['$http', '$rootScope', '$scope','Principal','$state',
                        '$sessionStorage','$q', 'AuthServerProvider'];

    function AuthorizationService($http, $rootScope, $scope, Principal, $state,
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
            Principal.identity(force).then(authThen);

            function authThen() {
                var isAuthenticated = Principal.isAuthenticated();

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
    }
})();