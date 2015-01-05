'use strict';

var CloudCardApp = angular.module("CloudCardApp", [
    "ui.bootstrap",
    "ngSanitize",
    "CloudCardApp.controllers",
    "CloudCardApp.directives"
]);


var controllers = angular.module("CloudCardApp.controllers", []);

controllers.controller("ViewCardCtrl", ['$scope',
  function ($scope) {

        $scope.cardFieldLabels = {
            "firstName": "First Name",
            "lastName": "Last Name",
            "nickname": "Nickname",
            "gender": "Gender",
            "birthDate": "Birth Date",
            "nationality": "Nationality",
            "phone": "Phone",
            "mobilePhone": "Mobile Phone",
            "workPhone": "Work Phone",
            "email": "Email",
            "website": "Website",
            "address_street": "Street",
            "address_postalCode": "Postal Code",
            "address_locality": "Locality",
            "address_region": "Region",
            "address_country": "Country"
        };

        if (typeof card !== 'undefined') {
            $scope.card = angular.fromJson(card);

            $scope.cardBackground = {
                'background-image': 'url(data:image/jpeg;base64,' + $scope.card.backgroundImage + ')'
            };
        }

        $scope.success = success;
        $scope.error = error;

  }]);


controllers.controller("SearchCardCtrl", ['$scope', '$window',
  function ($scope, $window) {

        $scope.env = 'PROD';

        $scope.searchCard = function () {

            var url = $scope.env + '/' + encodeURIComponent($scope.cloudCard);

            $window.location.href = url;

        };

  }]);



var directives = angular.module("CloudCardApp.directives", []);

directives.directive('dynamicResize', ['$window',
    function ($window) {
        return function (scope, element) {
            scope.getWinHeight = function () {
                return $window.innerHeight;
            };

            var setNavHeight = function (newHeight) {
                element.css('height', newHeight + 'px');
            };

            // Set on load
            scope.$watch(scope.getWinHeight, function (newValue, oldValue) {
                setNavHeight(scope.getWinHeight() - 0);
            }, true);

            // Set on resize
            angular.element($window).bind('resize', function () {
                scope.$apply();
            });
        };
}]);