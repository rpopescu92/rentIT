/**
 * Created by chu23 on 13/06/2017.
 */
(function() {
    'use strict';

    angular.module('rentITApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function stateConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');

        $stateProvider.state('market-analysis', {
            url: '/market-analysis',
            parent: 'app',
            views: {
                'content@': {
                    templateUrl: '/app/market-analysis/market-analysis.html',
                    controller: 'MarketAnalysisController',
                    title: 'Market Analysis'
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