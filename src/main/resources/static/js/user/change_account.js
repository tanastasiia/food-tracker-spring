angular.module("accountChange", [])
    .controller("AccountChangeCtrl", ["$scope", "$http", function ($scope, $http) {

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
        };

        $scope.initForm = function () {
            $scope.user = $scope.getUser();
        };

        $scope.changeAccount = function (user) {
            removeAlertDivs();
            user.dateOfBirth = document.getElementById("dateOfBirth").value;
            $http({
                method: "POST",
                url: "/api/user/change_account",
                data: $.param(user),
                headers: {"Content-Type": "application/x-www-form-urlencoded"}
            }).then(
                function (data) {
                    location.replace("/account");
                },
                function (error) {
                    error.data.forEach(msg => {
                        createAlertDiv(msg.message, msg.field, "danger");
                    });
                }
            );
        }


    }]);

function createAlertDiv(msg, beforeElementId, infoOrDanger) {
    var errorField = document.getElementById(beforeElementId);
    var errorDiv = document.createElement("DIV");
    errorDiv.className = "alert alert-" + infoOrDanger;
    errorDiv.appendChild(document.createTextNode(msg));
    errorField.parentNode.insertBefore(errorDiv, errorField.nextSibling);
}

function removeAlertDivs() {
    var divs = document.getElementsByClassName("alert alert-danger");
    while (divs[0]) {
        divs[0].parentNode.removeChild(divs[0]);
    }
    divs = document.getElementsByClassName("alert alert-info");
    while (divs[0]) {
        divs[0].parentNode.removeChild(divs[0]);
    }
}