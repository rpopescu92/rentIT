(function(){
    'use strict';

    angular.module('rentITApp')
            .controller('HomeController', HomeController);

    function HomeController() {
        $scope.currentPage =1;
        $scope.pageChanged = function() {
            console.log("change");
        }
     }
})();