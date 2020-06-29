angular.module("food", [])
    .controller("FoodCtrl", ["$scope", "$http", function ($scope, $http) {

        $scope.userFood = {};
        $scope.food = {};

        $scope.userStat = {};

        $scope.addMeal = function () {
            removeAlertDivs();
            var userFood = {
                "foodName": document.getElementById("meal-foodName").value,
                "amount": document.getElementById("meal-amount").value
            };
            $http({
                method: "POST",
                url: "/api/user/add_meal",
                data: $.param(userFood),
                headers: {"Content-Type": "application/x-www-form-urlencoded"}
            }).then(
                function (data) {
                    createAlertDiv(data.data.message, "meal-form-header", "info");
                    $scope.userStat.calories = data.data.calories;
                },
                function (error) {

                    if (error.status === 406) {
                        error.data.forEach(msg => {
                            createAlertDiv(msg.message, "meal-" + msg.field, "danger");
                        });
                    } else {
                        createAlertDiv(error.data.message, "meal-form-header", "danger");
                    }
                }
            );
        };
        $scope.addFood = function (food) {
            removeAlertDivs();
            $http({
                method: "POST",
                url: "/api/user/add_food",
                data: $.param(food),
                headers: {"Content-Type": "application/x-www-form-urlencoded"}
            }).then(
                function (data) {
                    createAlertDiv(data.data.message, "food-form-header", "info");
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
        };

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
                    console.log("getuserStat error")
                }
            );
        };


        $scope.foodNamesList = function () {
            $http({
                method: "GET",
                url: "/api/user/food_names_list",
                headers: {"Content-Type": "application/json"}
            }).then(
                function (data) {
                    autocomplete(document.getElementById("meal-foodName"), data.data.foodNames);
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


function autocomplete(inp, arr) {
    console.log("autocomplete func");
    var currentFocus;
    inp.addEventListener("input", function (e) {
        var a, b, i, val = this.value;
        closeAllLists();
        if (!val) {
            return false;
        }
        currentFocus = -1;
        a = document.createElement("DIV");
        a.setAttribute("id", this.id + "autocomplete-list");
        a.setAttribute("class", "autocomplete-items");
        this.parentNode.appendChild(a);
        for (i = 0; i < arr.length; i++) {
            if (arr[i].substr(0, val.length).toUpperCase() === val.toUpperCase()) {
                b = document.createElement("DIV");
                b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                b.innerHTML += arr[i].substr(val.length);
                b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                b.addEventListener("click", function (e) {
                    inp.value = this.getElementsByTagName("input")[0].value;
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
    });
    inp.addEventListener("keydown", function (e) {
        var x = document.getElementById(this.id + "autocomplete-list");
        if (x) x = x.getElementsByTagName("div");
        if (e.keyCode === 40) {
            currentFocus++;
            addActive(x);
        } else if (e.keyCode === 38) {
            currentFocus--;
            addActive(x);
        } else if (e.keyCode === 13) {
            e.preventDefault();
            if (currentFocus > -1) {
                if (x) x[currentFocus].click();
            }
        }
    });

    function addActive(x) {
        if (!x) return false;
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        x[currentFocus].classList.add("autocomplete-active");
    }

    function removeActive(x) {
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }

    function closeAllLists(elmnt) {
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt !== x[i] && elmnt !== inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }

    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}