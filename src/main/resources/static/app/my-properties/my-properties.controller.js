(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('MyPropertiesController', MyPropertiesController);

    MyPropertiesController.$inject = ['$state', '$scope', 'MyPropertiesService', 'Account'];

    function MyPropertiesController($state, $scope, MyPropertiesService, Account) {

        $scope.property = {};
        $scope.properties = [];
        $scope.query = {
                    order: 'price',
                    limit: 5,
                    page: 1
                };

        init();
        $scope.username;

        getUserProperties($scope.username, $scope.query.page);
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
                                 .then(function(data){
                                        console.log('properties retrieved');
                                  }, function(error) {
             });
        }
    }
})();