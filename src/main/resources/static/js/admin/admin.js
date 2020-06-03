angular.module("admin", [])
    .controller("UserCtrl", ["$scope", "$http", function ($scope, $http) {
        console.log("userCtrl")
        $scope.users = [];
        $scope.test="test123";
        $scope.foods = [];
        $scope.meals = [];

        $scope.foodPage=0;
        $scope.foodPageMax=0;

        $scope.usersFoodPage=0;
        $scope.usersFoodPageMax=0;

        $scope.getUsers = function(){
            $http({
                method: "GET",
                url: "/api/admin/all_users",
                headers: { "Content-Type" : "application/json" },
            }).then(
                function(data) {
                    $scope.users = data.data.users;
                },
                function(error) {
                    console.log("users error")
                }
            );
        }
        $scope.getFood = function(){
            $http({
                method: "GET",
                url: "/api/admin/all_food",
                headers: { "Content-Type" : "application/json" },
                params: {page: $scope.foodPage}
            }).then(
                function(data) {
                    $scope.foods = data.data.foods.content;
                    $scope.foodPageMax = data.data.foods.totalPages-1;

                },
                function(error) {
                    console.log("foods error")
                }
            );
        };
        $scope.getUsersFood = function(){
            console.log($scope.usersFoodPage);
            console.log("max: " , $scope.usersFoodPageMax);
            $http({
                method: "GET",
                url: "/api/admin/all_users_food",
                headers: { "Content-Type" : "application/json" },
                params: {page: $scope.usersFoodPage}
            }).then(
                function(data) {
                    $scope.meals = data.data.meals.content;
                    $scope.usersFoodPageMax = data.data.meals.totalPages-1;
                },
                function(error) {
                    console.log("usersFood error")
                }
            );
        }

    }]);