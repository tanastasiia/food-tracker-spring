angular.module("admin", [])
    .controller("UserCtrl", ["$scope", "$http", function ($scope, $http) {
        $scope.users = [];
        $scope.foodInfos = [];
        $scope.meals = [];

        $scope.foodPage = 0;
        $scope.foodPageMax = 0;

        $scope.usersFoodPage = 0;
        $scope.usersFoodPageMax = 0;

        $scope.getUsers = function () {
            $http({
                method: "GET",
                url: "/api/admin/all_users",
                headers: {"Content-Type": "application/json"},
            }).then(
                function (data) {
                    $scope.users = data.data.users;
                },
                function (error) {
                    console.log("users error")
                }
            );
        }
        $scope.getFood = function () {
            $http({
                method: "GET",
                url: "/api/admin/all_food",
                headers: {"Content-Type": "application/json"},
                params: {page: $scope.foodPage}
            }).then(
                function (data) {
                    $scope.foodInfos = data.data.foodInfos.content;
                    $scope.foodPageMax = data.data.foodInfos.totalPages - 1;

                },
                function (error) {
                    console.log("foods error")
                }
            );
        };
        $scope.getUsersFood = function () {
            $http({
                method: "GET",
                url: "/api/admin/all_meals",
                headers: {"Content-Type": "application/json"},
                params: {page: $scope.usersFoodPage}
            }).then(
                function (data) {
                    $scope.meals = data.data.meals.content;
                    $scope.usersFoodPageMax = data.data.meals.totalPages - 1;
                },
                function (error) {
                    console.log("usersFood error")
                }
            );
        }
        $scope.changeRole = function (userId) {
            $http({
                method: "POST",
                url: "/api/admin/user/" + userId + "/change/role",
                headers: {"Content-Type": "application/x-www-form-urlencoded"}
            }).then(
                location.reload()
            );
        }

    }]);