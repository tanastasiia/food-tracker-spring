angular.module("password", [])
    .controller("PasswordChangeCtrl", ["$scope", "$http", function ($scope, $http) {

        $scope.passwordDto = {};
        $scope.showIncorrectPassword = false;
        $scope.showPasswordChanged = false;


        $scope.changePassword = function (passwordDto) {
            $http({
                method: "POST",
                url: "/api/user/change_password",
                data: $.param(passwordDto),
                headers: {"Content-Type": "application/x-www-form-urlencoded"}
            }).then(
                function (data) {
                    $scope.showIncorrectPassword = false;
                    $scope.showPasswordChanged = true;
                },
                function (error) {
                    $scope.showIncorrectPassword = true;
                    $scope.showPasswordChanged = false;
                }
            );
        }

    }]);