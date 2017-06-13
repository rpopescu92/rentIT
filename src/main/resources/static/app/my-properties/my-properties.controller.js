(function () {
    'use strict';

    angular.module('rentITApp')
        .controller('MyPropertiesController', MyPropertiesController)
        .filter('startFrom', function () {
            return function (input, start) {
                start = +start; //parse to int
                return input.slice(start);
            }
        });

    MyPropertiesController.$inject = ['$state', '$scope', '$rootScope', 'MyPropertiesService',
        'Account', 'ViewPropertyService', 'AlterPropertyService'];

    function MyPropertiesController($state, $scope, $rootScope, MyPropertiesService,
                                    Account, ViewPropertyService, AlterPropertyService) {

        $scope.viewProperty = viewProperty;
        $scope.getUserProperties = getUserProperties;
        $scope.edit = edit;
        $scope.rented = rented;
        $scope.unrent = unrent;
        $scope.property = {};
        $scope.properties = [];
        $scope.query = {
            order: 'price',
            limit: 3,
            page: 1
        };

        $scope.currentPage = 1;
        $scope.itemsPerPage = $scope.query.limit;
        $scope.pages = [];
        $scope.totalItems;

        init();
        $scope.username;

        function init() {
            Account.getAccount()
                .then(function (data) {
                    $scope.username = data.username;
                    getUserProperties($scope.query.page);
                }, function (error) {

                });
        }

        function getUserProperties(page) {
            $scope.query.page = page;
            MyPropertiesService.getUserProperties($scope.username, $scope.query)
                .then(function (response) {
                    if (response.status == 200) {
                        $scope.properties = response.data.content;
                        $scope.totalItems = response.data.totalElements;
                        $scope.pages = response.data.totalPages;
                    }
                }, function (error) {
                    console.log("error get properties");
                });
        }

        function viewProperty(property) {
            $scope.$broadcast('view-property', property);
            $state.go('view-property', {propertyId: property.id});
        }

        function rented(id) {
            AlterPropertyService.rentProperty(id, "RENTED");
            init();
        }

        function unrent(id) {
            AlterPropertyService.rentProperty(id, "NOT_RENTED");
            init();
        }

        function edit(property) {

        }

        $scope.setPage = function (pageNo) {
            $scope.currentPage = pageNo;
        };

        $scope.pageChanged = function () {
            getUserProperties($scope.query.page);
        };

        $scope.setItemsPerPage = function (num) {
            $scope.query.limit = num;
            getUserProperties(1);
        };

        $scope.previous = function () {
            $scope.query.page = $scope.currentPage;
            getUserProperties($scope.username, $scope.query.page);
        };

        $scope.next = function () {
            $scope.query.page = $scope.currentPage;
            getUserProperties($scope.username, $scope.query.page);
        }

    }
})();