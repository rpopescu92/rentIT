(function() {
    'use strict';

    angular.module('rentITApp')
            .directive('starRating',
                        	function() {
                        		return {
                        			restrict : 'A',
                        			template : '<ul class="rating">'
                        					 + '	<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">'
                        					 + '\u2605'
                        					 + '</li>'
                        					 + '</ul>',
                        			scope : {
                        				ratingValue : '=',
                        				max : '=',
                        				onRatingSelected : '&'
                        			},
                        			link : function(scope, elem, attrs) {
                        				var updateStars = function() {
                        					scope.stars = [];
                        					for ( var i = 0; i < scope.max; i++) {
                        						scope.stars.push({
                        							filled : i < scope.ratingValue
                        						});
                        					}
                        				};

                        				scope.toggle = function(index) {
                        					scope.ratingValue = index + 1;
                        					scope.onRatingSelected({
                        						rating : index + 1
                        					});
                        				};

                        				scope.$watch('ratingValue',
                        					function(oldVal, newVal) {
                        						if (newVal) {
                        							updateStars();
                        						}
                        					});
                        			}
                }})
            .controller('ViewProperty', ViewProperty)
            ;

    ViewProperty.$inject = ['$scope', '$state', 'ViewPropertyService', '$rootScope'];

    function ViewProperty($scope, $state, ViewPropertyService, $rootScope) {

        $scope.property = {};
        $scope.images = [];
        $scope.isAuthenticated = true;
        $scope.addComment = addComment;
        $scope.comments = [];
        $scope.myComment;
        $scope.getSelectedRating = getSelectedRating;
        $scope.rating = 5;

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

         function addComment(comment) {
            $scope.comments.push(comment);
            $scope.myComment = "";

         }

         function getSelectedRating(rating) {
             console.log(rating);
         }
    }
})();