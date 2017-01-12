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
                    limit: 6,
                    page: 1
                };

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
                                        }
                                  }, function(error) {
                                        console.log("error get properties");
                                });
        }
    }
})();