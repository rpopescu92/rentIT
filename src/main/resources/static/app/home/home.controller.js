(function(){
    'use strict';

    angular.module('rentITApp')
            .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope'];


    function HomeController($scope) {
        $scope.myInterval = 3000;

                $scope.slides = [
                                    {
                                      image: 'http://lorempixel.com/400/200/'
                                    },
                                    {
                                      image: 'http://lorempixel.com/400/200/food'
                                    },
                                    {
                                      image: 'http://lorempixel.com/400/200/sports'
                                    },
                                    {
                                      image: 'http://lorempixel.com/400/200/people'
                                    }
                                  ];

        $scope.noWrapSlides = false;
          $scope.activeSlide = 0;


     }
})();