(function () {
    'use strict';

    angular.module('rentITApp')
        .controller('PropertiesController', PropertiesController);

    PropertiesController.$inject = ['$scope', '$rootScope', 'PropertiesService', '$state'];

    function PropertiesController($scope, $rootScope, PropertiesService, $state) {
        $scope.getProperties = getProperties;
        $scope.query = {
            order: 'price',
            limit: 3,
            page: 1
        };
        $scope.properties = [];
        $scope.viewProperty = viewProperty;
        $scope.selected = [];

        $scope.currentPage = 1;
        $scope.itemsPerPage = $scope.query.limit;
        $scope.pages = [];
        $scope.totalItems = [];
        $scope.searchName = '';
        $scope.searchLocation = '';
        $scope.nrRooms = [1, 2, 3, 4, 5];
        $scope.selectedNrRooms = [];
        $scope.selectedNrStars = [];
        $scope.minPrice = '';
        $scope.maxPrice = '';
        $scope.search = search;

        init();

        function init() {
            getProperties($scope.query.page);
        }

        $scope.toggle = function (item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            }
            else {
                list.push(item);
            }
        };

        $scope.exists = function (item, list) {
            return list.indexOf(item) > -1;
        };

        function search() {
            var searchOptions = {
                name: $scope.searchName,
                numberOfRooms: $scope.selectedNrRooms,
                numberOfStars: $scope.selectedNrStars,
                minPrice: $scope.minPrice,
                maxPrice: $scope.maxPrice
            };
            var search = _isValidSearch() ? searchOptions : null;
            PropertiesService.getProperties($scope.query, search)
                .then(function (response) {
                        if (response.status == 200) {
                            $scope.properties = response.data.content;
                            $scope.totalItems = response.data.totalElements;
                            $scope.pages = response.data.totalPages;

                        }
                    },
                    function (error) {
                        console.log("error get properties");
                    });
        }

        function getProperties(page) {
            $scope.query.page = page;

            PropertiesService.getProperties($scope.query, null)
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

        function _isValidSearch() {
            if ($scope.searchName !== '' || $scope.selectedNrRooms.length > 0 ||
                $scope.selectedNrStars.length > 0 || $scope.minPrice !== '' || $scope.maxPrice !== '') {
                return true;
            }
            return false;
        }

        $rootScope.$on('load-properties', function () {
            $scope.getProperties($scope.query.page);
        });

        $scope.setPage = function (pageNo) {
            $scope.currentPage = pageNo;
        };

        $scope.pageChanged = function () {
            getProperties($scope.query.page);
        };

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