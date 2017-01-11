(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('ViewProperty', ViewProperty);

    ViewProperty.$inject = ['$scope', '$state', 'ViewPropertyService', '$rootScope'];

    function ViewProperty($scope, $state, ViewPropertyService, $rootScope) {

        $scope.property;
        $scope.images = [];
        $scope.isAuthenticated = true;

        init();

        function init() {
            $scope.property = ViewPropertyService.getProperty();
            $scope.images = $scope.property.images;

        }

        $scope.currentIndex = 0;
        $scope.setCurrentSlideIndex = function (index) {
             $scope.currentIndex = index;
         };
        $scope.isCurrentSlideIndex = function (index) {
             return $scope.currentIndex === index;
         };
        $scope.prevSlide = function () {
             $scope.currentIndex = ($scope.currentIndex < $scope.slides.length - 1) ? ++$scope.currentIndex : 0;
         };
         $scope.nextSlide = function () {
             $scope.currentIndex = ($scope.currentIndex > 0) ? --$scope.currentIndex : $scope.slides.length - 1;
         };
    }
})();