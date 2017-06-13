(function () {

    angular.module('rentITApp')
        .controller('MarketAnalysisController', MarketAnalysisController);

    MarketAnalysisController.$inject = ['$scope', 'MarketAnalysisService'];

    function MarketAnalysisController($scope, MarketAnalysisService) {
        $scope.title = 'Market Analysis';
        $scope.labels = ['Sector 1', 'Sector 2', 'Sector 3', 'Sector 4', 'Sector 5', 'Sector 6'];

        $scope.data = [
            [65, 59, 80, 81, 56, 55, 40]
        ];

        $scope.cityLabels = [];
        $scope.cityData = [];

        init();

        function init() {
            MarketAnalysisService.get()
                .then(function (data) {
                    $scope.data = data.data;
                });

            MarketAnalysisService.getForCities()
                .then(function (data) {
                   console.log(data.data);
                    $scope.cityLabels = data.data.labels;
                    $scope.cityData = data.data.data;
                });
        }
    }

})();