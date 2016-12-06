(function(){
    'use strict';

    angular.module('rentITApp')
            .factory('stateHandler', stateHandler);

    stateHandler.$inject('$rootScope','$state', '$sessionStorage', '$window','AuthorizationService','Principal');

    function stateHandler($rootScope, $state, $sessionStorage, $window, AuthorizationService, Principal) {

    }
})();