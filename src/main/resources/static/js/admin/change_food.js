angular.module("foodChange", [])
    .controller("FoodChangeCtrl", ["$scope", "$http", function ($scope, $http) {

        $scope.foodInfo = {};
        var searchParams = new URLSearchParams(window.location.search);

        $scope.getFood = function () {
            $http({
                method: "GET",
                url: "/api/admin/food/" + searchParams.get("foodId"),
                headers: {"Content-Type": "application/json"},
            }).then(
                function (data) {
                    $scope.foodInfo = data.data;
                },
                function (error) {
                }
            );
        };

        $scope.changeFood = function (foodInfo) {
            console.log("changeFood " + foodInfo.food.nameUa);
            removeAlertDivs();
            $http({
                method: "POST",
                url: "/api/admin/food/" + searchParams.get("foodId") + "/change",
                data: JSON.stringify(foodInfo),
                headers: {"Content-Type": "application/json"}
            }).then(
                function (data) {
                    location.replace(document.referrer);
                },
                function (error) {
                    if (error.status === 406) {
                        error.data.forEach(msg => {
                            createAlertDiv(msg.message, "food-" + msg.field, "danger");
                        });
                    } else {
                        createAlertDiv(error.data.message, "food-form-header", "danger");
                    }
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