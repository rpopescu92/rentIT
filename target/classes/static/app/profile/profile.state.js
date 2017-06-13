(function() {
    'use strict';

    angular.module('rentITApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
        //$urlRouterProvider.otherwise('/home');

        $stateProvider.state('profile', {
            url: '/profile',
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: 'app/profile/profile.html',
                    controller: 'ProfileController'
                }
               },
             resolve: {
                    authorize: ['AuthorizationService',
                        function(AuthorizationService) {
                              return AuthorizationService.authorize();
                         }]
                  }
        })
    }
})();