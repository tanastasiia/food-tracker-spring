angular.module("admin", [])
    .controller("UserCtrl", ["$scope", "$http", function ($scope, $http) {
        console.log("userCtrl")
        $scope.users = [];
        $scope.test = "test123";
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
        $scope.changeRole = function (userId, role) {
            console.log(userId);
            console.log(role);
            $http({
                method: "POST",
                url: "/api/admin/change_role",
                data: $.param({"userId": userId, "role": role}),
                headers: {"Content-Type": "application/x-www-form-urlencoded"}
            }).then(
                location.reload()
            );
        }

    }]);