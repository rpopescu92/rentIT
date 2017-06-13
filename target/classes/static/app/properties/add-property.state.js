(function() {

    'use strict';

    angular.module('rentITApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');

        $stateProvider.state('add-property', {
            url:'/add-property',
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: '/app/properties/add-property.html',
                    controller: 'AddPropertyController'

                }
            },
            resolve: {
                authorize: ['AuthorizationService',
                           function (AuthorizationService) {
                               return AuthorizationService.authorize();
            }]
            }
        });
    }
})();