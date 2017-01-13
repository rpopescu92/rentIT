(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('MyPropertiesController', MyPropertiesController)
            .filter('startFrom', function() {
                return function(input, start) {
                    start = +start; //parse to int
                    return input.slice(start);
                }
            });

    MyPropertiesController.$inject = ['$state', '$scope','$rootScope', 'MyPropertiesService', 'Account', 'ViewPropertyService'];

    function MyPropertiesController($state, $scope, $rootScope, MyPropertiesService, Account, ViewPropertyService) {

        $scope.viewProperty = viewProperty;
        $scope.getUserProperties = getUserProperties;
        $scope.property = {};
        $scope.properties = [];
        $scope.query = {
                    order: 'price',
                    limit: 4,
                    page: 1
                };

        $scope.currentPage = 1;
        $scope.itemsPerPage = $scope.query.limit;
        $scope.numberOfPages;
        $scope.totalItems;

        init();
        $scope.username;

        function init() {
             Account.getAccount()
                      .then(function(data) {
                            $scope.username = data.username;
                            getUserProperties($scope.username, $scope.query.page);
                       }, function(error) {

                       });
        }

        function getUserProperties(username, page) {
            $scope.query.page = page;
            MyPropertiesService.getUserProperties($scope.username, $scope.query)
                                 .then(function(response){
                                       if(response.status == 200){
                                            $scope.properties = response.data.content;
                                            $scope.totalItems = response.data.totalElements;
                                            $scope.numberOfPages = response.data.totalPages;
                                        }
                                  }, function(error) {
                                        console.log("error get properties");
                                });
        }

        function viewProperty(property) {
             ViewPropertyService.setProperty(property);
             $scope.$broadcast('view-property', property);
             $state.go('view-property');
         }

         $scope.setPage = function (pageNo) {
             $scope.currentPage = pageNo;
           };

           $scope.pageChanged = function() {
             console.log('Page changed to: ' + $scope.currentPage);
             $scope.query.page = $scope.currentPage;
             getUserProperties($scope.username, $scope.query.page);
           };

         $scope.setItemsPerPage = function(num) {
           $scope.itemsPerPage = num;
           $scope.currentPage = 1; //reset to first paghe
         }

         $scope.previous = function(currentPage) {
            $scope.query.page = $scope.currentPage;
             getUserProperties($scope.username, $scope.query.page);
         }

         $scope.next = function(currentPage) {
            $scope.query.page = $scope.currentPage;
            getUserProperties($scope.username, $scope.query.page);
         }

    }
})();