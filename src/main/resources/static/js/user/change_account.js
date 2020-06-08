
angular.module("accountChange", [])
    .controller("AccountChangeCtrl", ["$scope", "$http", function ($scope, $http) {
        console.log("AccountChangeCtrl");

        $scope.newUser = {};
        $scope.user = {};

        $scope.showError = false;

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
        };
        $scope.initForm = function(){
            $scope.user =  $scope.getUser();
        };

        $scope.changeAccount = function (user) {

            $http({
                method: "POST",
                url: "/api/user/change_account",
                data: $.param(user),
                headers: {"Content-Type" : "application/x-www-form-urlencoded" }
            }).then(
                function (data) {
                    location.replace("/account");
                },
                function (error) {
                    $scope.showError = true;
                }
            );
        }


    }]);