angular.module("food", [])
    .controller("FoodCtrl", ["$scope", "$http", function ($scope, $http) {
        console.log("foodCtrl");

        $scope.userFood = {};
        $scope.food = {};
        $scope.showAddUserSuccess = false;
        $scope.showAddSuccess = false;
        $scope.showAddUserError = false;
        $scope.showAddError = false;

        $scope.userStat={};

        $scope.addUserFood = function (userFood) {
            $http({
                method: "POST",
                url: "/api/user/add_user_food",
                data: $.param(userFood),
                headers: {"Content-Type" : "application/x-www-form-urlencoded" }
            }).then(
                function (data) {
                    $scope.showAddUserSuccess = true;
                    $scope.showAddSuccess = false;
                    $scope.showAddUserError = false;
                    $scope.showAddError = false;
                    $scope.userStat.calories=data.data;
                },
                function (error) {
                    $scope.showAddUserError = true;
                    $scope.showAddUserSuccess = false;
                    $scope.showAddSuccess = false;
                    $scope.showAddError = false;
                }
            );
        }
        $scope.addFood = function (food) {
            $http({
                method: "POST",
                url: "/api/user/add_food",
                data: $.param(food),
                headers: {"Content-Type" : "application/x-www-form-urlencoded"}
            }).then(
                function (data) {
                    $scope.showAddSuccess = true;
                    $scope.showAddUserSuccess = false;
                    $scope.showAddUserError = false;
                    $scope.showAddError = false;

                },
                function (error) {
                    $scope.showAddError = true;
                    $scope.showAddUserSuccess = false;
                    $scope.showAddSuccess = false;
                    $scope.showAddUserError = false;

                }
            );
        }

        $scope.getuserStat = function () {
            console.log("getuserStat");
            $http({
                method: "GET",
                url: "/api/user/user_statistics",
                headers: {"Content-Type": "application/json"}
            }).then(
                function (data) {
                    $scope.userStat = data.data;
                },
                function (error) {
                    console.log("users error")
                }
            );
        }

    }]);







/*
angular.module("food", [])
    .controller("FoodCtrl", ["$scope", "$http", function ($scope, $http) {
        console.log("foodCtrl");

        $scope.userFood = {};
        $scope.food = {};
        $scope.showAddUserSuccess = false;
        $scope.showAddSuccess = false;
        $scope.showAddUserError = false;
        $scope.showAddError = false;

        $scope.userStat={};

        $scope.addUserFood = function (userFood) {
            console.log("addUserFood method")
            $http({
                method: "POST",
                url: "/api/user/add_user_food",
                data: $.param(userFood),
                headers: {"Content-Type" : "application/x-www-form-urlencoded" }
            }).then(
                function (data) {
                    $scope.showAddUserSuccess = true;
                    $scope.showAddSuccess = false;
                    $scope.showAddUserError = false;
                    $scope.showAddError = false;
                    $scope.userStat.calories=data.data;
                },
                function (error) {
                    $scope.showAddUserError = true;
                    $scope.showAddUserSuccess = false;
                    $scope.showAddSuccess = false;
                    $scope.showAddError = false;
                }
            );
        }
        $scope.addFood = function (food) {
            $http({
                method: "POST",
                url: "/api/user/add_food",
                data: $.param(food),
                headers: {"Content-Type" : "application/x-www-form-urlencoded"}
            }).then(
                function (data) {
                    $scope.showAddSuccess = true;
                    $scope.showAddUserSuccess = false;
                    $scope.showAddUserError = false;
                    $scope.showAddError = false;

                },
                function (error) {
                    $scope.showAddError = true;
                    $scope.showAddUserSuccess = false;
                    $scope.showAddSuccess = false;
                    $scope.showAddUserError = false;

                }
            );
        }

        $scope.getuserStat = function () {
            $http({
                method: "GET",
                url: "/api/user/user_statistics",
                headers: {"Content-Type": "application/json"}
            }).then(
                function (data) {
                    $scope.userStat = data.data;
                },
                function (error) {
                    console.log("users error")
                }
            );
        }

    }]);*/
