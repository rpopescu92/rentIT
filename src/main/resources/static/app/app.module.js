(function() {
    'use strict';

    angular.module('rentITApp', ['ui.router', 'ngMaterial',
                    'ngStorage','ui.bootstrap', 'ui.navbar',
                    'ngCacheBuster',
                    'angular-thumbnails'])
                    .run(stateInitializer);
    stateInitializer.$inject = ['stateHandler'];

    function stateInitializer(stateHandler) {
          stateHandler.initialize();
    };
})();