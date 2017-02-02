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
        $scope.items = [1, 2, 3, 4, 5];
        $scope.toggle = toggle;
        $scope.selected = [];
        $scope.exists = exists;
        $scope.favorite = favorite;

        init();

        function init() {
            getProperties($scope.queryProperties.page);
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

        function exists(item, list) {
            return list.indexOf(item) > -1;
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

        function favorite(property) {

        }

    }
}) ();