(function() {
    'use strict';

    angular.module('rentITApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/admin');
        $stateProvider.state('admin', {
            url: '/admin',
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: '/app/admin/admin.html',
                    controller: 'AdminController'
                   }
            }
        }

        )
    }
})();