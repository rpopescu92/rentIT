(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('PropertiesController', PropertiesController)
            .filter('searchFor', function(){

            	return function(arr, searchString){
            		if(!searchString){
            			return arr;
            		}

            		var result = [];

            		searchString = searchString.toLowerCase();

            		angular.forEach(arr, function(item){
            			if(item.title.toLowerCase().indexOf(searchString) !== -1){
            				result.push(item);
            			}
            		});
            		return result;
            	};
            });

    PropertiesController.$inject = ['$scope', '$rootScope', 'PropertiesService', 'ViewPropertyService', '$state', 'PrincipalService', 'filterFilter'];

    function PropertiesController($scope, $rootScope, PropertiesService, ViewPropertyService, $state, PrincipalService, filterFilter){
        $scope.getProperties = getProperties;
        $scope.queryProperties = {
                    order: 'price',
                    limit: 5,
                    page: 1
           };
        $scope.properties = [];
        $scope.viewProperty = viewProperty;
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
        function viewProperty(property) {
            ViewPropertyService.setProperty(property);
            $scope.$broadcast('view-property', property);
            $state.go('view-property');
        }

        $rootScope.$on('load-properties', function() {
            $scope.getProperties($scope.query.page);
        });


    }
}) ();