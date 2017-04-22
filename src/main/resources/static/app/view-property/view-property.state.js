(function() {
    'use strict';

    angular.module('rentITApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');

        $stateProvider.state('view-property', {
            url: '/view-property/:propertyId',
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: '/app/view-property/view-property.html',
                    controller: 'ViewProperty'
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