(function() {
    'use strict';

    angular.module('rentITApp')
            .service('ViewPropertyService', ViewPropertyService);

    ViewPropertyService.$inject = ['$http'];

    function ViewPropertyService($http) {

        var viewProperty;

        return {
            getProperty: getProperty,
            setProperty: setProperty,
            addHistoryRating: addHistoryRating,
            getHistoryRatings: getHistoryRatings
        }

        function getProperty() {
            return viewProperty;
        }

        function setProperty(property) {
            viewProperty = property;
        }

        function addHistoryRating(historyRating) {
            return $http({
                url:'/api/ratings/'+ historyRating.property.id,
                method: 'POST',
                data: historyRating
            });
        }

        function getHistoryRatings(propertyId){
            return $http.get('/api/ratings/'+propertyId)
                    .then(function(data){
                            return data.data;
                    });
        }
    }
})();