(function() {
    'use strict';

    angular.module('rentITApp')
            .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$scope', '$rootScope','PrincipalService'];

    function NavbarController($scope, $rootScope, PrincipalService) {
        $scope.init = init;

        $scope.isAuthenticated = false;

        init();

        $rootScope.$on("authenticationSuccess", function(event, data) {
            $scope.isAuthenticated = true;
        });

        $scope.konami = [{
                name: "Konami",
                link: "#",
                subtree: [{
                    name: "Metal Gear",
                    link: "#",
                    subtree: [{
                        name: "Metal Gear",
                        link: "metal-gear"
                    }, {
                        name: "Metal Gear 2: Solid Snake",
                        link: "metal-gear2"
                    }, {
                        name: "Metal Gear Solid: The Twin Snakes",
                        link: "metal-gear-solid"
                    }]
                }]
            }];

            $scope.trees = [{
                name: "Konami",
                link: "#",
                subtree: [{
                    name: "Metal Gear",
                    link: "#",
                    subtree: [{
                        name: "Metal Gear",
                        link: "metal-gear"
                    }, {
                        name: "Metal Gear 2: Solid Snake",
                        link: "#"
                    }, {
                        name: "Metal Gear Solid: The Twin Snakes",
                        link: "#"
                    }]
                }, {
                    name: "divider",
                    link: "#"
                }, {
                    name: "Castlevania",
                    link: "#",
                    subtree: [{

                    }]
                }]
            }]
        function init() {
            $scope.isAuthenticated = PrincipalService.isAuthenticated();

        }
    }
})();