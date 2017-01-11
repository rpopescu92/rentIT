(function() {
    'use strict';

    angular.module('rentITApp')
            .service('ViewPropertyService', ViewPropertyService);

    ViewPropertyService.$inject = ['$http'];

    function ViewPropertyService($http) {

        var viewProperty;

        return {
            getProperty: getProperty,
            setProperty: setProperty
        }

        function getProperty() {
            return viewProperty;
        }

        function setProperty(property) {
            viewProperty = property;
        }
    }
})();