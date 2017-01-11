(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('ViewProperty', ViewProperty);

    ViewProperty.$inject = ['$scope', '$state', 'ViewPropertyService', '$rootScope'];

    function ViewProperty($scope, $state, ViewPropertyService, $rootScope) {

        $scope.property;

        init();

        function init() {
            $scope.property = ViewPropertyService.getProperty();
        }
    }
})();