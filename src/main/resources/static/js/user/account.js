angular.module("account", [])
    .controller("AccountCtrl", ["$scope", "$http", function ($scope, $http) {
        console.log("acc")

        $scope.user = {};

        $scope.getUser = function () {
            $http({
                method: "GET",
                url: "/api/user/user",
                headers: {"Content-Type": "application/json"}
            }).then(
                function (data) {
                    $scope.user = data.data;
                },
                function (error) {
                    console.log("user error")
                }
            );
        }


    }]);