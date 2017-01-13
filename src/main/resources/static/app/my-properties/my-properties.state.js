(function() {
    'use strict';

    angular.module('rentITApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');

        $stateProvider.state('my-properties', {
            url: '/my-properties',
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: '/app/my-properties/my-properties.html',
                    controller: 'MyPropertiesController',
                    title: 'My Properties'
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