(function () {
    'use strict';

    angular.module('rentITApp')
        .directive('starRating',
            function () {
                return {
                    restrict: 'A',
                    template: '<ul class="rating">'
                    + '	<li ng-repeat="star in stars" ng-class="star" ng-click="toggle($index)">'
                    + '\u2605'
                    + '</li>'
                    + '</ul>',
                    scope: {
                        ratingValue: '=',
                        max: '=',
                        onRatingSelected: '&'
                    },
                    link: function (scope, elem, attrs) {
                        var updateStars = function () {
                            scope.stars = [];
                            for (var i = 0; i < scope.max; i++) {
                                scope.stars.push({
                                    filled: i < scope.ratingValue
                                });
                            }
                        };

                        scope.toggle = function (index) {
                            scope.ratingValue = index + 1;
                            scope.onRatingSelected({
                                rating: index + 1
                            });
                        };

                        scope.$watch('ratingValue',
                            function (oldVal, newVal) {
                                if (newVal) {
                                    updateStars();
                                }
                            });
                    }
                }
            })
        .controller('ViewProperty', ViewProperty)
    ;

    ViewProperty.$inject = ['$scope', '$stateParams', 'ViewPropertyService', 'Account', 'ProfileService'];

    function ViewProperty($scope, $stateParams, ViewPropertyService, Account, ProfileService) {

        var vm = this;

        $scope.property = {};
        var slides = $scope.slides = [];
        $scope.isAuthenticated = true;
        $scope.addComment = addComment;
        $scope.comments = [];
        $scope.getSelectedRating = getSelectedRating;
        $scope.rating = 5;
        $scope.historyRating = {};
        $scope.historyRating.author = {};
        $scope.historyRatings = [];
        $scope.username;
        $scope.isOwner = false;
        $scope.owner = {};
        $scope.photo = {};
        $scope.defaultPhoto = "/icons/default_photo.png";

        $scope.myInterval = 5000;
        $scope.noWrapSlides = false;
        $scope.active = 0;

        vm.propertyId = $stateParams.propertyId;
        var index = 0;

        init();

        function init() {
            ViewPropertyService.getProperty(vm.propertyId)
                .then(function (data) {
                    $scope.property = data.data;
                    $scope.slides = $scope.property.images;
                    for(var i=0;i<$scope.slides.length;i++) {
                        slides.push({
                            id: index++,
                            content: $scope.slides[i].content
                        });
                    }
                    $scope.slides = slides;
                    getAccount();
                    getHistoryRatings($scope.property.id);
                });
        }

        function isOwner() {
            if ($scope.property.owner.username === $scope.username) {
                $scope.isOwner = true;
            } else {
                ViewPropertyService.getOwnerDetails($scope.property.owner)
                    .then(function (data) {
                        $scope.owner.emailAddress = data.emailAddress;
                        $scope.owner.phoneNumber = data.phoneNumber;
                        console.log(data);
                        ProfileService.getProfileDetails($scope.property.owner.username)
                            .then(function (data) {
                                if (data.photo != null) {
                                    $scope.photo.content = data.photo.content;
                                    $scope.photo.name = data.photo.name;
                                } else {
                                    $scope.photo.content = $scope.defaultPhoto;
                                }
                            });
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
                .then(function (data) {
                    $scope.historyRatings = data;
                    console.log(data);
                    $scope.historyRatings.forEach(function (item) {
                        item.createdDate = new Date(item.createdDate).toLocaleString();
                    });
                });
        }

        function getSelectedRating(rating) {
            console.log(rating);
            $scope.historyRating.rating = rating;
        }

        function getAccount() {
            Account.getAccount()
                .then(function (data) {
                    $scope.username = data.username;
                    isOwner();
                });
        }
    }
})();