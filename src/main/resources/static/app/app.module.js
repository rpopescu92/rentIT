(function() {
    'use strict';

    angular.module('rentITApp', ['ui.router', 'ngMaterial',
                    'ngStorage','ui.bootstrap',
                    'ngCacheBuster',
                    'ngLocale'])
                    .run(stateInitializer);
    stateInitializer.$inject = ['stateHandler', '$rootScope'];

    function stateInitializer(stateHandler, $rootScope) {
        stateHandler.initialize();
        $rootScope.$on("$routeChangeSuccess", function(currentRoute, previousRoute){
            //Change page title, based on Route information
            $rootScope.title = current.$$route.title
          });

    };

    /*myApp.run(['$rootScope', function($rootScope) {
        $rootScope.$on('$routeChangeSuccess', function (event, current, previous) {
            $rootScope.title = current.$$route.title;
        })
        */
})();