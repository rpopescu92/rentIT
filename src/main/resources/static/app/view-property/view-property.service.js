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
            console.log("property "+viewProperty);
            return viewProperty;
        }

        function setProperty(property) {
            console.log("set "+ property);
            viewProperty = property;
        }
    }
})();