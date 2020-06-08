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

        $scope.addMeal = function () {
            var userFood = {"foodName" :  document.getElementById("food-name").value,
                   "amount":  document.getElementById("food-amount").value};
            $http({
                method: "POST",
                url: "/api/user/add_meal",
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
                    console.log("users error")
                }
            );
        };


        $scope.foodNamesList = function() {
            $http({
                method: "GET",
                url: "/api/user/food_names_list",
                headers: {"Content-Type": "application/json"}
            }).then(
                function (data) {
                    autocomplete(document.getElementById("food-name"),  data.data.foodNames);
                }
            );
        }




    }]);




function autocomplete(inp, arr) {
    console.log("autocomplete func");
    var currentFocus;
    inp.addEventListener("input", function(e) {
        var a, b, i, val = this.value;
        closeAllLists();
        if (!val) { return false;}
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
                b.addEventListener("click", function(e) {
                    inp.value = this.getElementsByTagName("input")[0].value;
                    closeAllLists();
                });
                a.appendChild(b);
            }
        }
    });
    inp.addEventListener("keydown", function(e) {
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