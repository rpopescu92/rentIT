(function() {
    'use strict';

    angular.module('rentITApp')
            .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/view-property');

        $stateProvider.state('view-property', {
            url: '/view-property',
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: '/app/view-property/view-property.html',
                    controller: 'ViewProperty'
                }
            }
        });
    }
})();