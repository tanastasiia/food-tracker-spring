angular.module("registration_form",[])
    .controller("RegistrationCtrl", ["$scope", "$http", function ($scope, $http) {
        $scope.user = {};
        $scope.showSuccess = false;
        $scope.showError = false;

        $scope.addUser = function(user){
            user.dateOfBirth = document.getElementById("dateOfBirth").value;
            $http({
                method: "POST",
                url: "/api/registration/register",
                data: $.param(user),
                headers: { "Content-Type" : "application/x-www-form-urlencoded" }
            }).then(
                (data) => {
                    console.log("success");
                    $scope.showSuccess = true;
                    $scope.showError = false;
                },
                (error) => {
                    console.log("error");
                    $scope.showSuccess = false;
                    $scope.showError = true;
                }
            );
        }
    }]);