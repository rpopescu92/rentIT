(function() {
    'use strict';

    angular.module('rentITApp', ['ui.router', 'ngMaterial',
                    'ngStorage','ui.bootstrap',
                    'ngCacheBuster'])
                    .run(stateInitializer);
    stateInitializer.$inject = ['stateHandler'];

    function stateInitializer(stateHandler) {
          stateHandler.initialize();
    };
})();