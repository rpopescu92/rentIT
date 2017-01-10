(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('PropertiesController', PropertiesController);

    PropertiesController.$inject = ['$scope', '$rootScope', 'PropertiesService'];

    function PropertiesController($scope, $rootScope, PropertiesService){
        $scope.getProperties = getProperties;
        $scope.queryProperties = {
                    order: 'price',
                    limit: 5,
                    page: 1
           };

        init();

        function init() {
            getProperties($scope.queryProperties.page);
        }


        function getProperties(page) {
            $scope.queryProperties.page = page;

            PropertiesService.getProperties($scope.queryProperties)
                                .then(function(response){
                                    if(response.status == 200){
                                        $scope.properties = response.data.content;
                                    }
                                },
                                function(error){

                                } );
        }

        $rootScope.$on('load-properties', function() {
            $scope.getProperties($scope.query.page);
        });


    }
}) ();