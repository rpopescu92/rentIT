(function() {
    'use strict';

    angular.module('rentITApp')
            .config(stateConfig);

     stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

     function stateConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');

        $stateProvider.state('properties',{
            url: '/properties',
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: 'app/properties/properties.html',
                    controller: 'PropertiesController'
                }
            }
        });
     }
})();