(function () {
    'use strict';

    angular.module('rentITApp')
        .controller('PropertiesController', PropertiesController)
        .filter('searchFor', function () {

            return function (arr, searchString) {
                if (!searchString) {
                    return arr;
                }

                var result = [];

                searchString = searchString.toLowerCase();

                angular.forEach(arr, function (item) {
                    if (item.title.toLowerCase().indexOf(searchString) !== -1) {
                        result.push(item);
                    }
                });
                return result;
            };
        });

    PropertiesController.$inject = ['$scope', '$rootScope', 'PropertiesService', 'ViewPropertyService', '$state', 'PrincipalService', 'filterFilter'];

    function PropertiesController($scope, $rootScope, PropertiesService, ViewPropertyService, $state, PrincipalService, filterFilter) {
        $scope.getProperties = getProperties;
        $scope.query = {
            order: 'price',
            limit: 3,
            page: 1
        };
        $scope.properties = [];
        $scope.viewProperty = viewProperty;
        $scope.roomsData = [
            {
                "label": 1,
                "id": 1
            },
            {
                "label": 2,
                "id": 2
            },
            {
                "label": 3,
                "id": 3
            },
            {
                "label": 4,
                "id": 4
            },
            {
                "label": 5,
                "id": 5
            }];
        $scope.roomsModel = [];
        $scope.roomsSettings = {
            scrollableHeight: '200px',
            scrollable: true,
            enableSearch: true
        };
        $scope.toggle = toggle;
        $scope.selected = [];
        $scope.roomsChecked = roomsChecked;
        $scope.favorite = favorite;
        $scope.rooms = [];

        $scope.currentPage = 1;
        $scope.itemsPerPage = $scope.query.limit;
        $scope.pages = [];
        $scope.totalItems = [];

        init();

        function init() {
            getProperties($scope.query.page);
        }

        function toggle(item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            }
            else {
                list.push(item);
            }
        }

        function roomsChecked(room) {
            var i = $.inArray(room, $scope.rooms);
            if (i > -1) {
                $scope.rooms.splice(i, 1);
            } else {
                $scope.rooms.push(room);
            }
        }

        $scope.roomFilter = function (properties) {
            if ($scope.rooms.length > 0) {
                if ($.inArray(properties.roomsNumber, $scope.rooms) < 0)
                    return;
            }

            return properties;
        };
        function getProperties(page) {
            $scope.query.page = page;

            PropertiesService.getProperties($scope.query)
                .then(function (response) {
                        if (response.status == 200) {
                            $scope.properties = response.data.content;
                            $scope.totalItems = response.data.totalElements;
                            $scope.pages = response.data.totalPages;


                            console.log($scope.properties);
                        }
                    },
                    function (error) {
                        console.log("error get properties");
                    });
        }

        function viewProperty(property) {
            $scope.$broadcast('view-property', property);
            $state.go('view-property', {propertyId: property.id});
        }

        $rootScope.$on('load-properties', function () {
            $scope.getProperties($scope.query.page);
        });

        function favorite(property) {

        }

        $scope.setPage = function (pageNo) {
            $scope.currentPage = pageNo;
        };

        $scope.pageChanged = function () {
            getProperties($scope.query.page);
        }

        $scope.setItemsPerPage = function (num) {
            $scope.query.limit = num;
            getProperties(1);
        };

        $scope.previous = function () {
            $scope.query.page = $scope.currentPage;
            getUserProperties($scope.username, $scope.query.page);
        };

        $scope.next = function () {
            $scope.query.page = $scope.currentPage;
            getUserProperties($scope.username, $scope.query.page);
        };
    }
})();