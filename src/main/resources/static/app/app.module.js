(function() {
    'use strict';

    angular.module('rentITApp', ['ui.router', 'ngMaterial', 'ngStorage']).run(stateInitializer);
    stateInitializer.$inject = ['stateHandler'];

    function stateInitializer(stateHandler) {
          stateHandler.initialize();
    };
})();