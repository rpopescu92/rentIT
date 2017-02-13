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

    ViewProperty.$inject = ['$scope', '$state', 'ViewPropertyService', '$rootScope', 'Account', 'ProfileService'];

    function ViewProperty($scope, $state, ViewPropertyService, $rootScope, Account, ProfileService) {

        $scope.property = {};
        $scope.images = [];
        $scope.isAuthenticated = true;
        $scope.addComment = addComment;
        $scope.comments = [];
        $scope.myComment;
        $scope.getSelectedRating = getSelectedRating;
        $scope.rating = 5;
        $scope.historyRating = {};
        $scope.historyRating.author = {};
        $scope.historyRatings = [];
        $scope.username;
        $scope.isOwner = false;
        $scope.owner= {};
        $scope.myInterval = 3000;
        $scope.photo = {};
        $scope.defaultPhoto = "/icons/default_photo.png";

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
        init();

        function init() {
            $scope.property = ViewPropertyService.getProperty();
            $scope.images = $scope.property.images;
            getAccount();
            getHistoryRatings($scope.property.id);
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

         function isOwner(){
            if($scope.property.owner.username === $scope.username) {
                $scope.isOwner = true;
            } else {
                ViewPropertyService.getOwnerDetails($scope.property.owner)
                                    .then(function(data) {
                                        $scope.owner.emailAddress = data.emailAddress;
                                        $scope.owner.phoneNumber = data.phoneNumber;
                                        ProfileService.getProfileDetails($scope.property.owner.username)
                                               .then(function(data) {
                                                   if(data.photo != null) {
                                                      $scope.photo.content = data.photo.content;
                                                      $scope.photo.name = data.photo.name;
                                                   } else {
                                                      $scope.photo.content = $scope.defaultPhoto;
                                    }});
                               });
            }
         }
         function addComment(comment) {
            $scope.comments.push(comment);
            $scope.myComment = "";
            $scope.historyRating.comment = comment;
            $scope.historyRating.property = $scope.property;
            $scope.historyRating.author.username = $scope.username;
            ViewPropertyService.addHistoryRating($scope.historyRating);
            $scope.historyRatings.push($scope.historyRating);
         }

         function getHistoryRatings(propertyId) {
            ViewPropertyService.getHistoryRatings(propertyId)
                        .then(function(data){
                            $scope.historyRatings = data;
                        });
         };

         function getSelectedRating(rating) {
             console.log(rating);
             $scope.historyRating.rating = rating;
         }

         function getAccount() {
            Account.getAccount()
                    .then(function(data){
                        $scope.username = data.username;
                        isOwner();
                    });
         }
    }
})();