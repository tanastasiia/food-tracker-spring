angular.module("statistics", [])
    .controller("StatisticsCtrl", ["$scope", "$http", function ($scope, $http) {
        console.log("statisticsCtrl")

        $scope.todaysFood = [];
        $scope.allFood = [];
        $scope.userStat = {};

        $scope.todaysFoodPage = 0;
        $scope.todaysFoodPageMax = 0;
        $scope.foodPage = 0;
        $scope.foodPageMax = 0;
        $scope.getTodaysFood = function () {
            $http({
                method: "GET",
                url: "/api/user/todays_food",
                headers: {"Content-Type": "application/json"},
                params: {page: $scope.todaysFoodPage}
            }).then(
                function (data) {
                    $scope.todaysFood = data.data.usersFood.content;
                    $scope.todaysFoodPageMax = data.data.usersFood.totalPages-1;
                },
                function (error) {
                    console.log("users error")
                }
            );
        }
        $scope.getAllFood = function () {
            $http({
                method: "GET",
                url: "/api/user/all_food",
                headers: {"Content-Type": "application/json"},
                params: {page: $scope.foodPage}
            }).then(
                function (data) {
                    $scope.allFood = data.data.usersFood.content;
                    $scope.foodPageMax = data.data.usersFood.totalPages-1;
                },
                function (error) {
                    console.log("users error")
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


    }]);